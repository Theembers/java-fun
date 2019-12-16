package me.theembers.homework.subject3;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    // 表达式字符合法性校验正则模式
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("[0-9\\.+-/*() ]+");

    // 运算符优先级map
    private static final Map<String, Integer> OPT_PRIORITY_MAP = new HashMap<>();

    static {
        OPT_PRIORITY_MAP.put("(", 0);
        OPT_PRIORITY_MAP.put("+", 2);
        OPT_PRIORITY_MAP.put("-", 2);
        OPT_PRIORITY_MAP.put("*", 3);
        OPT_PRIORITY_MAP.put("/", 3);
        OPT_PRIORITY_MAP.put(")", 7);
    }


    /**
     * 输入加减乘除表达式字符串，返回计算结果
     *
     * @param expression 表达式字符串
     * @return 返回计算结果
     */
    public static double executeExpression(String expression) {
        // 非空校验
        if (null == expression || "".equals(expression.trim())) {
            throw new IllegalArgumentException("expression couldn't be empty.");
        }

        // 表达式字符合法性校验
        Matcher matcher = EXPRESSION_PATTERN.matcher(expression);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("表达式含有非法字符！");
        }

        // 运算符栈
        Stack<String> optStack = new Stack<>();
        // 数值栈
        Stack<BigDecimal> numStack = new Stack<>();
        // 当前正在读取中的数值字符缓存
        StringBuilder curNumCache = new StringBuilder();

        // 逐个读取字符，并根据运算符判断参与何种计算
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
                    // 如果追加器有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值 写入 numStack
                    numStack.push(new BigDecimal(curNumCache.toString()));
                    // 重置数值字符缓存
                    curNumCache.delete(0, curNumCache.length());
                }

                String curOpt = String.valueOf(c);
                if (optStack.empty()) {
                    optStack.push(curOpt);
                } else {
                    if (curOpt.equals("(")) {
                        // 当前运算符为左括号，直接入运算符栈
                        optStack.push(curOpt);
                    } else if (curOpt.equals(")")) {
                        // 当前运算符为右括号，触发括号内的字表达式进行计算
                        directCalc(optStack, numStack, true);
                    } else {
                        // 当前运算符为加减乘除之一，要与栈顶运算符比较，判断是否要进行一次二元计算
                        comparePriorityAndCalc(optStack, numStack, curOpt);
                    }
                }
            }
        }
        if (curNumCache.length() > 0) {
            // 如果数值字符缓存有值，说明之前读取的字符是数值，而且此时已经完整读取完一个数值
            numStack.push(new BigDecimal(curNumCache.toString()));
        }
        directCalc(optStack, numStack, false);
        return numStack.pop().doubleValue();
    }

    /**
     * 拿当前运算符和栈顶运算符对比，如果栈顶运算符优先级高于或同级于当前运算符，
     * 则执行一次二元运算（递归比较并计算），否则当前运算符入栈
     *
     * @param optStack 运算符栈
     * @param numStack 数值栈
     * @param theOpt   当前运算符
     */
    private static void comparePriorityAndCalc(Stack<String> optStack, Stack<BigDecimal> numStack, String theOpt) {
        // 比较当前运算符和栈顶运算符的优先级
        String peekOpt = optStack.peek();
        int priority = checkPriority(peekOpt, theOpt);
        if (priority == -1 || priority == 0) {
            // 栈顶运算符优先级大或同级，触发一次二元运算
            String opt = optStack.pop(); // 当前参与计算运算符
            BigDecimal num2 = numStack.pop(); // 当前参与计算数值2
            BigDecimal num1 = numStack.pop(); // 当前参与计算数值1
            BigDecimal bigDecimal = doCalc(opt, num1, num2);

            // 计算结果当做操作数入栈
            numStack.push(bigDecimal);
            // 运算完栈顶还有运算符，则还需要再次触发一次比较判断是否需要再次二元计算
            if (optStack.empty()) {
                optStack.push(theOpt);
            } else {
                comparePriorityAndCalc(optStack, numStack, theOpt);
            }
        } else {
            // 当前运算符优先级高，则直接入栈
            optStack.push(theOpt);
        }
    }

    /**
     * 遇到右括号和等号执行的连续计算操作（递归计算）
     *
     * @param optStack  运算符栈
     * @param numStack  数值栈
     * @param isBracket true表示为括号类型计算
     */
    private static void directCalc(Stack<String> optStack, Stack<BigDecimal> numStack, boolean isBracket) {
        String opt = optStack.pop(); // 当前参与计算运算符
        BigDecimal num2 = numStack.pop(); // 当前参与计算数值2
        BigDecimal num1 = numStack.pop(); // 当前参与计算数值1
        BigDecimal bigDecimal = doCalc(opt, num1, num2);

        // 计算结果当做操作数入栈
        numStack.push(bigDecimal);

        if (isBracket) {
            if ("(".equals(optStack.peek())) {
                // 括号类型则遇左括号停止计算，同时将左括号从栈中移除
                optStack.pop();
            } else {
                directCalc(optStack, numStack, isBracket);
            }
        } else {
            if (!optStack.empty()) {
                // 等号类型只要栈中还有运算符就继续计算
                directCalc(optStack, numStack, isBracket);
            }
        }
    }

    /**
     * 执行计算
     */
    private static BigDecimal doCalc(String opt, BigDecimal bigDecimal1,
                                     BigDecimal bigDecimal2) {
        BigDecimal resultBigDecimal = new BigDecimal(0);
        switch (opt) {
            case "+":
                resultBigDecimal = bigDecimal1.add(bigDecimal2);
                break;
            case "-":
                resultBigDecimal = bigDecimal1.subtract(bigDecimal2);
                break;
            case "*":
                resultBigDecimal = bigDecimal1.multiply(bigDecimal2);
                break;
            case "/":
                resultBigDecimal = bigDecimal1.divide(bigDecimal2, 10, BigDecimal.ROUND_HALF_DOWN);
                break;
            default:
                break;
        }
        return resultBigDecimal;
    }

    /**
     * priority = 0 表示两个运算符同级别
     * priority = 1 第二个运算符级别高，负数则相反
     *
     * @param opt1
     * @param opt2
     * @return
     */
    private static int checkPriority(String opt1, String opt2) {
        int priority = OPT_PRIORITY_MAP.get(opt2) - OPT_PRIORITY_MAP.get(opt1);
        return priority;
    }
}

