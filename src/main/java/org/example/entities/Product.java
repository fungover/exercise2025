package org.example.entities;
import java.time.LocalDateTime;
import java.util.Objects;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {

    // Constructor
    public Product {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(name, "Name cannot be null");
        Objects.requireNonNull(category, "Category cannot be null");
        Objects.requireNonNull(createdDate, "Created date cannot be null");

        if (id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        if (modifiedDate == null) {
            modifiedDate = createdDate;
        }
    }
}