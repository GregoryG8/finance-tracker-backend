package com.finance.tracker.finance_tracker.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a category of financial transactions, such as "Groceries", "Rent", or "Salary".
 * This entity is stored in the "category" table in the database.
 */
@Data
@Entity
@Table(name = "category")
public class Category {

    /**
     * The unique identifier for this category. It is auto-incremented by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The name of the category (e.g., "Groceries", "Rent").
     */
    private String name;

    public int getId() {
        return id;
    }

    // Getters and setters for the fields
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
