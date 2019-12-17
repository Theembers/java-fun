package me.theembers.homework.subject2.filereader;

import java.io.ByteArrayOutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.CountDownLatch;

public class SegmentReaderTask implements Runnable {
    private long start;
    private long segmentSize;
    private byte[] readBuff;

    private RandomAccessFile raFile;
    /**
     * 缓冲区大小
     */
    private static final int bufferSize = 1024 * 1024;
    /**
     * 字符集
     */
    private String charset;

    private WorkHandler handler;

    private CountDownLatch countDownLatch;


    public SegmentReaderTask(StartEndPoint point, RandomAccessFile raFile, WorkHandler handler, String charset, CountDownLatch countDownLatch) {
        this.start = point.start;
        this.segmentSize = point.end - point.start + 1;
        this.readBuff = new byte[bufferSize];
        this.raFile = raFile;
        this.handler = handler;
        this.charset = charset;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            MappedByteBuffer mapBuffer = raFile.getChannel().map(FileChannel.MapMode.READ_ONLY, start, this.segmentSize);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
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
        String line = new String(bytes, charset);
        if (line != null && !"".equals(line)) {
            this.handler.execute(line);
        }
    }

    public static class StartEndPoint {
        public long start;
        public long end;
    }
}
