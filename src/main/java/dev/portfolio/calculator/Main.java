package dev.portfolio.calculator;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/** Точка входа: связывает вычисления и консольный интерфейс. */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in, StandardCharsets.UTF_8)) {
            PrintStream output = new PrintStream(System.out, true, StandardCharsets.UTF_8);
            new ConsoleCalculator(new Calculator(), input, output).run();
        }
    }
}
