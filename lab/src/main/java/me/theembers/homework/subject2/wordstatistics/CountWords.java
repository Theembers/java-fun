package me.theembers.homework.subject2.wordstatistics;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountWords {
    private static final Pattern PATTERN = Pattern.compile("\\b[a-zA-Z-]+\\b");//正则表达式

    private Map<String, AtomicLong> wordsCountMap;
    private Map<String, Lock> wordsSegmentLock;
    private String[] checkWords;

    public CountWords(String... checkWords) {
        this.checkWords = checkWords;
        this.wordsCountMap = new HashMap<>(checkWords.length);
        this.wordsSegmentLock = new ConcurrentHashMap<>(checkWords.length);
        if (checkWords != null && checkWords.length > 0) {
            Arrays.asList(checkWords).forEach(word -> {
                this.wordsCountMap.put(word, new AtomicLong(0));
                this.wordsSegmentLock.put(word, new ReentrantLock());
            });
        }
    }

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
        }
    }


    public Map<String, AtomicLong> getWordsCountMap() {
        return new HashMap<>(this.wordsCountMap);
    }
}
