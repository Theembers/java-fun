package me.theembers.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-31 16:59
 */
public class AqsDemo {
    private ReentrantLock lock = new ReentrantLock();

    public void getMethod() {
        if (lock.tryLock()) {
            System.out.println("get...");
            lock.unlock();
        }

    }

    private void setMethod() {
        if (lock.tryLock()) {
            try {
                System.out.println("set.........................");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        AqsDemo aqsDemo = new AqsDemo();
        try {
            Thread t1 = new Thread(() -> {
                for (; ; ) {
                    aqsDemo.getMethod();
                }
            });

            Thread t2 = new Thread(() -> {
                for (; ; ) {
                    aqsDemo.setMethod();
                }
            });

            t2.start();
            Thread.sleep(1000);
            t1.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
