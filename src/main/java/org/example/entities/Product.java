package org.example.entities;

public class Product {
    private final int id;
    private final String name;
    private final Category category;
    private final double rating;

    // Constructors
    public Product(int id) {
        this(id, "");
    }

    public Product(int id, String name) {
        this(id, name, Category.GENERAL);
    }

    public Product(int id, String name, Category category) {
        this(id, name, category, 0.0);
    }

    public Product(int id, String name, Category category, double rating) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
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

    public double getRating() {
        return this.rating;
    }
}