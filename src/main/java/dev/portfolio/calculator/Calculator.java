package dev.portfolio.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Вычисления без зависимости от консоли: этот класс можно использовать в другом интерфейсе. */
public final class Calculator {
    private static final int DIVISION_SCALE = 10;

    public BigDecimal calculate(BigDecimal left, Operation operation, BigDecimal right) {
        Objects.requireNonNull(left, "Первое число не должно быть null");
        Objects.requireNonNull(operation, "Операция не должна быть null");
        Objects.requireNonNull(right, "Второе число не должно быть null");

        return switch (operation) {
            case ADD -> left.add(right);
            case SUBTRACT -> left.subtract(right);
            case MULTIPLY -> left.multiply(right);
            case DIVIDE -> divide(left, right);
        };
    }

    private BigDecimal divide(BigDecimal left, BigDecimal right) {
        // signum() распознаёт любой вариант нуля, в том числе 0.00 и -0.0.
        if (right.signum() == 0) {
            throw new ArithmeticException("Деление на ноль запрещено.");
        }
        return left.divide(right, DIVISION_SCALE, RoundingMode.HALF_UP);
    }
}
