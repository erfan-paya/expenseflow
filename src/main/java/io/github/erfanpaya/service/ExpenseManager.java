package io.github.erfanpaya.service;

import io.github.erfanpaya.model.Expense;

import java.util.ArrayList;

public class ExpenseManager {
    private final ArrayList<Expense> expenses;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
    }

    public Expense getExpenseByIndex(int index) {
        return expenses.get(index);
    }

    public int count() {
        return this.expenses.size();
    }

    public void add(Expense expense) {
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
        return calculateTotal() / count();
    }

    public int findMostExpensiveExpenseIndex() {
        if (expenses.isEmpty()) return -1;
        double expensiveAmount = expenses.getFirst().getAmount();
        int index = 0;

        for (int i = 1; i < expenses.size(); i++) {
            if (expenses.get(i).getAmount() > expensiveAmount) {
                expensiveAmount = expenses.get(i).getAmount();
                index = i;
            }
        }

        return index;
    }

    public int findCheapestExpenseIndex() {
        if (expenses.isEmpty()) return -1;
        double cheapestAmount = expenses.getFirst().getAmount();
        int index = 0;
        for (int i = 1; i < expenses.size(); i++) {
            if (expenses.get(i).getAmount() < cheapestAmount) {
                cheapestAmount = expenses.get(i).getAmount();
                index = i;
            }
        }

        return index;
    }
}
