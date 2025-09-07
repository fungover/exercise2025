package org.example.entities;

public class Product {
    private final int id;
    private final String name;
    private final Category category;

    // Constructors
    public Product(int id) {
        this(id, "");
    }

    public Product(int id, String name) {
        this(id, name, Category.GENERAL);
    }

    public Product(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
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
}