package me.theembers.homework.subject2;

import me.theembers.homework.subject2.filereader.BigFileReader;
import me.theembers.homework.subject2.filereader.FileReader;
import me.theembers.homework.subject2.filereader.ReaderConfig;
import me.theembers.homework.subject2.wordstatistics.CountWords;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class MainClient {
    public static void main(String[] args) {
        CountWords countWords = new CountWords("harry", "that", "the");

        ReaderConfig readerConfig = new ReaderConfig(10, 100, "UTF-8");
        FileReader bigFileReader = new BigFileReader(new File("F:\\hp1.txt"), readerConfig, line -> {
            countWords.countWords(line);
        });
        bigFileReader.execute();

        Map<String, AtomicLong> map = countWords.getWordsCountMap();

        map.forEach((k, v) -> System.out.println(String.format("%s : %d", k, v.get())));
    }
}
