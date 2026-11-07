package io.github.erfanpaya;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> descriptions = new ArrayList<>();
        ArrayList<Double> amounts = new ArrayList<>();
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

            descriptions.add(description);
            amounts.add(amount);
            System.out.println("Expense added successfully!");
            System.out.println();
        }

        displayExpenses(descriptions, amounts);

        scanner.close();
    }

    public static double calculateTotal(ArrayList<Double> amounts) {
        double total = 0;
        for (Double amount : amounts) {
            total += amount;
        }

        return total;
    }

    public static double calculateAverage(ArrayList<Double> amounts) {
        if (amounts.isEmpty()) return 0;
        return calculateTotal(amounts) / amounts.size();
    }

    public static int findMostExpensiveExpenseIndex(ArrayList<Double> amounts) {
        if (amounts.isEmpty()) return -1;
        double expensiveAmount = amounts.getFirst();
        int index = 0;

        for (int i = 0; i < amounts.size(); i++) {
            if (amounts.get(i) > expensiveAmount) {
                expensiveAmount = amounts.get(i);
                index = i;
            }
        }

        return index;
    }

    public static int findCheapestExpenseIndex(ArrayList<Double> amounts) {
        if (amounts.isEmpty()) return -1;
        double cheapestAmount = amounts.getFirst();
        int index = 0;
        for (int i = 0; i < amounts.size(); i++) {
            if (amounts.get(i) < cheapestAmount) {
                cheapestAmount = amounts.get(i);
                index = i;
            }
        }

        return index;
    }

    public static void displayExpenses(ArrayList<String> descriptions, ArrayList<Double> amounts) {
        System.out.println("==================");
        System.out.println(" Expense Summary ");
        System.out.println("==================");

        for (int i = 0; i < descriptions.size(); i++) {
            System.out.printf("%d. %s -> %.2f%n",
                    i + 1, descriptions.get(i), amounts.get(i));
        }

        System.out.println("------------------");
        System.out.println("Total expenses: " + descriptions.size());
        System.out.printf("Total amount: %.2f%n", calculateTotal(amounts));

        if (amounts.isEmpty()) {
            System.out.println("Average expense: N/A");
            System.out.println();
            System.out.println("No expenses recorded yet.");
            System.out.println("==================");
            return;
        }
        System.out.printf("Average expense: %.2f%n", calculateAverage(amounts));

        int mostExpensiveIndex = findMostExpensiveExpenseIndex(amounts);
        int cheapestIndex = findCheapestExpenseIndex(amounts);
        System.out.println();
        System.out.printf("Most expensive: %s -> %.2f%n", descriptions.get(mostExpensiveIndex), amounts.get(mostExpensiveIndex));
        System.out.printf("Cheapest: %s -> %.2f%n", descriptions.get(cheapestIndex), amounts.get(cheapestIndex));
        System.out.println("==================");
    }
}