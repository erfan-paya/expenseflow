package io.github.erfanpaya;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Expense> expenses = new ArrayList<>();

        String description;
        double amount;


        System.out.println("****************");
        System.out.println("* Expense Flow *");
        System.out.println("****************");
        System.out.println();

        while (true) {
            System.out.print("Enter expense description(or 'done' to finish): ");
            description = scanner.nextLine();
            description = description.trim();
            if (description.equalsIgnoreCase("done")) {
                break;
            }

            if (description.isBlank()) {
                System.out.println("Error: description cannot be empty");
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

            expenses.add(new Expense(description, amount));
            System.out.println("Expense added successfully!");
            System.out.println();
        }

        displayExpenses(expenses);

        scanner.close();
    }

    public static double calculateTotal(ArrayList<Expense> expenses) {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    public static double calculateAverage(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) return 0;
        return calculateTotal(expenses) / expenses.size();
    }

    public static int findMostExpensiveExpenseIndex(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) return -1;
        double expensiveAmount = expenses.getFirst().getAmount();
        int index = 0;

        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getAmount() > expensiveAmount) {
                expensiveAmount = expenses.get(i).getAmount();
                index = i;
            }
        }

        return index;
    }

    public static int findCheapestExpenseIndex(ArrayList<Expense> expenses) {
        if (expenses.isEmpty()) return -1;
        double cheapestAmount = expenses.getFirst().getAmount();
        int index = 0;
        for (int i = 0; i < expenses.size(); i++) {
            if (expenses.get(i).getAmount() < cheapestAmount) {
                cheapestAmount = expenses.get(i).getAmount();
                index = i;
            }
        }

        return index;
    }

    public static void displayExpenses(ArrayList<Expense> expenses) {
        System.out.println("==================");
        System.out.println(" Expense Summary ");
        System.out.println("==================");

        for (int i = 0; i < expenses.size(); i++) {
            System.out.printf("%d. %s -> %.2f%n",
                    i + 1, expenses.get(i).getDescription(), expenses.get(i).getAmount());
        }

        System.out.println("------------------");
        System.out.println("Total expenses: " + expenses.size());
        System.out.printf("Total amount: %.2f%n", calculateTotal(expenses));

        if (expenses.isEmpty()) {
            System.out.println("Average expense: N/A");
            System.out.println();
            System.out.println("No expenses recorded yet.");
            System.out.println("==================");
            return;
        }
        System.out.printf("Average expense: %.2f%n", calculateAverage(expenses));

        int mostExpensiveIndex = findMostExpensiveExpenseIndex(expenses);
        int cheapestIndex = findCheapestExpenseIndex(expenses);
        System.out.println();
        System.out.printf("Most expensive: %s -> %.2f%n", expenses.get(mostExpensiveIndex).getDescription(), expenses.get(mostExpensiveIndex).getAmount());
        System.out.printf("Cheapest: %s -> %.2f%n", expenses.get(cheapestIndex).getDescription(), expenses.get(cheapestIndex).getAmount());
        System.out.println("==================");
    }
}