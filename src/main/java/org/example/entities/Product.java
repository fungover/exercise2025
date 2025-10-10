package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public final class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    private Product(Builder builder) {
        if (builder.name == null || builder.name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (builder.rating < 0 || builder.rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        this.id = Objects.requireNonNull(builder.id, "ID cannot be null");
        this.name = builder.name;
        this.category = Objects.requireNonNull(builder.category, "Category cannot be null");
        this.rating = builder.rating;

        this.createdDate = builder.createdDate != null ? builder.createdDate : LocalDate.now();
        this.modifiedDate = builder.modifiedDate != null ? builder.modifiedDate : this.createdDate;
    }

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
            return new Product(this);
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
    public int getRating() { return rating; }
    public LocalDate getCreatedDate() { return createdDate; }
    public LocalDate getModifiedDate() { return modifiedDate; }

    public Product withUpdatedValues(String name, Category category, int rating, LocalDate modifiedDate) {
        return new Builder()
                .id(this.id)
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(this.createdDate)
                .modifiedDate(modifiedDate)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
