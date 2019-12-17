package me.theembers.homework.subject3;


public class MainClient {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        String expression1 = "3*0+3+8+9*1";
        System.out.println(expression1 + "= " + calculator.execute(expression1));

        String expression2 = "3+(3-0)*2";
        System.out.println((expression2 + "= " + calculator.execute(expression2)));
    }
}
