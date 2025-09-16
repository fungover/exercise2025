package org.example.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private Product(String id,
                    String name,
                    Category category,
                    int rating,
                    LocalDateTime createdDate,
                    LocalDateTime modifiedDate) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public String id() { return id; }
    public String name() { return name; }
    public Category category() { return category; }
    public int rating() { return rating; }
    public LocalDateTime createdDate() { return createdDate; }
    public LocalDateTime modifiedDate() { return modifiedDate; }

    public static Product createNew(String id, String name, Category category, int rating, LocalDateTime now) {
        return builder()
                .id(id).name(name).category(category).rating(rating)
                .createdDate(now).modifiedDate(now)
                .build();
    }

    public Product withUpdated(String name, Category category, int rating, LocalDateTime now) {
        return toBuilder()
                .name(name).category(category).rating(rating)
                .modifiedDate(now)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return Builder.from(this);
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

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

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public static Builder from(Product product){
            return new Builder()
                    .id(product.id)
                    .name(product.name)
                    .category(product.category)
                    .rating(product.rating)
                    .createdDate(product.createdDate)
                    .modifiedDate(product.modifiedDate);
        }

        public Product build() {
            if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
            if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name required");
            if (category == null) throw new IllegalArgumentException("category required");
            if (rating < 0 || rating > 10) throw new IllegalArgumentException("rating 0–10");
            Objects.requireNonNull(createdDate, "createdDate");
            Objects.requireNonNull(modifiedDate, "modifiedDate");

            return new Product(this.id, this.name, this.category, this.rating, this.createdDate, this.modifiedDate);
        }
    }
}
