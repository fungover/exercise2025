package org.example.entities;

import java.time.LocalDate;

/**
 * Immutable record representing a product in the warehouse.
 * Validates id, name, category, rating, and dates.
 */
public record Product (
        String id,
        String name,
        Category category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
    public Product {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be null/blank");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name cannot be null/blank");
        if (category == null) throw new IllegalArgumentException("category cannot be null");
        if (rating < 0 || rating > 10) throw new IllegalArgumentException("rating must be between 0 and 10");
        if (createdDate == null || modifiedDate == null) throw new IllegalArgumentException("dates cannot be null");
        if (modifiedDate.isBefore(createdDate)) throw new IllegalArgumentException("modifiedDate cannot be before createdDate");
        name = name.trim();
    }
}