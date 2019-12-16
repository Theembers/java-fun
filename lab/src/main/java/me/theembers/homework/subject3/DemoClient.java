package me.theembers.homework.subject3;


public class DemoClient {
    public static void main(String[] args) {
        String expression1 = "3*0+3+8+9*1";
        System.out.println(expression1 + "= " + Calculator.executeExpression(expression1));

        String expression2 = "3+(3-0)*2";
        System.out.println((expression2 + "= " + Calculator.executeExpression(expression2)));
    }
}
