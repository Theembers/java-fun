package me.theembers.homework.subject4;

import java.io.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 文件加载器
 *
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:34 下午
 */
public class FileDataProducer implements Runnable {
    /**
     * 内存数据库
     */
    private MemoryDataBase dataBase;
    private File file;
    private CountDownLatch countDownLatch;

    public FileDataProducer(MemoryDataBase dataBase, File file, CountDownLatch countDownLatch) {
        this.dataBase = dataBase;
        this.file = file;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        loadFileData();
        countDownLatch.countDown();
    }

    /**
     * 文件数据读取
     */
    private void loadFileData() {
        LinkedBlockingQueue<ItemInfo> queue = dataBase.getDataTable();
        InputStreamReader read;
        try {
            read = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(read);
            String line;
            String[] dataArr;
            for (; ; ) {
                if ((line = br.readLine()) == null) {
                    break;
                }
                if (line.equals("")) {
                    continue;
                }
                dataArr = line.split(",");
                ItemInfo item = new ItemInfo(dataArr[0], dataArr[1], new Float(dataArr[2]));
                queue.add(item);
            }
            br.close();
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
