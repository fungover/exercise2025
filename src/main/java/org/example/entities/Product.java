package org.example.entities;

import java.time.LocalDateTime;

public class Product {
    private final String id;
    private String name;
    private Category category;
    private int rating;
    private final LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.rating = builder.rating;
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = null;
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
    public LocalDateTime createdDate() {
        return createdDate;
    }
    public LocalDateTime modifiedDate() {
        return modifiedDate;
    }
    public void modifyDateToNow() {
        this.modifiedDate = LocalDateTime.now();
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;

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

        public Product build() {
            if (this.id.isEmpty()) {
                throw new IllegalArgumentException("ID cannot be empty");
            }
            if (this.name.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (this.category == null) {
                throw new IllegalArgumentException("Category cannot be empty");
            }
            if (this.rating < 0 || this.rating > 10) {
                throw new IllegalArgumentException("Rating should be between 0 and 10");
            }
            return new Product(this);
        }
    }
}
