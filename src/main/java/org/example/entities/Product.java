package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {

    public Product {
        Objects.requireNonNull(id, "ID must not be null");
        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID must not be empty");
        }
        Objects.requireNonNull(name, "Name must not be null");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        Objects.requireNonNull(category, "Category must not be null");
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        Objects.requireNonNull(createdDate, "Created date must not be null");
        Objects.requireNonNull(modifiedDate, "Modified date must not be null");
    }
}