package me.theembers.homework.subject3.operator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


/**
 * 运算上下文
 */
public class OperatorContext {
    // 运算符方法集
    private static final Map<String, Operator> OPERATOR_MAP = new HashMap<>();

    static {
        OPERATOR_MAP.put("+", new AddOperator());
        OPERATOR_MAP.put("-", new SubtractOperator());
        OPERATOR_MAP.put("*", new MultiplyOperator());
        OPERATOR_MAP.put("/", new DivideOperator());
    }


    // 运算符优先级map
    private static final Map<String, Integer> OPERATOR_PRIORITY_MAP = new HashMap<>();

    static {
        OPERATOR_PRIORITY_MAP.put("(", 0);
        OPERATOR_PRIORITY_MAP.put("+", 1);
        OPERATOR_PRIORITY_MAP.put("-", 1);
        OPERATOR_PRIORITY_MAP.put("*", 2);
        OPERATOR_PRIORITY_MAP.put("/", 2);
        OPERATOR_PRIORITY_MAP.put(")", 3);
    }

    /**
     * return -1 第二个运算符级别低
     * return 0 表示两个运算符同级别
     * return 1 第二个运算符级别高
     *
     * @param opt1
     * @param opt2
     * @return
     */
    public int checkPriority(String opt1, String opt2) {
        return OPERATOR_PRIORITY_MAP.get(opt2) - OPERATOR_PRIORITY_MAP.get(opt1);
    }


    public BigDecimal operate(String operate, BigDecimal num1, BigDecimal num2) {
        if (OPERATOR_MAP.containsKey(operate)) {
            return OPERATOR_MAP.get(operate).operate(num1, num2);
        }
        throw new RuntimeException("no operate can be used by this " + operate);
    }
}
