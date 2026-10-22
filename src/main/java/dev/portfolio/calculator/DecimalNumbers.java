package dev.portfolio.calculator;

import java.math.BigDecimal;

/** Преобразование пользовательского ввода и удобное отображение результата. */
public final class DecimalNumbers {
    private DecimalNumbers() {
    }

    public static BigDecimal parse(String text) {
        if (text == null) {
            throw new IllegalArgumentException("Введите число, например 12.5 или -3,2.");
        }

        String normalized = text.strip().replace(',', '.');
        // Только обычная десятичная запись: без экспоненты и разделителей тысяч.
        if (!normalized.matches("[+-]?(?:[0-9]+(?:\\.[0-9]*)?|\\.[0-9]+)")) {
            throw new IllegalArgumentException("Введите число, например 12.5 или -3,2.");
        }
        // Конструктор из строки сохраняет десятичное значение без промежуточного double.
        return new BigDecimal(normalized);
    }

    public static String format(BigDecimal number) {
        return number.stripTrailingZeros().toPlainString();
    }
}
