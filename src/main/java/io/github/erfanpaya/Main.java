package io.github.erfanpaya;

import io.github.erfanpaya.cli.ExpenseFlowCli;
import io.github.erfanpaya.service.ExpenseManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            ExpenseManager manager = new ExpenseManager();
            ExpenseFlowCli cli = new ExpenseFlowCli(scanner, manager);
            cli.run();
        }
    }
}