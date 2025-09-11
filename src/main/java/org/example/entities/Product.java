package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public final class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    public Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.category = Objects.requireNonNull(category);
        if (rating < 0 || rating > 10) throw new IllegalArgumentException("Rating must be between 0 and 10");
        this.rating = rating;
        this.createdDate = Objects.requireNonNull(createdDate, "createdDate");
        this.modifiedDate = Objects.requireNonNull(modifiedDate, "modifiedDate");
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getRating() { return rating; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getModifiedDate() { return modifiedDate; }

    public Product withUpdatedValues(String name, Category category, int rating, LocalDate modifiedDate) {
        return new Product(this.id, name, category, rating, this.createdDate, modifiedDate);
    }
}
