package me.theembers.homework.subject4;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:32 下午
 */
public class MemoryDataBase {
    /**
     * 存储队列
     */
    private LinkedBlockingQueue<ItemInfo> dataTable;

    public MemoryDataBase() {
        dataTable = new LinkedBlockingQueue<>();
    }

    public LinkedBlockingQueue<ItemInfo> getDataTable() {
        return dataTable;
    }
}
