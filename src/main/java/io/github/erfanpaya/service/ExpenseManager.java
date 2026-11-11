package io.github.erfanpaya.service;

import io.github.erfanpaya.model.Expense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

        return List.copyOf(expensesByDescription);
    }

    public List<Expense> getExpensesByCategory(String category) {
        String keyword = category.toLowerCase(Locale.ROOT);
        List<Expense> expensesByCategory = new ArrayList<>();

        for (Expense expense : expenses) {
            if (expense.getCategory().toLowerCase(Locale.ROOT).equals(keyword)) {
                expensesByCategory.add(expense);
            }
        }

        return List.copyOf(expensesByCategory);
    }

    public Map<String, BigDecimal> getSpendingByCategory() {
        Map<String, BigDecimal> spendingByCategory = new LinkedHashMap<>();

        for (Expense expense : expenses) {
            String category = expense.getCategory().toLowerCase(Locale.ROOT);
            BigDecimal expenseAmount = expense.getAmount();

            spendingByCategory.put(
                    category,
                    spendingByCategory.getOrDefault(category, BigDecimal.ZERO).add(expenseAmount)
            );
        }

        return Collections.unmodifiableMap(spendingByCategory);
    }

    public List<Expense> getExpensesSortedByDescription() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getDescription, String.CASE_INSENSITIVE_ORDER));
        return List.copyOf(sorted);
    }

    public List<Expense> getExpensesByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        if (minAmount.compareTo(maxAmount) > 0) {
            throw new IllegalArgumentException(
                    "minAmount (" + minAmount + ") must not be greater than maxAmount (" + maxAmount + ")");
        }

        List<Expense> result = new ArrayList<>();
        for (Expense expense : expenses) {
            BigDecimal amount = expense.getAmount();
            if (amount.compareTo(minAmount) >= 0 && amount.compareTo(maxAmount) <= 0) {
                result.add(expense);
            }
        }

        return List.copyOf(result);
    }

    public List<Expense> getExpensesSortedByAmountAscending() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getAmount));

        return List.copyOf(sorted);
    }

    public List<Expense> getExpensesSortedByAmountDescending() {
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::getAmount).reversed());

        return List.copyOf(sorted);
    }

    public int getExpenseCount() {
        return this.expenses.size();
    }

    public void addExpense(Expense expense) {
        Objects.requireNonNull(expense, "Expense cannot be null");
        this.expenses.add(expense);
    }

    public boolean updateExpense(UUID id, String description, String category, BigDecimal amount) {
        int selectedExpenseIndex = findExpenseById(id);

        if (selectedExpenseIndex == -1) {
            return false;
        }

        Expense selectedExpense = expenses.get(selectedExpenseIndex);
        selectedExpense = selectedExpense.withUpdatedDetails(description, category, amount);
        expenses.set(selectedExpenseIndex, selectedExpense);
        return true;
    }

    public boolean removeExpense(UUID id) {
        int selectedExpenseIndex = findExpenseById(id);

        if (selectedExpenseIndex == -1) {
            return false;
        }

        this.expenses.remove(selectedExpenseIndex);
        return true;

    }

    //    ------------- Helpers ---------------

    public int findExpenseById(UUID id) {
        int selectedExpenseIndex = -1;
        for (int i = 0; i < getExpenseCount(); i++) {
            if (expenses.get(i).getId().equals(id)) {
                selectedExpenseIndex = i;
                break;
            }
        }

        return selectedExpenseIndex;
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Expense expense : expenses) {
            total = total.add(expense.getAmount());
        }

        return total;
    }

    public BigDecimal calculateAverage() {
        if (expenses.isEmpty()) return BigDecimal.ZERO;
        return calculateTotal().divide(BigDecimal.valueOf(getExpenseCount()), 2, RoundingMode.HALF_UP);
    }

    public Expense findMostExpensiveExpense() {
        if (expenses.isEmpty()) return null;
        Expense expensiveExpense = expenses.getFirst();

        for (Expense expense : expenses) {
            if (expense.getAmount().compareTo(expensiveExpense.getAmount()) > 0) {
                expensiveExpense = expense;
            }
        }

        return expensiveExpense;
    }

    public Expense findCheapestExpense() {
        if (expenses.isEmpty()) return null;
        Expense cheapestExpense = expenses.getFirst();

        for (Expense expense : expenses) {
            if (expense.getAmount().compareTo(cheapestExpense.getAmount()) < 0) {
                cheapestExpense = expense;
            }
        }

        return cheapestExpense;
    }
}
