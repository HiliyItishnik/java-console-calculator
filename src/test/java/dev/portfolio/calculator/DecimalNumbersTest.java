package dev.portfolio.calculator;

import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DecimalNumbersTest {
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "12.5 | 12.5",
            "-3,2 | -3.2",
            "+7 | 7",
            ".5 | 0.5",
            ",5 | 0.5",
            "12. | 12",
            "0.10000000000000001 | 0.10000000000000001"
    })
    void acceptsDecimalInput(String input, String expected) {
        assertEquals(0, new BigDecimal(expected).compareTo(DecimalNumbers.parse(input)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"  12,5  ", "\t12.5\t", "\u200312.5\u2003"})
    void ignoresSurroundingWhitespace(String input) {
        assertEquals(new BigDecimal("12.5"), DecimalNumbers.parse(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "abc", "NaN", "Infinity", "1,2.3", "1 000", "1e5", "+", "."})
    void rejectsInvalidInput(String input) {
        assertThrows(IllegalArgumentException.class, () -> DecimalNumbers.parse(input));
    }

    @ParameterizedTest
    @CsvSource({"3.000, 3", "0.000, 0", "-0.0, 0", "1000.00, 1000", "-2.5000, -2.5"})
    void formatsWithoutTrailingZerosOrScientificNotation(String input, String expected) {
        assertEquals(expected, DecimalNumbers.format(new BigDecimal(input)));
    }
}
