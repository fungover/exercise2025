package org.example.entities;

import java.time.LocalDate;

public class Product {
    String id;
    String name;
    Category category;
    int rating;
    LocalDate createdDate;
    LocalDate modifiedDate;


    public Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = createdDate;
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
        return createdDate;
    }
}
