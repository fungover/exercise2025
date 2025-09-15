package org.example.entities;

import java.time.ZonedDateTime;
import java.util.Objects;

public final class Product {
    // Final fields for immutability
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final ZonedDateTime createdDate;
    private final ZonedDateTime modifiedDate;

    // Private constructor, it can only be called by Builder
    private Product(
            String id,
            String name,
            Category category,
            int rating,
            ZonedDateTime createdDate,
            ZonedDateTime modifiedDate)
    {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    // Getters with immutable access
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

    public ZonedDateTime createdDate() {
        return createdDate;
    }

    public ZonedDateTime modifiedDate() {
        return modifiedDate;
    }

    // Compares two Product objects for equality based on all fields
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return rating == product.rating &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(category, product.category) &&
                Objects.equals(createdDate, product.createdDate) &&
                Objects.equals(modifiedDate, product.modifiedDate);
    }

    // Generates unique hash code based on all fields
    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, rating, createdDate, modifiedDate);
    }

    // Creates readable string representation for debugging and logging
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

    public static class Builder {
        // Fields to hold values temporarily before creating a Product
        private String id;
        private String name;
        private Category category;
        private int rating;
        private ZonedDateTime createdDate;
        private ZonedDateTime modifiedDate;

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

        public Builder createdDate(ZonedDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(ZonedDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Product build() {
            ZonedDateTime now = ZonedDateTime.now();

            // Set default values for times if not provided
            if (createdDate == null) {
                createdDate = now;
            }
            if (modifiedDate == null) {
                modifiedDate = now;
            }

            // Validation
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("id required");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name required");
            }
            if (category == null) {
                throw new IllegalArgumentException("category required");
            }
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("rating must be between 0 and 10");
            }
            if (modifiedDate.isBefore(createdDate)) {
                throw new IllegalArgumentException("modifiedDate cannot be before createdDate");
            }

            // Create and returns new Product instance
            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }
    }

    // Entry point for the Builder pattern
    public static Builder builder() {
        return new Builder();
    }
}