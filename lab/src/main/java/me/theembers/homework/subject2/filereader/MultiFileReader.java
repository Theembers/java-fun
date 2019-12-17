package me.theembers.homework.subject2.filereader;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程的文件读取
 */
public class MultiFileReader implements FileReader {
    /**
     * 线程池
     */
    private ExecutorService executorService;

    /**
     * 任务执行者
     */
    private WorkHandler handler;
    /**
     * 读取器配置
     */
    private ReaderConfig readerConfig;

    /**
     * 文件字节大小
     */
    private long fileLength;
    private RandomAccessFile raFile;
    private Set<SegmentReaderTask.Point> segmentPoints;


    public MultiFileReader(File file, ReaderConfig readerConfig, WorkHandler handler) {
        if (!file.exists()) {
            throw new RuntimeException("file isn't existed.");
        }
        this.fileLength = file.length();
        this.handler = handler;
        this.readerConfig = readerConfig;
        try {
            this.raFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.executorService = Executors.newFixedThreadPool(readerConfig.getThreadSize());
        segmentPoints = new HashSet<>();
    }


    /**
     * 执行
     * 1. 先按照线程数拆分文本段 标记每个线程需完成的段落
     * 2. 线程调用 读取任务
     * 3. 全部完成后 执行线程/文件等 释放
     */
    @Override
    public final void execute() {
        // 初始化分段
        initSegmentPoint();
        if (segmentPoints == null || segmentPoints.size() == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(segmentPoints.size());
        for (SegmentReaderTask.Point point : segmentPoints) {
            this.executorService.execute(new SegmentReaderTask(point, raFile, handler, readerConfig, countDownLatch));
        }
        try {
            // 执行完毕后 结束
            countDownLatch.await();
            destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化分段
     * 根据文件字节大小 与 线程数 求出每个线程需要读取的字节数
     */
    private final void initSegmentPoint() {
        long length = this.fileLength / this.readerConfig.getThreadSize();
        try {
            splitSegment(0, length);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 关闭
     */
    private void destroy() {
        try {
            this.raFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.executorService.shutdown();
    }

    /**
     * 拆分段
     * <p>
     * 1. 通过调用 RandomAccessFile#seek()方法 递归标记界限
     * 2. 根据 ‘\n\r\b’ 偏移切分，防止单词不完整
     * 3. 递归 进行下一段拆分
     *
     * @param start
     * @param length
     * @throws IOException
     */
    private void splitSegment(long start, long length) throws IOException {
        if (start > fileLength - 1) {
            return;
        }
        SegmentReaderTask.Point point = new SegmentReaderTask.Point();
        point.setStart(start);
        long endPosition = start + length - 1;
        if (endPosition >= fileLength - 1) {
            point.setEnd(fileLength - 1);
            segmentPoints.add(point);
            return;
        }

        raFile.seek(endPosition);
        byte tmp = (byte) raFile.read();
        while (tmp != '\n' && tmp != '\r' && tmp != '\b') {
            endPosition++;
            if (endPosition >= fileLength - 1) {
                endPosition = fileLength - 1;
                break;
            }
            raFile.seek(endPosition);
            tmp = (byte) raFile.read();
        }
        point.setEnd(endPosition);
        segmentPoints.add(point);
        splitSegment(endPosition + 1, length);
    }
}