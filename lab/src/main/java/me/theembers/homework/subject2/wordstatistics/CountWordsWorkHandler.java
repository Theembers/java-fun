package me.theembers.homework.subject2.wordstatistics;

import me.theembers.homework.subject2.filereader.WorkHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 单词次数统计
 */
public class CountWordsWorkHandler implements WorkHandler {
    /**
     * 匹配单词的正则表达式
     */
    private static final Pattern PATTERN = Pattern.compile("\\b[a-zA-Z-]+\\b");
    /**
     * 存储单词个数的 线程安全的map
     */
    private Map<String, AtomicLong> wordsCountMap;
    /**
     * 基于单词为分段的分段锁
     */
    private Map<String, Lock> wordsSegmentLock;

    public CountWordsWorkHandler(String... checkWords) {
        this.wordsCountMap = new HashMap<>(checkWords.length);
        this.wordsSegmentLock = new ConcurrentHashMap<>(checkWords.length);
        // 初始化
        if (checkWords != null && checkWords.length > 0) {
            Arrays.asList(checkWords).forEach(word -> {
                this.wordsCountMap.put(word, new AtomicLong(0));
                this.wordsSegmentLock.put(word, new ReentrantLock());
            });
        }
    }

    /**
     * 执行统计
     *
     * @param text 文本内容
     */
    public void countWords(String text) {
        Matcher m = PATTERN.matcher(text);
        while (m.find()) {
            String word = m.group();
            if (wordsSegmentLock.containsKey(word)) {
                Lock lock = wordsSegmentLock.get(word);
                lock.lock();
                wordsCountMap.get(word).addAndGet(1);
                lock.unlock();
            }
            // 不需要统计的词直接丢弃
        }
    }


    public Map<String, AtomicLong> getWordsCountMap() {
        return new HashMap<>(this.wordsCountMap);
    }

    @Override
    public void execute(String line) {
        countWords(line);
    }
}
