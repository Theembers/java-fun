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
        this.groupMap = new TreeMap<>(Comparator.comparing(Long::valueOf));
        this.countDownLatch = countDownLatch;
    }

    public TreeMap<String, ItemInfo> getGroupMap() {
        return groupMap;
    }

    @Override
    public void run() {
        try {
            LinkedBlockingQueue<ItemInfo> queue = this.dataBase.getQueue();
            for (; ; ) {
                if (queue.isEmpty()) {
                    // 只有当 count 为 1 时 才说明 生产者数据已全部完成加载
                    if (countDownLatch.getCount() <= 1) {
                        countDownLatch.countDown();
                        break;
                    }
                } else {
                    ItemInfo item = queue.take();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
