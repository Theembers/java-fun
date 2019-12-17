package me.theembers.homework.subject2;

import me.theembers.homework.subject2.filereader.MultiFileReader;
import me.theembers.homework.subject2.filereader.FileReader;
import me.theembers.homework.subject2.filereader.ReaderConfig;
import me.theembers.homework.subject2.wordstatistics.CountWordsWorkHandler;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 题目二：
 * JAVA中对文件读取的效率会受到文件大小本身的影响，本题目要求能够对于文本文件进行读取，在保证读取效率的同时，要求内存不能溢出。
 * 要求：
 * 输入一个本地文本文件地址，文本文件大小为2G,文本编码类型为utf-8。
 * 读取该文本文件中出现特定单词的数量
 * 把文本部分文件读取到内存中后，即可释放内存，并统计特定单词出现次数和总时间耗时
 * 尽量减低字符统计耗时。
 */
public class MainClient {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // 设置 单词个数统计 WorkHandler
        CountWordsWorkHandler countWords = new CountWordsWorkHandler("DEBUG", "ERROR", "INFO");
        // 设置 文件读取配置信息
        ReaderConfig readerConfig = new ReaderConfig(5, 1024 * 1024, "UTF-8");

        // 初始化 文件读取器
        FileReader fileReader = new MultiFileReader(new File("F:\\1.log"), readerConfig, countWords);
        // 执行
        fileReader.execute();

        long end = System.currentTimeMillis();
        System.out.println(String.format("执行总耗时：%d", end - start));
        // 获取 结果
        Map<String, AtomicLong> map = countWords.getWordsCountMap();
        map.forEach((k, v) -> System.out.println(String.format("%s : %d", k, v.get())));
    }
}
