package org.example.entities;

import java.time.LocalDate;

public class Product {
    private final int id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private LocalDate modifiedDate;

    // Constructors
    public Product(int id) {
        this(id, "");
    }

    public Product(int id, String name) {
        this(id, name, Category.GENERAL);
    }

    public Product(int id, String name, Category category) {
        this(id, name, category, 0);
    }

    public Product(int id, String name, Category category, int rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = LocalDate.now();
    }

    // Getters
    public int getId() {
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

    // Setters
    private void setModifiedDate() {
        this.modifiedDate = LocalDate.now();
    }

    public void setName(String notebook) {
        setModifiedDate();
    }
}