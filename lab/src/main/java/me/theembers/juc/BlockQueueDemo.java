package me.theembers.juc;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockQueueDemo {
    private final BlockingQueue<Info> dataQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final ExecutorService executorService2 = Executors.newFixedThreadPool(4);

    private final AtomicInteger count = new AtomicInteger(0);

    private boolean doWork(String[] enterpriseCodes) {
        CountDownLatch countDownLatch = new CountDownLatch(enterpriseCodes.length + 1);
        for (String e : Arrays.asList(enterpriseCodes)) {
            System.out.println("start: " + e);
            executorService.execute(() -> {
                int pageNum = 0;
                for (; ; ) {
                    if (dataQueue.size() > 50) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " sleep...");
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    List<Info> snapEntityList = getInfoList(pageNum);

                    if (CollectionUtils.isEmpty(snapEntityList)) {
                        break;
                    }
                    dataQueue.addAll(snapEntityList);
                    System.out.println("dataQueue size: " + dataQueue.size());
                    pageNum++;
                }
                countDownLatch.countDown();
            });
        }
        for (; ; ) {
            if (countDownLatch.getCount() <= 1 && dataQueue.isEmpty()) {
                System.out.println("done!");
                countDownLatch.countDown();
                break;
            }
            executorService2.execute(() -> {
                if (!dataQueue.isEmpty()) {
                    Info info = dataQueue.poll();
                    System.out.println("poll：" + dataQueue.size());
                    saveInfoList(info);
                    count.incrementAndGet();
                }
            });
        }
        try {
            countDownLatch.await();
            System.out.println("over! count:" + count);
            executorService2.shutdown();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BlockQueueDemo demo = new BlockQueueDemo();
        demo.doWork(new String[]{
                "1", "2"
        });
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }

    private void saveInfoList(Info info) {
        System.out.println(info);
    }

    private List<Info> getInfoList(int pageNum) {
        if (pageNum > 100) {
            return new ArrayList<>(0);
        }
        return Arrays.asList(new Info(), new Info(), new Info(), new Info(), new Info(), new Info(), new Info(), new Info(), new Info(), new Info());
    }

    private class Info {
        @Override
        public String toString() {
            return "Info{}";
        }
    }
}
