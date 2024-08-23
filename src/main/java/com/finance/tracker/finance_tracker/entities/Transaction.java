package com.finance.tracker.finance_tracker.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a financial transaction, which could be an income or an expense.
 * This entity is stored in the "transaction" table in the database.
 */
@Data
@Entity
@Table(name = "transaction")
public class Transaction {

    /**
     * The unique identifier for this transaction. It is auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The type of the transaction (INCOME or EXPENSE).
     * It is stored as a string in the database.
     */
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    /**
     * The amount of money involved in the transaction.
     * Precision is set to 10 digits with 2 decimal places.
     */
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    /**
     * The date when the transaction took place.
     */
    private LocalDate date;

    /**
     * A brief description or note about the transaction.
     */
    private String description;

    /**
     * The category associated with this transaction (e.g., Groceries, Rent).
     * This is a foreign key relationship with the Category entity.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    // Getters and setters for the fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
