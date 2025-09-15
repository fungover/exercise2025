package org.fungover.entities;

import java.util.UUID;
import java.time.Instant;

public record Product(
        String identifier,
        String name,
        int rating,
        Category category,
        Instant createdDate,
        Instant lastModifiedDate) {

    public Product(String name, Category category, int rating) {
        this(UUID.randomUUID().toString(), validateName(name), validateRating(rating), category, Instant.now(), null);
    }

    private static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Name cannot be empty";
        }
        return name;
    }

    private static int validateRating(int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be between 0 and 10");
        }
        return rating;
    }

    public Product updateFields(String id, String name, Category category, int rating) {
        return new Product(id, name, rating, category, createdDate, Instant.now());
    }

}
