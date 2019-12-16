package me.theembers.homework.subject4;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:32 下午
 */
public class MemoryDataBase {
    private LinkedBlockingQueue<ItemInfo> queue;
    public MemoryDataBase() {
        queue = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<ItemInfo> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingQueue<ItemInfo> queue) {
        this.queue = queue;
    }
}
