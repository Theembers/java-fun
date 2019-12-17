package me.theembers.homework.subject3.operator;

import java.math.BigDecimal;

/**
 * 乘法
 */
public class MultiplyOperator implements Operator {
    @Override
    public BigDecimal operate(BigDecimal num1, BigDecimal num2) {
        return num1.multiply(num2);
    }
}
