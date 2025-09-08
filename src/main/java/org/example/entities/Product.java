package org.example.entities;

import java.time.LocalDate;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    // Constructors
    public Product(String id) {
        this(id, "");
    }

    public Product(String id, String name) {
        this(id, name, Category.GENERAL);
    }

    public Product(String id, String name, Category category) {
        this(id, name, category, 0);
    }

    public Product(String id, String name, Category category, int rating) {
        LocalDate now = LocalDate.now();
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = now;
        this.modifiedDate = now;
    }

    private Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    // Getters
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getRating() {
        return this.rating;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public LocalDate getModifiedDate() {
        return this.modifiedDate;
    }

    // Methods
    public Product update(String name, Category category, int rating) {
        return new Product(this.id, name, category, rating, this.createdDate, LocalDate.now());
    }
}