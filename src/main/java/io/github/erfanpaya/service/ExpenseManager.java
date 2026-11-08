package io.github.erfanpaya.service;

import io.github.erfanpaya.model.Expense;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    public List<Expense> getExpenses() {
        return List.copyOf(expenses);
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
