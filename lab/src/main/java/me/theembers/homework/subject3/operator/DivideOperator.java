package me.theembers.homework.subject3.operator;

import java.math.BigDecimal;

/**
 * 除法
 */
public class DivideOperator implements Operator {
    @Override
    public BigDecimal operate(BigDecimal num1, BigDecimal num2) {
        return num1.divide(num2);
    }
}
