package io.github.erfanpaya.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Expense {
    private final String description;
    private final String category;
    private final BigDecimal amount;
    private final UUID id;

    public Expense(String description, String category, BigDecimal amount) {
        if (!validateDescription(description)) {
            throw new IllegalArgumentException("Invalid description");
        }
        if (!validateCategory(category)) {
            throw new IllegalArgumentException("Invalid category");
        }
        if (!validateAmount(amount)) {
            throw new IllegalArgumentException("Invalid amount");
        }

        this.id = UUID.randomUUID();
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    private Expense(String description, String category, BigDecimal amount, UUID id) {
        this.description = description;
        this.category = category;
        this.amount = amount;
        this.id = id;
    }

    public Expense withUpdatedDetails(String description, String category, BigDecimal amount) {
        if (!validateDescription(description)) {
            throw new IllegalArgumentException("Invalid description");
        }
        if (!validateCategory(category)) {
            throw new IllegalArgumentException("Invalid category");
        }
        if (!validateAmount(amount)) {
            throw new IllegalArgumentException("Invalid amount");
        }

        return new Expense(description, category, amount, this.id);
    }

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    private boolean validateDescription(String description) {
        return description != null && !description.isBlank();
    }

    private boolean validateCategory(String category) {
        return category != null && !category.isBlank();
    }

    private boolean validateAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }


    @Override
    public String toString() {
        return String.format("%s -> %s -> %s",
                this.getDescription(), this.getCategory(), this.getAmount());
    }
}
