package org.example.entities;

import java.time.LocalDate;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;


    public Product(String id, String name, Category category, int rating, LocalDate createdDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = createdDate;
    }

    public Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }


    public int rating() {
        return rating;
    }

    public LocalDate createdDate() {
        return createdDate;
    }

    public LocalDate modifiedDate() {
        return modifiedDate;
    }
}
