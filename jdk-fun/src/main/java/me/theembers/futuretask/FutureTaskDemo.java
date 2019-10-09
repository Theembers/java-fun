package me.theembers.futuretask;

import java.util.concurrent.*;

/**
 * FutureTask 异步阻塞
 *
 * @author TheEmbers Guo
 * createTime 2019-10-09 15:22
 */
public class FutureTaskDemo {

    private static void asThread2Run() {
        FutureTask<String> task = new FutureTask<>(new TaskService());
        new Thread(task).start();
        try {
            String result = task.get(3, TimeUnit.SECONDS);
            System.out.println("===>> " + result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static void asThreadPool2Run() {
        FutureTask<String> task = new FutureTask<>(new TaskService());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
        try {
            String result = task.get(3, TimeUnit.SECONDS);
            System.out.println("===>> " + result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("===>> " + Thread.currentThread().getName());
//        asThread2Run();
        asThreadPool2Run();
        System.out.println("===>> end.");
    }
}
