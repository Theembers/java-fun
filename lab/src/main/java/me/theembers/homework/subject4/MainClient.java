package me.theembers.homework.subject4;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:33 下午
 */
public class MainClient {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        // 加载数据（我的电脑相对路径读取会异常，所以只能写这个绝对路径）
        String dirPath = "/Users/theembers/workspace/Java/java-fun/lab/src/main/resources/data/";
        File dirFile = new File(dirPath);
        File[] files = dirFile.listFiles();

        MemoryDataBase dataBase = new MemoryDataBase();
        // countDownLatch的count等于：文件数量 + 1个consumer线程数
        CountDownLatch countDownLatch = new CountDownLatch(files.length + 1);

        for (File file : files) {
            FileDataProducer producer = new FileDataProducer(dataBase, file, countDownLatch);
            EXECUTOR_SERVICE.execute(producer);
        }

        GroupAndMinConsumer consumer = new GroupAndMinConsumer(dataBase, countDownLatch);
        new Thread(consumer).start();

        try {
            countDownLatch.await();
            EXECUTOR_SERVICE.shutdownNow();
            consumer.getGroupMap().forEach((k, dataItem) -> {
                System.out.println(dataItem.toString());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
