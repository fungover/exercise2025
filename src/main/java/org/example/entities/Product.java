package org.example.entities;

public class Product {
    String id;
    String name;
    Category category;

    public Product(String id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
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


}
