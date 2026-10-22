package dev.portfolio.calculator;

import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {
    private final Calculator calculator = new Calculator();

    @ParameterizedTest(name = "{0} {1} {2} = {3}")
    @CsvSource({
            "2, ADD, 3, 5",
            "0.1, ADD, 0.2, 0.3",
            "-7, ADD, 2, -5",
            "3, SUBTRACT, 8, -5",
            "10.5, SUBTRACT, 0.25, 10.25",
            "-2, SUBTRACT, -3, 1",
            "1.2, MULTIPLY, 3, 3.6",
            "-4, MULTIPLY, -2.5, 10",
            "123, MULTIPLY, 0, 0",
            "5, DIVIDE, 2, 2.5",
            "1, DIVIDE, 3, 0.3333333333",
            "2, DIVIDE, 3, 0.6666666667",
            "-1, DIVIDE, 2048, -0.0004882813",
            "1, DIVIDE, 2048, 0.0004882813",
            "1, DIVIDE, 100000000000, 0",
            "0, DIVIDE, -2, 0"
    })
    void calculatesWithTheDocumentedRoundingPolicy(String left, Operation operation,
                                                  String right, String expected) {
        BigDecimal result = calculator.calculate(new BigDecimal(left), operation, new BigDecimal(right));

        // compareTo сравнивает значения: 2.5 и 2.5000000000 считаются равными.
        assertEquals(0, new BigDecimal(expected).compareTo(result));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "0.00", "-0.000"})
    void rejectsEveryRepresentationOfZeroAsDivisor(String zero) {
        assertThrows(ArithmeticException.class,
                () -> calculator.calculate(BigDecimal.ONE, Operation.DIVIDE, new BigDecimal(zero)));
    }
}
