package io.github.erfanpaya;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

        String description;
        double amount;
        String category;


        System.out.println("****************");
        System.out.println("* Expense Flow *");
        System.out.println("****************");
        System.out.println();

        while (true) {
            System.out.print("Enter expense description(or 'done' to finish): ");
            description = scanner.nextLine().trim();
            if (description.equalsIgnoreCase("done")) {
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
                amount = Double.parseDouble(scanner.nextLine());
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

        displayExpenses(manager);

        scanner.close();
    }

    public static void displayExpenses(ExpenseManager manager) {
        System.out.println("==================");
        System.out.println(" Expense Summary ");
        System.out.println("==================");

        for (int i = 0; i < manager.count(); i++) {
            Expense expense = manager.getExpenseByIndex(i);
            System.out.printf("%d. %s -> %s -> %.2f%n",
                    i + 1, expense.getDescription(), expense.getCategory(), expense.getAmount());
        }

        System.out.println("------------------");
        System.out.println("Total expenses: " + manager.count());
        System.out.printf("Total amount: %.2f%n", manager.calculateTotal());

        if (manager.count() == 0) {
            System.out.println("Average expense: N/A");
            System.out.println();
            System.out.println("No expenses recorded yet.");
            System.out.println("==================");
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