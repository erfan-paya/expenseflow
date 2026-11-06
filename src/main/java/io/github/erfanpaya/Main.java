package io.github.erfanpaya;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String description;
        double amount;

        System.out.println("****************");
        System.out.println("* Expense Flow *");
        System.out.println("****************");
        System.out.println();

        System.out.print("Enter expense description: ");
        description = scanner.nextLine();

        if (description.isBlank()) {
            System.out.println("Error: description cannot be empty");
            return;
        }

        System.out.print("Enter expense amount: ");
        try {
            amount = Double.parseDouble(scanner.nextLine());
        }  catch (NumberFormatException e) {
            System.out.println("Error: amount must be a number");
            return;
        }


        if(amount <= 0 || Double.isInfinite(amount) || Double.isNaN(amount))  {
            System.out.println("Error: amount must be a positive number");
            return;
        }

        System.out.println("------------------");
        System.out.println("  Expense Detail  ");
        System.out.println("------------------");
        System.out.println("Description: " + description);
        System.out.printf("Amount: %.2f%n", amount);

        scanner.close();
    }
}