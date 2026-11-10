package io.github.erfanpaya.service;

import io.github.erfanpaya.model.Expense;

import java.time.LocalDate;
import java.util.*;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
    }

    public List<Expense> getExpensesByDescription(String description) {
        String keyword = description.toLowerCase(Locale.ROOT);
        List<Expense> expensesByDescription = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getDescription().toLowerCase(Locale.ROOT).contains(keyword)) {
                expensesByDescription.add(expense);
            }
        }

        return expensesByDescription;
    }

    public List<Expense> getExpensesByCategory(String category) {
        String keyword = category.toLowerCase(Locale.ROOT);
        List<Expense> expensesByCategory = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getCategory().toLowerCase(Locale.ROOT).equals(keyword)) {
                expensesByCategory.add(expense);
            }
        }

        return expensesByCategory;
    }

    public List<Expense> getExpensesSortedByDescription() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getDescription, String.CASE_INSENSITIVE_ORDER));
        return List.copyOf(sorted);
    }

    public List<Expense> getExpensesSortedByAmountAscending() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparingDouble(Expense::getAmount));

        return List.copyOf(sorted);
    }

    public List<Expense> getExpensesSortedByAmountDescending() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparingDouble(Expense::getAmount).reversed());

        return List.copyOf(sorted);
    }

    public int getExpenseCount() {
        return this.expenses.size();
    }

    public void addExpense(Expense expense) {
        Objects.requireNonNull(expense, "Expense cannot be null");
        this.expenses.add(expense);
    }

    public double calculateTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    public double calculateAverage() {
        if (expenses.isEmpty()) return 0;
        return calculateTotal() / getExpenseCount();
    }

    public Expense findMostExpensiveExpense() {
        if (expenses.isEmpty()) return null;
        Expense expensiveExpense = expenses.getFirst();

        for (Expense expense : expenses) {
            if (expense.getAmount() > expensiveExpense.getAmount()) {
                expensiveExpense = expense;
            }
        }

        return expensiveExpense;
    }

    public Expense findCheapestExpense() {
        if (expenses.isEmpty()) return null;
        Expense cheapestExpense = expenses.getFirst();

        for (Expense expense : expenses) {
            if (expense.getAmount() < cheapestExpense.getAmount()) {
                cheapestExpense = expense;
            }
        }

        return cheapestExpense;
    }
}
