package org.example.entities;

import java.time.LocalDate;
import java.util.UUID;

public final class Product {

    private final String id;
    private final String name;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;
    private final Category category;

    public Product(String name, int rating, LocalDate createdDate, LocalDate modifiedDate, Category category) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.category = category;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getRating() { return rating; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getModifiedDate() { return modifiedDate; }
    public Category getCategory() { return category; }


    // Factory method för uppdateringar
    public Product withUpdates(String name, Category category, int rating) {
        return new Product(
                name,
                rating,
                this.createdDate,
                LocalDate.now(),
                category
        );
    }
}
