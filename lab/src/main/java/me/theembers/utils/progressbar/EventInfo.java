package me.theembers.utils.progressbar;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-05 11:21
 */
public class EventInfo {
    @Getter
    private String name;
    @Getter
    private AtomicLong num;
    @Getter
    private Long total;
    @Getter
    private Long time;


    private EventInfo(String name) {
        this.name = name;

    }

    public static EventInfo initBuild(String name) {
        EventInfo eventInfo = new EventInfo(name);
        eventInfo.num = new AtomicLong(0);
        eventInfo.time = System.currentTimeMillis();
        return eventInfo;
    }

    public EventInfo flush(Long num) {
        this.num.addAndGet(num);
        this.time = System.currentTimeMillis();
        return this;
    }

    public EventInfo setTotal(Long total) {
        this.total = total;
        return this;
    }

    @Override
    public String toString() {
        return "EventInfo{" +
                "name='" + name + '\'' +
                ", num=" + num +
                ", total=" + total +
                ", time=" + time +
                '}';
    }
}
