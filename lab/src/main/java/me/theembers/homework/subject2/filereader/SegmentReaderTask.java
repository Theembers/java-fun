package me.theembers.homework.subject2.filereader;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 分段读取任务
 */
public class SegmentReaderTask implements Runnable {
    private long start;
    private long segmentSize;
    private byte[] readBuff;

    private RandomAccessFile raFile;

    private ReaderConfig readerConfig;

    private WorkHandler handler;

    private CountDownLatch countDownLatch;


    public SegmentReaderTask(Point point, RandomAccessFile raFile, WorkHandler handler, ReaderConfig readerConfig, CountDownLatch countDownLatch) {
        this.start = point.start;
        this.segmentSize = point.end - point.start + 1;
        this.readBuff = new byte[readerConfig.getBufferSize()];
        this.raFile = raFile;
        this.handler = handler;
        this.readerConfig = readerConfig;
        this.countDownLatch = countDownLatch;
    }

    /**
     * 读取线程
     */
    @Override
    public void run() {
        try {
            MappedByteBuffer mapBuffer = raFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, this.segmentSize);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int bufferSize = readerConfig.getBufferSize();
            for (int offset = 0; offset < segmentSize; offset += bufferSize) {
                int readLength;
                if (offset + bufferSize <= segmentSize) {
                    readLength = bufferSize;
                } else {
                    readLength = (int) (segmentSize - offset);
                }
                mapBuffer.get(readBuff, 0, readLength);
                for (int i = 0; i < readLength; i++) {
                    byte tmp = readBuff[i];
                    if (tmp == '\n' || tmp == '\r') {
                        doWork(bos.toByteArray());
                        bos.reset();
                    } else {
                        bos.write(tmp);
                    }
                }
            }
            if (bos.size() > 0) {
                doWork(bos.toByteArray());
            }
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWork(byte[] bytes) throws UnsupportedEncodingException {
        String line = new String(bytes, readerConfig.getCharset());
        if (line != null && !"".equals(line)) {
            this.handler.execute(line);
        }
    }

    public static class Point {
        private long start;
        private long end;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }
}
