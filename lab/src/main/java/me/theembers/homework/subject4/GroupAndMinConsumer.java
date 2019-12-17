package me.theembers.homework.subject4;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:31 下午
 */
public class GroupAndMinConsumer implements Runnable {
    /**
     * 内存数据库
     */
    private MemoryDataBase dataBase;
    /**
     * 分组 (groupId,minQuota)
     */
    private TreeMap<String, ItemInfo> groupMap;

    private CountDownLatch countDownLatch;

    public GroupAndMinConsumer(MemoryDataBase dataBase, CountDownLatch countDownLatch) {
        this.dataBase = dataBase;
        // treeMap 排序，实现comparator：根据key值升序
        this.groupMap = new TreeMap<>(Comparator.comparing(Long::valueOf));
        this.countDownLatch = countDownLatch;
    }

    public TreeMap<String, ItemInfo> getGroupMap() {
        return groupMap;
    }

    @Override
    public void run() {
        try {
            LinkedBlockingQueue<ItemInfo> queue = this.dataBase.getDataTable();
            for (; ; ) {
                if (queue.isEmpty()) {
                    // 只有当 count 为1时说明 FileDataProducer数据已全部完成加载
                    if (countDownLatch.getCount() <= 1) {
                        countDownLatch.countDown();
                        break;
                    }
                    // 到这里说明仍有线程未完成 继续执行
                } else {
                    // 执行比较操作 维护各分组的最小值
                    compareAndSaveMinItem(queue.take());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 比较并存储最小数据项
     *
     * @param item 当前数据项
     */
    private void compareAndSaveMinItem(ItemInfo item) {
        ItemInfo curDataItem = groupMap.get(item.getGroupId());
        if (curDataItem == null) {
            groupMap.put(item.getGroupId(), item);
        } else {
            if (item.getQuota() < curDataItem.getQuota()) {
                groupMap.put(item.getGroupId(), item);
            }
        }
    }
}
