package org.example.entities;

import java.time.LocalDate;

public record Product(
            String id,
            String name,
            Category category,
            int rating,
            LocalDate createdDate,
            LocalDate modifiedDate
    ) {
    // Compact constructor for validation
    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        if (createdDate() == null) {
            createdDate = LocalDate.now();
        }
        if (modifiedDate() == null) {
            modifiedDate = createdDate;
        }
    }
}







/*    public String getId() { return id; }
    public String getName() { return name; }
    public int getRating() { return rating; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getModifiedDate() { return modifiedDate; }
    public Category getCategory() { return category; }*/



/*public final class Product {

    private final String id;
    private final String name;
    private final int rating;
    private final LocalDate createdDate = LocalDate.now();
    private LocalDate modifiedDate;
    private final Category category;

    public Product(String id, String name, int rating, LocalDate modifiedDate, Category category) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.modifiedDate = modifiedDate;
        this.category = category;

    }*/
