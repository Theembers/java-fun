package me.theembers.homework.subject2.filereader;

public class ReaderConfig {
    /**
     * 线程数
     */
    private int threadSize;
    /**
     * 缓冲区大小
     */
    private int bufferSize = 1024 * 1024;
    /**
     * 字符集
     */
    private String charset = "UTF-8";


    public ReaderConfig(int threadSize, int bufferSize, String charset) {
        this.threadSize = threadSize;
        this.bufferSize = bufferSize;
        this.charset = charset;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
