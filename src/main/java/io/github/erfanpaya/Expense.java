package io.github.erfanpaya;

public class Expense {
    private final String description;
    private final String category;
    private final double amount;

    public Expense(String description, String category, double amount) {
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s -> %.2f",
                this.getDescription(), this.getCategory(), this.getAmount());
    }
}
