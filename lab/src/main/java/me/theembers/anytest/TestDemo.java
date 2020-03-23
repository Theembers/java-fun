package me.theembers.anytest;

import java.util.Scanner;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-23 10:26
 */
public class TestDemo {
    // 共享成员变量,矩阵
    public static int[][] arr = null;
    // 主函数
    public static void main(String[] args) {

        System.out.println(tranString(700));
    }

    public static int trans(String str) {
        int count = 0;
        for (int i = str.length() - 1, j = 1; i >= 0; i--, j *= 26){
            char c = str.charAt(i);
            if (c < 'a' || c > 'z') return 0;
            count += ((int)c - 'a') * j;
        }
        return count;
    }

    public static String tranString(int n) {
        String s = "";
        while (n > 0){
            int m = n % 26;
            s = (char)(m + 'a') + s;
            n = (n - m) / 26;
        }
        return s;
    }
}