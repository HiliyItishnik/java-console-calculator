package dev.portfolio.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationTest {
    @ParameterizedTest
    @CsvSource({"+, ADD", "-, SUBTRACT", "*, MULTIPLY", "/, DIVIDE"})
    void recognizesSupportedSymbols(String symbol, Operation expected) {
        assertEquals(expected, Operation.fromSymbol(symbol));
    }

    @ParameterizedTest
    @ValueSource(strings = {"%", "^", "abc"})
    void rejectsUnsupportedOperations(String symbol) {
        assertThrows(IllegalArgumentException.class, () -> Operation.fromSymbol(symbol));
    }
}
