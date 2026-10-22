package dev.portfolio.calculator;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/** Диалог с пользователем и история текущего запуска. */
public final class ConsoleCalculator {
    private final Calculator calculator;
    private final Scanner input;
    private final PrintStream output;
    private final List<String> history = new ArrayList<>();

    public ConsoleCalculator(Calculator calculator, Scanner input, PrintStream output) {
        this.calculator = calculator;
        this.input = input;
        this.output = output;
    }

    public void run() {
        output.println("=== Java Console Calculator ===");
        printHelp();

        while (true) {
            String command = readLine("\nОперация (+, -, *, /) или h/help/c/q: ");
            if (isExit(command)) {
                break;
            }

            switch (command.toLowerCase(Locale.ROOT)) {
                case "help" -> printHelp();
                case "h" -> printHistory();
                case "c" -> {
                    history.clear();
                    output.println("История очищена.");
                }
                default -> {
                    Operation operation;
                    try {
                        operation = Operation.fromSymbol(command);
                    } catch (IllegalArgumentException exception) {
                        output.println(exception.getMessage());
                        continue;
                    }

                    BigDecimal left = readNumber("Первое число: ");
                    if (left == null) {
                        output.println("\nДо встречи!");
                        return;
                    }
                    BigDecimal right = readNumber("Второе число: ");
                    if (right == null) {
                        output.println("\nДо встречи!");
                        return;
                    }
                    calculateAndPrint(left, operation, right);
                }
            }
        }

        output.println("\nДо встречи!");
    }

    private void calculateAndPrint(BigDecimal left, Operation operation, BigDecimal right) {
        try {
            BigDecimal result = calculator.calculate(left, operation, right);
            String entry = DecimalNumbers.format(left) + " " + operation.getSymbol() + " "
                    + DecimalNumbers.format(right) + " = " + DecimalNumbers.format(result);
            history.add(entry);
            output.println("Результат: " + entry);
        } catch (ArithmeticException exception) {
            output.println("Ошибка: " + exception.getMessage());
        }
    }

    private BigDecimal readNumber(String prompt) {
        while (true) {
            String text = readLine(prompt);
            if (isExit(text)) {
                return null;
            }
            try {
                return DecimalNumbers.parse(text);
            } catch (IllegalArgumentException exception) {
                output.println(exception.getMessage());
            }
        }
    }

    private String readLine(String prompt) {
        output.print(prompt);
        output.flush();
        // Конец потока (EOF) завершает приложение без исключения и бесконечного цикла.
        return input.hasNextLine() ? input.nextLine().strip() : null;
    }

    private boolean isExit(String text) {
        return text == null || text.equalsIgnoreCase("q");
    }

    private void printHistory() {
        if (history.isEmpty()) {
            output.println("История пока пуста.");
            return;
        }
        output.println("История вычислений:");
        for (int i = 0; i < history.size(); i++) {
            output.println((i + 1) + ". " + history.get(i));
        }
    }

    private void printHelp() {
        output.println("Выбери операцию, затем введи два числа по очереди.");
        output.println("Дроби: 12.5 или 12,5. Деление округляется до 10 знаков после запятой.");
        output.println("В меню: h — история, c — очистить историю, help — справка.");
        output.println("q — выход на любом шаге. История хранится только до закрытия программы.");
    }
}
