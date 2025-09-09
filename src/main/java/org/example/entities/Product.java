package org.example.entities;

import java.time.LocalDate;

public final class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    public Product(String id, String name, Category category, int rating,
                   LocalDate createdDate, LocalDate modifiedDate) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name cannot be blank");
        }
        this.name = name;

        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        this.category = category;

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be between 0 and 10");
        }
        this.rating = rating;

        if (createdDate == null) {
            throw new IllegalArgumentException("createdDate cannot be null");
        }
        this.createdDate = createdDate;

        if (modifiedDate == null) {
            throw new IllegalArgumentException("modifiedDate cannot be null");
        } else if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("modifiedDate cannot be before createdDate");
        }
        this.modifiedDate = modifiedDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }
}
