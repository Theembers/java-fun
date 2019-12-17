package me.theembers.homework.subject3.operator;

import java.math.BigDecimal;

/**
 * 减法
 */
public class SubtractOperator implements Operator {
    @Override
    public BigDecimal operate(BigDecimal num1, BigDecimal num2) {
        return num1.subtract(num2);
    }
}
