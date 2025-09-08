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
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
    }
}
