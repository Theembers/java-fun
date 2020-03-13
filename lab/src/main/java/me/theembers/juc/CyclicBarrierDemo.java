package me.theembers.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    private static class BarrierThread implements Runnable {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": BarrierThread going..");
        }
    }

    public static void main(String[] args) {

        final CyclicBarrier barrier = new CyclicBarrier(3, new BarrierThread());

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " index: " + barrier.await());
                } catch (BrokenBarrierException | InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " start...");
            }, ("T" + i)).start();
        }

    }
}
