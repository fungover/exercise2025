package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {

    public Product {

        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(category, "Category cannot be null");
        Objects.requireNonNull(createdDate, "Created date cannot be null");
        Objects.requireNonNull(modifiedDate, "Modified date cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
    }
}
