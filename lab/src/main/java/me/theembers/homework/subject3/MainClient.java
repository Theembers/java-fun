package me.theembers.homework.subject3;


import me.theembers.homework.subject3.operator.OperatorContext;
/**
 * 题目三：
 * 设计数据结构与算法，计算算数表达式，需要支持基本计算，加减乘除，满足计算优先级例如输入 3*0+3+8+9*1 输出20。括号，支持括号，例如输入 3+（3-0）*2 输出 9假设所有的数字均为整数，无需考虑精度问题。
 * 要求：
 * 1.代码结构清晰
 * 2.数据结构选型合理。
 * 3.不能使用现成的引擎
 */
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
