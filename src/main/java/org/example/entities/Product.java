package org.example.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final double price;

    // Constructors
    private Product(String id, String name, Category category, int rating, double price) {
        LocalDateTime now = LocalDateTime.now();
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = now;
        this.modifiedDate = now;
        this.price = price;
    }

    private Product(String id, String name, Category category, int rating, LocalDateTime createdDate, LocalDateTime modifiedDate, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.price = price;
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

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public double getPrice() {
        return this.price;
    }

    // Methods
    public Product update(String name, Category category, int rating, double price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (rating < 1 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be greater than or equal to zero");
        }

        return new Product(this.id, name, category, rating, this.createdDate, LocalDateTime.now(), price);
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private double price;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Product id cannot be null or blank");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Product name cannot be null or blank");
            }
            if (category == null) {
                throw new IllegalArgumentException("Category cannot be null");
            }
            if (rating < 1 || rating > 10) {
                throw new IllegalArgumentException("Rating must be between 1 and 10");
            }
            if (price < 0) {
                throw new IllegalArgumentException("Price must be greater than or equal to zero");
            }

            return new Product(id, name, category, rating, price);
        }
    }
}