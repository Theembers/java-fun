package me.theembers.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * 每个线程通过调用 CountDownLatch#countDown() 方法 使得 CountDownLatch.count扣为0 (如果调用次数大于设置的count值，则保持为0)
 * 其他线程可以通过 CountDownLatch#await() 方法 阻塞当前线程，当 CountDownLatch.count为0时继续执行
 * 其他线程可以通过 CountDownLatch#await(time,unit) 方法 阻塞当前线程，在限定时间内 CountDownLatch.count为0时继续执行，否则放弃等待，继续执行其他代码
 */
public class CountDownLatchDemo {
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(5);

    public static void main(String[] args) {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        new Thread(() -> {
            for (int i = 0; i <= 6; i++)
                demo.bizMethod();
        }).start();

        new Thread(() -> {
            try {
                COUNT_DOWN_LATCH.await();
                System.out.println(Thread.currentThread().getName() + ": going..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                if (COUNT_DOWN_LATCH.await(1, TimeUnit.SECONDS)) {
                    System.out.println(Thread.currentThread().getName() + ": going..");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + ": time out!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        System.out.println("main thread going..");

    }


    private boolean bizMethod() {

        try {
            Thread.sleep(1000);
            COUNT_DOWN_LATCH.countDown();
            System.out.println(Thread.currentThread().getName() + ": " + COUNT_DOWN_LATCH.getCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;

    }
}
