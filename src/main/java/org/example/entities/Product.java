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
    // Compact constructor for validation
    public Product {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (createdDate == null) {
            createdDate = LocalDate.now();
        }
        if (modifiedDate == null) {
            modifiedDate = createdDate;
        }
    }
}

