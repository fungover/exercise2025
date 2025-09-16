package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable product entity used in the warehouse.
 * <p>
 * Use {@link Product.Builder} to create instances. The builder performs validation
 * and supplies reasonable defaults (createdDate/defaults to now, modifiedDate defaults to createdDate).
 */

public class Product implements Sellable {
        private final String id;
        private final String name;
        private final Category category;
        private final int rating;
        private final LocalDate createdDate;
        private final LocalDate modifiedDate;
        private final double price;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.rating = builder.rating;
        this.createdDate = builder.createdDate != null ? builder.createdDate : LocalDate.now();
        this.modifiedDate = builder.modifiedDate != null ? builder.modifiedDate : this.createdDate;
        this.price = builder.price;
    }

    /**
     * Public immutable getters
    */
    @Override
    public String getId() { return id;}

    @Override
    public String getName() { return name;}

    public Category getCategory() { return category;}

    public int getRating() { return rating;}

    public LocalDate getCreatedDate() { return createdDate;}

    public LocalDate getModifiedDate() { return modifiedDate;}

    @Override
    public double getPrice() { return price;}

    /**
     * equals/hashCode/toString (based on id)
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product that = (Product) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", category=" + category + '}';
    }

    /**
     *  Builder
    */
    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;
        private double price;

        public Builder id(String id) {this.id = id; return this;}
        public Builder name(String name) {this.name = name; return this;}
        public Builder category(Category category) {this.category = category; return this;}
        public Builder rating(int rating) {this.rating = rating; return this;}
        public Builder createdDate(LocalDate createdDate) {this.createdDate = createdDate; return this;}
        public Builder modifiedDate(LocalDate modifiedDate) {this.modifiedDate = modifiedDate; return this;}
        public Builder price(double price) {this.price = price; return this; }

        public Product build() {
            if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be null/blank");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("name cannot be null/blank");
            String trimmedName = name.trim();
            if (trimmedName.isEmpty()) throw new IllegalArgumentException("name cannot be blank");
            if (category == null) throw new IllegalArgumentException("category cannot be null");
            if (rating < 0 || rating > 10) throw new IllegalArgumentException("rating must be between 0 and 10");
            if (price < 0.0) throw new IllegalArgumentException("price cannot be negative");

            LocalDate created = createdDate != null ? createdDate : LocalDate.now();
            LocalDate modified = modifiedDate != null ? modifiedDate : created;
            if (modified.isBefore(created)) throw new IllegalArgumentException("modifiedDate cannot be before createdDate");

            this.name = trimmedName;
            this.createdDate = created;
            this.modifiedDate = modified;

            return new Product(this);
        }
    }
}