package io.github.erfanpaya.cli;

import io.github.erfanpaya.model.Expense;
import io.github.erfanpaya.service.ExpenseManager;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class ExpenseFlowCli {
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

    private void sortMenuLoop() {
        boolean running = true;
        while (running) {
            displaySortMenu();

            String choice = scanner.nextLine().trim();

            running = handleSortChoice(choice);
        }
    }

    private boolean handleChoice(String choice) {
        MainMenuOption option = MainMenuOption.fromOption(choice);

        if (option == null) {
            System.out.println("Error: invalid choice. Please try again.");
            return true;
        }

        switch (option) {
            case ADD_EXPENSE -> addExpense();
            case VIEW_ALL -> displayAllExpenses();
            case VIEW_SUMMARY -> displaySummary();
            case SEARCH_EXPENSES -> searchExpenses();
            case FILTER_EXPENSES_BY_CATEGORY -> filterExpensesByCategory();
            case VIEW_CATEGORY_SUMMARY -> displaySpendingByCategory();
            case FILTER_EXPENSES_BY_AMOUNT_RANGE -> displayExpenseByAmountRange();
            case SORT_EXPENSES -> sortMenuLoop();
            case EXIT -> {
                return false;
            }
        }
        return true;
    }

    private boolean handleSortChoice(String choice) {
        SortMenuOption option = SortMenuOption.fromOption(choice);

        if (option == null) {
            System.out.println("Error: invalid choice. Please try again.");
            return true;
        }

        switch (option) {
            case SORT_BY_DESCRIPTION -> displaySortedExpenses(manager.getExpensesSortedByDescription());
            case SORT_BY_AMOUNT_ASCENDING -> displaySortedExpenses(manager.getExpensesSortedByAmountAscending());
            case SORT_BY_AMOUNT_DESCENDING -> displaySortedExpenses(manager.getExpensesSortedByAmountDescending());
            case SORT_MENU_EXIT -> {
                return false;
            }
        }

        return true;
    }

    private void addExpense() {
        String description = readNonBlank("Enter expense description: ", "description");
        String category = readNonBlank("Enter expense category: ", "category");
        double amount = readAmount("Enter expense amount: ");

        manager.addExpense(new Expense(description, category, amount));
        System.out.println("Expense added successfully!");
        System.out.println();
    }

    private void searchExpenses() {
        String description = readNonBlank("Enter expense description: ", "description");
        List<Expense> selectedExpenses = manager.getExpensesByDescription(description);

        if (selectedExpenses.isEmpty()) {
            System.out.println("No expenses found matching: " + description);
            System.out.println();
            return;
        }

        displayExpenses(selectedExpenses);
        System.out.println();
    }

    private void filterExpensesByCategory() {
        String category = readNonBlank("Enter expense category: ", "category");
        List<Expense> selectedExpenses = manager.getExpensesByCategory(category);

        if (selectedExpenses.isEmpty()) {
            System.out.println("No expenses found matching: " + category);
            System.out.println();
            return;
        }

        displayExpenses(selectedExpenses);
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

    private double readAmount(String prompt) {
        while (true) {
            System.out.print(prompt);
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

    private void displayHeader(String header) {
        System.out.println(BORDER);
        System.out.println(header);
        System.out.println(BORDER);
    }

    private void displayMenu() {
        MainMenuOption[] options = MainMenuOption.values();

        for (MainMenuOption option : options) {
            System.out.println(option.getOption() + ". " + option.getLabel());
        }

        System.out.print("Choose an option: ");
    }

    private void displaySortMenu() {
        displayHeader(" Sort Expenses ");

        SortMenuOption[] options = SortMenuOption.values();
        for (SortMenuOption option : options) {
            System.out.println(option.getOption() + ". " + option.getLabel());
        }

        System.out.print("Choose an option: ");
    }

    private void displayExpenses(List<Expense> expenses) {
        int index = 1;

        for (Expense expense : expenses) {
            System.out.printf("%d. %s%n", index++, expense);
        }
    }

    private void displayAllExpenses() {
        displayHeader(" All Expenses ");

        if (manager.getExpenseCount() == 0) {
            System.out.println("No expenses recorded yet.");
        }

        displayExpenses(manager.getExpenses());

        System.out.println(BORDER);
        System.out.println();
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
        displayHeader(" Expenses Summary ");

        if (manager.getExpenseCount() == 0) {
            displayExpenseStatistics();
            System.out.println("Average expense: N/A");
            System.out.println("No expenses recorded yet.");
            System.out.println(BORDER);
            return;
        }

        displayExpenses(manager.getExpenses());
        System.out.println(SEPARATOR);

        displayExpenseStatistics();
        System.out.printf("Average expense: %.2f%n", manager.calculateAverage());

        displayMostAndCheapestExpenses();
        System.out.println(BORDER);
    }

    private void displaySpendingByCategory() {
        displayHeader(" Spending by Category ");

        Map<String, Double> spendingByCategory = manager.getSpendingByCategory();

        if (spendingByCategory.isEmpty()) {
            System.out.println("No spending expenses found.");
            System.out.println(BORDER);
            return;
        }

        for (Map.Entry<String, Double> entry : spendingByCategory.entrySet()) {
            String capKey = entry.getKey().substring(0, 1).toUpperCase(Locale.ROOT) + entry.getKey().substring(1);
            System.out.printf("%s: %.2f%n", capKey, entry.getValue());
        }

        System.out.println(SEPARATOR);
        System.out.printf("Total spending: %.2f%n", manager.calculateTotal());
        System.out.println(BORDER);
    }

    private void displayExpenseByAmountRange() {
        displayHeader(" Expenses by Amount Range ");

        while (true) {
            double min = readAmount("Enter minimum amount: ");
            double max = readAmount("Enter maximum amount: ");

            try {
                List<Expense> expenses = manager.getExpensesByAmountRange(min, max);

                if (expenses.isEmpty()) {
                    System.out.println("No expenses found in this amount range.");
                } else {
                    displayExpenses(expenses);
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(BORDER);
    }

    private void displaySortedExpenses(List<Expense> expenses) {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        displayExpenses(expenses);
    }

    private enum MainMenuOption {
        ADD_EXPENSE("1", "Add Expense"),
        VIEW_ALL("2", "View all Expenses"),
        VIEW_SUMMARY("3", "View Expenses Summary"),
        SEARCH_EXPENSES("4", "Search Expenses"),
        FILTER_EXPENSES_BY_CATEGORY("5", "Filter Expenses by Category"),
        VIEW_CATEGORY_SUMMARY("6", "View Category Summary"),
        FILTER_EXPENSES_BY_AMOUNT_RANGE("7", "Filter Expenses by Amount Range"),
        SORT_EXPENSES("8", "Sort Expenses"),
        EXIT("0", "Exit");

        private final String option;
        private final String label;

        MainMenuOption(String option, String label) {
            this.option = option;
            this.label = label;
        }

        private static MainMenuOption fromOption(String option) {
            MainMenuOption[] options = MainMenuOption.values();

            for (MainMenuOption opt : options) {
                if (opt.getOption().equals(option)) {
                    return opt;
                }
            }

            return null;
        }

        private String getOption() {
            return option;
        }

        private String getLabel() {
            return label;
        }
    }

    private enum SortMenuOption {
        SORT_BY_DESCRIPTION("1", "Sort Expenses by Description"),
        SORT_BY_AMOUNT_ASCENDING("2", "Sort Expenses by Amount Ascending"),
        SORT_BY_AMOUNT_DESCENDING("3", "Sort Expenses by Amount Descending"),
        SORT_MENU_EXIT("0", "Back to menu");

        private final String option;
        private final String label;

        SortMenuOption(String option, String label) {
            this.option = option;
            this.label = label;
        }

        private static SortMenuOption fromOption(String option) {
            SortMenuOption[] options = SortMenuOption.values();

            for (SortMenuOption opt : options) {
                if (opt.getOption().equals(option)) {
                    return opt;
                }
            }

            return null;
        }

        private String getOption() {
            return option;
        }

        private String getLabel() {
            return label;
        }
    }
}
