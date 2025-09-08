package org.example.entities;

public class Product {
    String id;
    String name;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }
}
