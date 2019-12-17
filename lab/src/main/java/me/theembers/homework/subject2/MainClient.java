package me.theembers.homework.subject2;

import me.theembers.homework.subject2.filereader.BigFileReader;
import me.theembers.homework.subject2.filereader.FileReader;

import java.io.File;


public class MainClient {
    public static void main(String[] args) {

        FileReader bigFileReader = new BigFileReader(new File("F:\\hp1.txt"), "UTF-8", line -> {
            System.out.println(Thread.currentThread().getName() + ": " + line);
        }, 10);
        bigFileReader.execute();
    }
}
