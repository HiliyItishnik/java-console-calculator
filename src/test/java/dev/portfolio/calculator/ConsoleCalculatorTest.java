package dev.portfolio.calculator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleCalculatorTest {
    @Test
    void calculatesRepeatedlyAndShowsHistoryInOrder() {
        String output = runSession("+\n0,1\n0.2\n*\n-2\n4\nh\nq\n");

        assertTrue(output.contains("Результат: 0.1 + 0.2 = 0.3"));
        assertTrue(output.contains("Результат: -2 * 4 = -8"));
        int first = output.indexOf("1. 0.1 + 0.2 = 0.3");
        int second = output.indexOf("2. -2 * 4 = -8");
        assertTrue(first >= 0 && second > first);
    }

    @Test
    void recoversFromInvalidOperationAndNumber() {
        String output = runSession("%\n+\nabc\n\n2\n3\nq\n");

        assertTrue(output.contains("Неизвестная операция"));
        assertTrue(output.contains("Введите число"));
        assertTrue(output.contains("Результат: 2 + 3 = 5"));
    }

    @Test
    void rejectsDivisionByZeroWithoutAddingItToHistoryAndContinues() {
        String output = runSession("/\n10\n-0,00\nh\n/\n5\n2\nh\nq\n");

        assertTrue(output.contains("Ошибка: Деление на ноль запрещено."));
        assertTrue(output.contains("История пока пуста."));
        assertTrue(output.contains("1. 5 / 2 = 2.5"));
        assertFalse(output.contains("1. 10 /"));
    }

    @Test
    void clearsHistory() {
        String output = runSession("+\n1\n2\nc\nh\nq\n");
        String afterClear = output.substring(output.indexOf("История очищена."));

        assertTrue(afterClear.contains("История пока пуста."));
        assertFalse(afterClear.contains("1 + 2 = 3"));
    }

    @Test
    void startsWithEmptyHistoryAndAcceptsUppercaseCommands() {
        String output = runSession("H\nHELP\nQ\n");

        assertTrue(output.contains("История пока пуста."));
        assertFalse(output.contains("Неизвестная операция"));
        assertTrue(output.contains("До встречи!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"q\n", "+\nq\n", "+\n1\nQ\n"})
    void quitsAtEveryPrompt(String input) {
        String output = runSession(input);

        assertTrue(output.contains("До встречи!"));
        assertFalse(output.contains("Результат:"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "+\n", "+\n1\n"})
    void endsCleanlyAtEndOfInput(String input) {
        String output = runSession(input);

        assertTrue(output.contains("До встречи!"));
        assertFalse(output.contains("Результат:"));
    }

    @Test
    void newSessionDoesNotReuseOldHistory() {
        runSession("+\n1\n2\nq\n");
        String output = runSession("h\nq\n");

        assertTrue(output.contains("История пока пуста."));
        assertFalse(output.contains("1 + 2 = 3"));
    }

    private String runSession(String input) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (Scanner scanner = new Scanner(input);
             PrintStream output = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            new ConsoleCalculator(new Calculator(), scanner, output).run();
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }
}
