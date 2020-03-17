package me.theembers.juc;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2020-03-16 16:54
 */
public class ForkJoinRecursiveActionTest {
    static final int SIZE = 10_000_000;
    static int[] array = randomArray();

    public static void main(String[] args) {

        int number = 9;

        System.out.println("数组中的初始元素： ");
        print();

        ArrayTransform mainTask = new ArrayTransform(array, number, 0, SIZE);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);

        System.out.println("并行计算之后的元素：");
        print();
    }

    static int[] randomArray() {
        int[] array = new int[SIZE];
        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextInt(100);
        }

        return array;
    }

    static void print() {
        for (int i = 0; i < 10; i++) {
            System.out.print(array[i] + ", ");
        }
        System.out.println();
    }
}
