package io.github.erfanpaya;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> descrptions = new ArrayList<>();
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

            descrptions.add(description.trim());
            amounts.add(amount);
            System.out.println("Expense added successfully!");
            System.out.println();
        }

        displayExpenses(descrptions, amounts);

        scanner.close();
    }

    public static double calculateTotal(ArrayList<Double> amounts) {
        double total = 0;
        for (Double amount : amounts) {
            total += amount;
        }

        return total;
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
        System.out.println("==================");
    }
}