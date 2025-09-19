package org.example.entities;


import java.time.ZonedDateTime;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        ZonedDateTime createdDate,
        ZonedDateTime modifiedDate
) {
    public Product {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty or null.");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10.");
        }
        if (createdDate == null) {
            throw new IllegalArgumentException("Created date cannot be null.");
        }
        if (modifiedDate == null) {
            throw new IllegalArgumentException("Modified date cannot be null.");
        }
        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("Modified date cannot be before created date.");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null.");
        }

    }
}
