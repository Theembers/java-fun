package me.theembers.homework.subject2.filereader;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BigFileReader implements FileReader {
    /**
     * 线程数
     */
    private int threadSize;
    /**
     * 字符集
     */
    private String charset;

    private WorkHandler handler;
    /**
     * 线程池
     */
    private ExecutorService executorService;
    private long fileLength;
    private RandomAccessFile raFile;
    private Set<SegmentReaderTask.StartEndPoint> segmentPoints;


    public BigFileReader(File file, String charset, WorkHandler handler, int threadSize) {
        if (!file.exists()) {
            throw new RuntimeException("file isn't existed.");
        }
        this.fileLength = file.length();
        this.handler = handler;
        this.charset = charset;
        this.threadSize = threadSize;
        try {
            this.raFile = new RandomAccessFile(file, "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.executorService = Executors.newFixedThreadPool(threadSize);
        segmentPoints = new HashSet<>();
    }

    @Override
    public final void execute() {
        // 初始化
        initSegmentPoint();
        if (segmentPoints == null || segmentPoints.size() == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(segmentPoints.size());
        for (SegmentReaderTask.StartEndPoint point : segmentPoints) {
            this.executorService.execute(new SegmentReaderTask(point, raFile, handler, charset, countDownLatch));
        }
        try {
            countDownLatch.await();
            destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final void initSegmentPoint() {
        long segmentCount = this.fileLength / this.threadSize;
        try {
            splitSegment(0, segmentCount);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

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
     *
     * @param start
     * @param size
     * @throws IOException
     */
    private void splitSegment(long start, long size) throws IOException {
        if (start > fileLength - 1) {
            return;
        }
        SegmentReaderTask.StartEndPoint point = new SegmentReaderTask.StartEndPoint();
        point.start = start;
        long endPosition = start + size - 1;
        if (endPosition >= fileLength - 1) {
            point.end = fileLength - 1;
            segmentPoints.add(point);
            return;
        }

        raFile.seek(endPosition);
        byte tmp = (byte) raFile.read();
        while (tmp != '\n' && tmp != '\r') {
            endPosition++;
            if (endPosition >= fileLength - 1) {
                endPosition = fileLength - 1;
                break;
            }
            raFile.seek(endPosition);
            tmp = (byte) raFile.read();
        }
        point.end = endPosition;
        segmentPoints.add(point);

        splitSegment(endPosition + 1, size);

    }
}