package me.theembers.homework.subject3;


import me.theembers.homework.subject3.operator.OperatorContext;

public class MainClient {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(new OperatorContext());

        String expression1 = "3*0+3+8+9*1";
        System.out.println(expression1 + "= " + calculator.execute(expression1));

        String expression2 = "3+(3-0)*2";
        System.out.println((expression2 + "= " + calculator.execute(expression2)));


        String expression3 = "3+(3-0)*2/1-2";
        System.out.println((expression3 + "= " + calculator.execute(expression3)));

        String expression4 = "2*3+(1+1)+2*3";
        System.out.println((expression4 + "= " + calculator.execute(expression4)));
    }
}
