package io.github.erfanpaya;

import java.util.Scanner;

public class ExpenseFlowCli {
    private static final String EXIT_COMMAND = "done";
    private static final String STARS = "****************";
    private static final String BORDER = "==================";
    private static final String SEPARATOR = "------------------";

    private final Scanner scanner;
    private final ExpenseManager manager;

    public ExpenseFlowCli(Scanner scanner, ExpenseManager manager) {
        this.scanner = scanner;
        this.manager = manager;
    }

    public void run() {
        displayWelcomeBanner();
        collectExpense();
        displaySummary();
    }

    private void collectExpense() {
        String description;
        String category;
        double amount;
        while (true) {
            System.out.print("Enter expense description(or 'done' to finish): ");
            description = scanner.nextLine().trim();
            if (description.equalsIgnoreCase(EXIT_COMMAND)) {
                break;
            }

            if (description.isBlank()) {
                System.out.println("Error: description cannot be empty");
                continue;
            }

            System.out.print("Enter expense category: ");
            category = scanner.nextLine().trim();
            if (category.isBlank()) {
                System.out.println("Error: category cannot be empty");
                continue;
            }

            System.out.print("Enter expense amount: ");
            try {
                amount = Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: amount must be a number");
                continue;
            }


            if (amount <= 0 || Double.isInfinite(amount) || Double.isNaN(amount)) {
                System.out.println("Error: amount must be a positive number");
                continue;
            }

            manager.add(new Expense(description, category, amount));
            System.out.println("Expense added successfully!");
            System.out.println();
        }
    }

    private void displayWelcomeBanner() {
        System.out.println(STARS);
        System.out.println("* Expense Flow *");
        System.out.println(STARS);
        System.out.println();
    }

    private void displaySummary() {
        System.out.println(BORDER);
        System.out.println(" Expense Summary ");
        System.out.println(BORDER);

        for (int i = 0; i < manager.count(); i++) {
            Expense expense = manager.getExpenseByIndex(i);
            System.out.printf("%d. %s -> %s -> %.2f%n",
                    i + 1, expense.getDescription(), expense.getCategory(), expense.getAmount());
        }

        System.out.println(SEPARATOR);
        System.out.println("Total expenses: " + manager.count());
        System.out.printf("Total amount: %.2f%n", manager.calculateTotal());

        if (manager.count() == 0) {
            System.out.println("Average expense: N/A");
            System.out.println();
            System.out.println("No expenses recorded yet.");
            System.out.println(BORDER);
            return;
        }
        System.out.printf("Average expense: %.2f%n", manager.calculateAverage());


        int mostExpensiveIndex = manager.findMostExpensiveExpenseIndex();
        int cheapestIndex = manager.findCheapestExpenseIndex();
        Expense mostExpensiveExpense = manager.getExpenseByIndex(mostExpensiveIndex);
        Expense cheapestExpense = manager.getExpenseByIndex(cheapestIndex);
        System.out.println();
        System.out.printf("Most expensive: %s -> %s -> %.2f%n",
                mostExpensiveExpense.getDescription(),
                mostExpensiveExpense.getCategory(),
                mostExpensiveExpense.getAmount());
        System.out.printf("Cheapest: %s -> %s -> %.2f%n",
                cheapestExpense.getDescription(),
                cheapestExpense.getCategory(),
                cheapestExpense.getAmount());
        System.out.println("==================");
    }
}
