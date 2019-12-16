package me.theembers.homework.subject1;

import java.util.Random;

public class DemoClient {

    public static void main(String[] args) {
        /**
         * integer 的例子
         */
        IntegerAntMinStack integerAntMinStack = new IntegerAntMinStack();
        Random random1 = new Random(31);
        for (int i = 0; i < 10; i++) {
            int item = random1.nextInt(100);
            integerAntMinStack.push(item);
            System.out.println("Push: " + item);
            boolean flag = random1.nextBoolean();
            if (flag && !integerAntMinStack.empty()) {
                try {
                    int min = integerAntMinStack.min();
                    System.out.println("Peek Min: " + min);
                } catch (Exception e) {
                    System.err.println();
                }
            }
        }

        /**
         * string 的例子
         */
        StringAntMinStack stringAntMinStack = new StringAntMinStack();
        Random random2 = new Random(66);
        for (int i = 0; i < 10; i++) {
            String item = String.valueOf(random2.nextInt(100));
            stringAntMinStack.push(item);
            System.out.println("Push: " + item);
            boolean flag = random2.nextBoolean();
            if (flag && !stringAntMinStack.empty()) {
                try {
                    String min = stringAntMinStack.min();
                    System.out.println("Peek Min: " + min);
                } catch (Exception e) {
                    System.err.println();
                }
            }
        }
    }
}
