package org.example.entities;

import java.time.LocalDateTime;

public final class Product implements Sellable {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final double price;

    private Product(String id,
                    String name,
                    Category category,
                    int rating,
                    LocalDateTime createdDate,
                    LocalDateTime modifiedDate,
                    double price) {

        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.price = price;
    }

    public String id() { return id; }
    public String name() { return name; }
    public Category category() { return category; }
    public int rating() { return rating; }
    public LocalDateTime createdDate() { return createdDate; }
    public LocalDateTime modifiedDate() { return modifiedDate; }
    public double price() { return price; }

    @Override public String getId() { return id; }
    @Override public String getName() { return name; }
    @Override public double getPrice() { return price; }

    public Product withUpdated(String name, Category category, int rating, LocalDateTime now, double price) {
        return toBuilder()
                .name(name)
                .category(category)
                .rating(rating)
                .modifiedDate(now)
                .price(price)
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

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public static Builder from(Product product){
            return new Builder()
                    .id(product.id)
                    .name(product.name)
                    .category(product.category)
                    .rating(product.rating)
                    .createdDate(product.createdDate)
                    .modifiedDate(product.modifiedDate)
                    .price(product.price);
        }

        public Product build() {
            if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
            if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("name required");
            if (category == null) throw new IllegalArgumentException("category required");
            if (rating < 0 || rating > 10) throw new IllegalArgumentException("rating 0–10");
            if (price < 0) throw new IllegalArgumentException("price must be >= 0");

            LocalDateTime created = (createdDate != null) ? createdDate : LocalDateTime.now();
            LocalDateTime modified = (modifiedDate != null) ? modifiedDate : created;

            return new Product(id, name, category, rating, created, modified, price);
        }
    }
}
