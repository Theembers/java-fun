package me.theembers.homework.subject3;


import me.theembers.homework.subject3.operator.OperatorContext;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 设计数据结构与算法，计算算数表达式，需要支持基本计算，加减乘除，满足计算优先级例如输入 3*0+3+8+9*1 输出20。括号，支持括号，例如输入 3+（3-0）*2 输出 9假设所有的数字均为整数，无需考虑精度问题。
 * 要求：
 * 1.代码结构清晰
 * 2.数据结构选型合理。
 * 3.不能使用现成的引擎
 */
public class Calculator {

    // 表达式字符合法性校验正则模式
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[0-9\\.+-/*() ]+");


    private OperatorContext operatorContext;

    public Calculator(OperatorContext operationContext) {
        this.operatorContext = operationContext;
    }

    /**
     * 根据 输入的表达式 输出运算结果
     *
     * @param expression 表达式字符串
     * @return 计算结果
     */
    public double execute(String expression) {
        // 非空校验
        if (null == expression || "".equals(expression.trim())) {
            throw new IllegalArgumentException("expression couldn't be empty.");
        }

        // 表达式字符合法性校验
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("bad expression.");
        }

        // 运算符栈
        Stack<String> operateStack = new Stack<>();
        // 数值栈
        Stack<BigDecimal> numStack = new Stack<>();
        // 当前正在读取的数值字符缓存
        StringBuilder curNumCache = new StringBuilder();

        // 逐个读取字符 并根据运算符判断参与何种计算
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == ' ') {
                continue;
            }
            if ((c >= '0' && c <= '9') || c == '.') {
                // 持续读取一个数值的各个字符
                curNumCache.append(c);
            } else {
                if (curNumCache.length() > 0) {
                    // 如果数值字符缓存有值 说明之前读取的字符是数值 而且此时已经完整读取完一个数值 写入 numStack
                    numStack.push(new BigDecimal(curNumCache.toString()));
                    // 重置数值字符缓存
                    curNumCache.delete(0, curNumCache.length());
                }

                String operate = String.valueOf(c);
                if (operateStack.empty()) {
                    operateStack.push(operate);
                } else {
                    if (operate.equals("(")) {
                        // 当前运算符为左括号 直接入运算符栈
                        operateStack.push(operate);
                    } else if (operate.equals(")")) {
                        // 当前运算符为右括号 触发括号内的字表达式进行计算
                        doCalc(operateStack, numStack);
                    } else {
                        // 当前运算符为加减乘除之一，要与栈顶运算符比较，判断是否要进行一次二元计算
                        comparePriorityAndCalc(operateStack, numStack, operate);
                    }
                }
            }
        }
        if (curNumCache.length() > 0) {
            // 如果数值字符缓存有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值
            numStack.push(new BigDecimal(curNumCache.toString()));
        }
        doCalc(operateStack, numStack);
        return numStack.pop().doubleValue();
    }

    /**
     * 拿当前运算符和栈顶运算符对比，如果栈顶运算符优先级高于或同级于当前运算符，
     * 则执行一次二元运算（递归比较并计算），否则当前运算符入栈
     *
     * @param operateStack 运算符栈
     * @param numStack     数值栈
     * @param theOperate   当前运算符
     */
    private void comparePriorityAndCalc(Stack<String> operateStack, Stack<BigDecimal> numStack, String theOperate) {
        // 比较当前运算符和栈顶运算符的优先级
        String peekOperate = operateStack.peek();
        int priority = operatorContext.checkPriority(peekOperate, theOperate);
        if (priority == -1 || priority == 0) {
            // 当前参与计算运算符
            String operate = operateStack.pop();
            // 当前参与计算的两个数值
            BigDecimal num2 = numStack.pop();
            BigDecimal num1 = numStack.pop();


            BigDecimal bigDecimal = operatorContext.operate(operate, num1, num2);

            // 计算结果当做操作数入栈
            numStack.push(bigDecimal);
            // 运算完栈顶还有运算符，则还需要再次触发一次比较判断是否需要再次二元计算
            if (operateStack.empty()) {
                operateStack.push(theOperate);
            } else {
                comparePriorityAndCalc(operateStack, numStack, theOperate);
            }
        } else {
            // 当前运算符优先级高，则直接入栈
            operateStack.push(theOperate);
        }
    }

    /**
     * 遇到右括号执行的连续计算操作（递归计算）
     *
     * @param operateStack 运算符栈
     * @param numStack     数值栈
     */
    private void doCalc(Stack<String> operateStack, Stack<BigDecimal> numStack) {
        // 当前参与计算运算符
        String operate = operateStack.pop();
        // 当前参与计算的两个数值
        BigDecimal num2 = numStack.pop();
        BigDecimal num1 = numStack.pop();


        BigDecimal bigDecimal = operatorContext.operate(operate, num1, num2);

        // 计算结果当做操作数入栈
        numStack.push(bigDecimal);
        if (operateStack.empty()) {
            return;
        }
        if ("(".equals(operateStack.peek())) {
            // 括号类型则遇左括号停止计算，同时将左括号从栈中移除
            operateStack.pop();
        } else {
            doCalc(operateStack, numStack);
        }
    }


}

