package me.theembers.homework.subject1;

import java.util.Random;

public class DemoClient {

    public static void main(String[] args) {
        /**
         * integer 的例子
         */
//        IntegerAntMinStack minStack = new IntegerAntMinStack();
//        Random random = new Random(31);
//        for (int i = 0; i < 10; i++) {
//            int item = random.nextInt(100);
//            minStack.push(item);
//            System.out.println("Push: " + item);
//            boolean flag = random.nextBoolean();
//            if (flag && !minStack.empty()) {
//                try {
//                    int min = minStack.min();
//                    System.out.println("Peek Min: " + min);
//                } catch (Exception e) {
//                    System.err.println();
//                }
//            }
//        }

        /**
         * string 的例子
         */
        StringAntMinStack minStack = new StringAntMinStack();
        Random random = new Random(66);
        for (int i = 0; i < 10; i++) {
            String item = String.valueOf(random.nextInt(100));
            minStack.push(item);
            System.out.println("Push: " + item);
            boolean flag = random.nextBoolean();
            if (flag && !minStack.empty()) {
                try {
                    String min = minStack.min();
                    System.out.println("Peek Min: " + min);
                } catch (Exception e) {
                    System.err.println();
                }
            }
        }
    }
}
