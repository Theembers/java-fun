package me.theembers.homework.subject1;

import java.util.Random;

/**
 * 题目一：
 * 设计含最小函数min()、pop()、push()的栈AntMinStack
 * <p>
 * 要求：
 * 1.AntMinStack实现测试，满足栈特性
 * 2.要求min、push、pop、的时间复杂度都是O(1)
 */
public class DemoClient {

    public static void main(String[] args) {
        /**
         * integer 的例子
         */
        IAntMinStack<Integer> integerAntMinStack = new IntegerAntMinStack();
        Random random1 = new Random(17);
        for (int i = 0; i < 10; i++) {
            int item = random1.nextInt(100);
            integerAntMinStack.push(item);
            System.out.println("push:\t" + item);
            boolean flag = random1.nextBoolean();
            if (flag && !integerAntMinStack.empty()) {
                try {
                    int min = integerAntMinStack.min();
                    System.out.println("min:\t" + min);
                } catch (Exception e) {
                    System.err.println();
                }
            }
        }
        System.out.println("===================================");
        /**
         * string 的例子
         */
        String str = "abcdefghijklmnopqrstuvwxyz";

        IAntMinStack<String> stringAntMinStack = new StringAntMinStack();
        Random random2 = new Random(100);
        for (int i = 0; i < 10; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < 5; j++) {
                int number = random2.nextInt(26);
                sb.append(str.charAt(number));
            }
            String item = sb.toString();
            stringAntMinStack.push(item);
            System.out.println("push:\t" + item);
            boolean flag = random2.nextBoolean();
            if (flag && !stringAntMinStack.empty()) {
                try {
                    String min = stringAntMinStack.min();
                    System.out.println("min:\t" + min);
                } catch (Exception e) {
                    System.err.println();
                }
            }
        }
    }
}
