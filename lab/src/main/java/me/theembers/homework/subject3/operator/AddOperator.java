package me.theembers.homework.subject3.operator;

import java.math.BigDecimal;

/**
 * 加法
 */
public class AddOperator implements Operator {
    @Override
    public BigDecimal operate(BigDecimal num1, BigDecimal num2) {
        return num1.add(num2);
    }
}
