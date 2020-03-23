package me.theembers.utils.autoturn;


import lombok.extern.slf4j.Slf4j;
import me.theembers.utils.progressbar.ProgressServer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 基于 Pageable 的自动翻页组件
 *
 * @param <E> Entity
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-03 14:57
 */
@Slf4j
public class PageAutoTurnHelper<E> {
    private static final ExecutorService MULTI_THREAD_EXECUTOR = new ThreadPoolExecutor(
            50,
            50,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
    );


    private Pageable pageable;

    public PageAutoTurnHelper() {
        pageable = PageRequest.of(0, 100);
    }

    public PageAutoTurnHelper(Pageable pageable) {

        this.pageable = pageable;
    }

    public PageAutoTurnHelper(int pageNum, int pageSize) {
        if (pageNum < 0) {
            pageNum = 0;
        }
        if (pageSize <= 0) {
            pageSize = 100;
        }
        pageable = PageRequest.of(pageNum, pageSize);
    }


    @FunctionalInterface
    public interface Pager<E> {
        Page<E> execute(Pageable page);
    }


    @FunctionalInterface
    public interface Worker<E> {
        void execute(List<E> list);
    }

    public void turning(Pager<E> pager, Worker<E> worker) {
        turning(pager, worker, true);
    }

    public void turning(Pager<E> pager, Worker<E> worker, boolean openProgress) {
        Page<E> page = pager.execute(pageable);
        worker.execute(page.getContent());

        if (openProgress) {
            ProgressServer.aroundRefresh((long) page.getNumberOfElements(), page.getTotalElements());
        }

        for (int p = 1; p < page.getTotalPages(); p++) {
            pageable = pageable.next();
            page = pager.execute(pageable);
            worker.execute(page.getContent());

            if (openProgress) {
                ProgressServer.aroundRefresh((long) page.getNumberOfElements(), page.getTotalElements());
            }
        }
    }

    public void multiTurning(Pager<E> pager, Worker<E> worker) {
        multiTurning(pager, worker, true);
    }

    public void multiTurning(Pager<E> pager, Worker<E> worker, boolean openProgress) {
        Page<E> page = pager.execute(pageable);
        worker.execute(page.getContent());

        if (openProgress) {
            ProgressServer.aroundRefresh((long) page.getNumberOfElements(), page.getTotalElements());
        }

        CountDownLatch countDownLatch = new CountDownLatch(page.getTotalPages() - 1);
        for (int p = 1; p < page.getTotalPages(); p++) {
            pageable = pageable.next();
            MULTI_THREAD_EXECUTOR.execute(new MultiPageTurner<>(pageable, page, pager, worker, PAGE_SIZE_QUEUE, countDownLatch));
        }
        while (countDownLatch.getCount() != 0) {
            if (!PAGE_SIZE_QUEUE.isEmpty()) {
                int size = PAGE_SIZE_QUEUE.poll();
                log.debug(Thread.currentThread().getName() + "++ " + size);

                if (openProgress) {
                    ProgressServer.aroundRefresh((long) size, page.getTotalElements());
                }
            } else {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final Queue<Integer> PAGE_SIZE_QUEUE = new LinkedBlockingQueue<>();


    private static class MultiPageTurner<E> implements Runnable {
        private Pageable pageable;
        private Page<E> page;
        private Pager<E> pager;
        private Worker<E> worker;
        private Queue<Integer> thePageSizeQueue;
        private CountDownLatch countDownLatch;

        public MultiPageTurner(Pageable pageable, Page<E> page, Pager<E> pager, Worker<E> worker, Queue<Integer> pageSizeQueue, CountDownLatch countDownLatch) {
            this.pageable = pageable;
            this.page = page;
            this.pager = pager;
            this.worker = worker;
            this.thePageSizeQueue = pageSizeQueue;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            log.debug(Thread.currentThread().getName() + "=>> " + pageable.toString());
            page = pager.execute(pageable);
            log.debug(Thread.currentThread().getName() + "=>> size: " + page.getContent().size());
            worker.execute(page.getContent());
            log.debug(Thread.currentThread().getName() + "=>> list: " + page.getSize());
            thePageSizeQueue.offer(page.getNumberOfElements());
            countDownLatch.countDown();
        }
    }
}
