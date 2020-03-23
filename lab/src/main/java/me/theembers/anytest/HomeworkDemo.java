package me.theembers.anytest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-18 10:21
 */
public class HomeworkDemo {


    /**
     * 1 、有这样一个序冽： A , B . C . ． … ， Z ,
     * AA . AB ' AC … , AZ . BA . BB . BC ， … ， BZ , ． … ZZ , AAA , AAB , AAC ， … AAZ . ABA , ABB , ABC ． … ，输出第 700 位的值。（45分钟）
     *
     *
     * 2 、把两个顺序链表合成一个顺序链表。（15分钟）
     * 3 、输出一串字符中连续出现．多次的字符。（15分钟）
     * 4 、计算两个长整数的乘积，长数字用数组表示。（30分钟）
     * 5 、把一个整数高低位反向输出．（15分钟）
     * 6 、用 C++ 类实现观察者模式． (10分钟）
     */

    /**
     * 1 、有这样一个序冽： A , B . C . ． … ， Z , AA . AB ' AC … , AZ . BA . BB . BC ， … ， BZ , ． … ZZ , AAA , AAB , AAC ， … AAZ . ABA , ABB , ABC ． … ，输出第 700 位的值。（45分钟）
     */
    private static class P1 {
        private static final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        private static final List<String> letterList;

        static {
            letterList = Arrays.asList(letters);
        }

        private static String worker(List<String> data, int index, int checkIndex) {
            if (data.size() <= 0) {
                data.addAll(letterList);
            }
            List<String> copyData = new ArrayList<>(data);
            for (; index < copyData.size(); index++) {
                String b = data.get(index);
                for (String e : letterList) {
                    String v = b + e;
                    data.add(v);

                    if (index >= checkIndex) {
                        return data.get(checkIndex);
                    }
                }
            }

            return worker(data, index, checkIndex);
        }


        public static void main(String[] args) {
            List<String> data = new ArrayList<>();
            String checkStr = P1.worker(data, 0, 700);
            System.out.println(checkStr);
            data.forEach(System.out::println);
        }


    }
}
