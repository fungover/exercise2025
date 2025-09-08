package org.example.entities;

public class Product {
    String id;
    String name;
    Category category;
    int rating;


    public Product(String id, String name, Category category, int rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
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
}
