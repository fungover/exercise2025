package org.example.entities;

public class Product {
    private int id;
    private String name;

    public Product(int id) {
        this.id = id;
        this.name = "";
    }

    public Product(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}