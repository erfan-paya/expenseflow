package io.github.erfanpaya.cli;

import io.github.erfanpaya.model.Expense;
import io.github.erfanpaya.service.ExpenseManager;

import java.util.Scanner;

public class ExpenseFlowCli {
    private static final String ADD_EXPENSE = "1";
    private static final String VIEW_ALL = "2";
    private static final String VIEW_SUMMARY = "3";
    private static final String EXIT = "4";

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
        menuLoop();
    }

    private void menuLoop() {
        boolean running = true;
        while (running) {
            displayMenu();

            String choice = scanner.nextLine().trim();

            running = handleChoice(choice);
        }

        System.out.println("Thank you for using ExpenseFlow!");
    }

    private boolean handleChoice(String choice) {
        switch (choice) {
            case ADD_EXPENSE -> addExpense();
            case VIEW_ALL -> displayAllExpenses();
            case VIEW_SUMMARY -> displaySummary();
            case EXIT -> {
                return false;
            }
            default -> System.out.println("Error: invalid choice, please enter 1-4");
        }
        return true;
    }

    private void addExpense() {
        String description = readNonBlank("Enter expense description: ", "description");
        String category = readNonBlank("Enter expense category: ", "category");
        double amount = readAmount();

        manager.addExpense(new Expense(description, category, amount));
        System.out.println("Expense added successfully!");
        System.out.println();
    }

    // ---------- Input helpers ----------

    private String readNonBlank(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isBlank()) {
                return value;
            }
            System.out.println("Error: " + fieldName + " cannot be empty");
        }
    }

    private double readAmount() {
        while (true) {
            System.out.print("Enter expense amount: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount > 0 && Double.isFinite(amount)) {
                    return amount;
                }
                System.out.println("Error: amount must be a positive number");
            } catch (NumberFormatException e) {
                System.out.println("Error: amount must be a number");
            }
        }
    }

    // ---------- Output helpers ----------
    private void displayWelcomeBanner() {
        System.out.println(STARS);
        System.out.println("* Expense Flow *");
        System.out.println(STARS);
        System.out.println();
    }

    private void displayMenu() {
        System.out.println(ADD_EXPENSE + ". Add Expense");
        System.out.println(VIEW_ALL + ". View All Expenses");
        System.out.println(VIEW_SUMMARY + ". View Expense Summary");
        System.out.println(EXIT + ". Exit");
        System.out.print("Choose an option: ");
    }

    private void displayExpenses() {
        int index = 1;

        for (Expense expense : manager.getExpenses()) {
            System.out.printf("%d. %s%n", index++, expense);
        }
    }

    private void displayAllExpenses() {
        System.out.println(BORDER);
        System.out.println(" All Expenses ");
        System.out.println(BORDER);

        if (manager.getExpenseCount() == 0) {
            System.out.println("No expenses recorded yet.");
        }

        displayExpenses();

        System.out.println(BORDER);
        System.out.println();
    }

    private void displaySummaryHeader() {
        System.out.println(BORDER);
        System.out.println(" Expense Summary ");
        System.out.println(BORDER);
    }

    private void displayExpenseStatistics() {
        System.out.println("Total expenses: " + manager.getExpenseCount());
        System.out.printf("Total amount: %.2f%n", manager.calculateTotal());
    }

    private void displayMostAndCheapestExpenses() {
        Expense mostExpensiveExpense = manager.findMostExpensiveExpense();
        Expense cheapestExpense = manager.findCheapestExpense();

        System.out.printf("Most expensive: %s%n", mostExpensiveExpense);
        System.out.printf("Cheapest: %s%n", cheapestExpense);
    }

    private void displaySummary() {
        displaySummaryHeader();

        if (manager.getExpenseCount() == 0) {
            displayExpenseStatistics();
            System.out.println("Average expense: N/A");
            System.out.println("No expenses recorded yet.");
            System.out.println(BORDER);
            return;
        }

        displayExpenses();
        System.out.println(SEPARATOR);

        displayExpenseStatistics();
        System.out.printf("Average expense: %.2f%n", manager.calculateAverage());

        displayMostAndCheapestExpenses();
        System.out.println(BORDER);
    }
}
