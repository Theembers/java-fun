package me.theembers.homework.subject2.filereader;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BigFileReader implements FileReader {
    /**
     * 线程池
     */
    private ExecutorService executorService;


    private WorkHandler handler;
    private ReaderConfig readerConfig;

    private long fileLength;
    private RandomAccessFile raFile;
    private Set<SegmentReaderTask.Point> segmentPoints;


    public BigFileReader(File file, ReaderConfig readerConfig, WorkHandler handler) {
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

    @Override
    public final void execute() {
        // 初始化
        initSegmentPoint();
        if (segmentPoints == null || segmentPoints.size() == 0) {
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(segmentPoints.size());
        for (SegmentReaderTask.Point point : segmentPoints) {
            this.executorService.execute(new SegmentReaderTask(point, raFile, handler, readerConfig, countDownLatch));
        }
        try {
            countDownLatch.await();
            destroy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final void initSegmentPoint() {
        long segmentCount = this.fileLength / this.readerConfig.getThreadSize();
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
        SegmentReaderTask.Point point = new SegmentReaderTask.Point();
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