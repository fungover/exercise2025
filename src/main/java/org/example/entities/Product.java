package org.example.entities;

import java.time.LocalDate;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

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

        public Product build() {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Product id cannot be empty");
            }

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }

            if (category == null) {
                throw new IllegalArgumentException("Product category cannot be null");
            }

            if (createdDate == null) {
                createdDate = LocalDate.now();
            }

            if (modifiedDate == null) {
                modifiedDate = createdDate;
            }

            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        validateRating(rating);
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    private void validateRating(int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10, it was: " + rating);
        }
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
        return modifiedDate;
    }
}
