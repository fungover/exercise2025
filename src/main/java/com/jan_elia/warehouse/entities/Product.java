package com.jan_elia.warehouse.entities;

import java.time.LocalDate;

public class Product {

    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    // Private constructor - only Builder can create Product
    private Product(String id, String name, Category category, int rating,
                    LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getRating() { return rating; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getModifiedDate() { return modifiedDate; }

    // Builder class
    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;

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

        public Builder createdDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDate modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        // build() method with validation + defaults
        public Product build() {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id cannot be null or blank");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name cannot be null or blank");
            }
            if (category == null) {
                throw new IllegalArgumentException("category cannot be null");
            }
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("rating must be between 0 and 10");
            }

            LocalDate now = LocalDate.now();
            if (createdDate == null) {
                createdDate = now;
            }
            if (modifiedDate == null) {
                modifiedDate = createdDate;
            }

            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }
    }
}
