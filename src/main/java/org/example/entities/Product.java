package org.example.entities;

import java.time.LocalDate;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
    public Product {

        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be between 0 and 10");
        }
        if (createdDate == null) {
            throw new IllegalArgumentException("createdDate cannot be null");
        }
        if (modifiedDate == null) {
            throw new IllegalArgumentException("modifiedDate cannot be null");
        }
        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("modifiedDate cannot be before createdDate");
        }
    }
}