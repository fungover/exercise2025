package org.example.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public record Product (
    String id,
    String name,
    Category category,
    int rating,
    LocalDateTime createdDate,  // immutable type
    LocalDateTime modifiedDate
) {

    public Product {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name required");
        if (category == null) throw new IllegalArgumentException("category required");
        if (rating < 0 || rating > 10) throw new IllegalArgumentException("rating 0–10");
        Objects.requireNonNull(createdDate, "createdDate");
        Objects.requireNonNull(modifiedDate, "modifiedDate");
    }

    public static Product createNew(String id, String name, Category category, int rating, LocalDateTime now) {
        return new Product(id, name, category, rating, now, now);
    }

    public Product withUpdated(String name, Category category, int rating, LocalDateTime now) {
        Objects.requireNonNull(now, "now");
        return new Product(this.id, name, category, rating, this.createdDate, now);
    }

}
