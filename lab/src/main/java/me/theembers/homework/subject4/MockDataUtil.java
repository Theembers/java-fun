package me.theembers.homework.subject4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:33 下午
 */
public class MockDataUtil {
    public static void main(String[] args) throws IOException {
        String path = "/Users/theembers/workspace/Java/java-fun/lab/src/main/resources/data/";
        for (int i = 1; i < 99; i++) {
            File file = new File(path + i + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fileWriter);
            for (int j = 0; j < 4; j++) {
                br.write(getRandomData((int) (Math.random() * 1000) + 100));
                br.newLine();
            }
            br.close();
            fileWriter.close();

        }
        System.out.println("done");
    }

    private static String getRandomData(Integer groupId) {
        Integer id = (int) (Math.random() * 1000000) + 1000000;
        Float quota = (int) (Math.random() * 1000) / 10.0f + 60;
        return id + "," + groupId + "," + quota;
    }
}
