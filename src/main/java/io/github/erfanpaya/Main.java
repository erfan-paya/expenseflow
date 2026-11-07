package io.github.erfanpaya;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        ExpenseFlowCli cli = new ExpenseFlowCli(scanner, manager);
        cli.run();
        scanner.close();
    }

}