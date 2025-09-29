package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        double price,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) implements Sellable {

    public Product {
        Objects.requireNonNull(id, "Product ID cannot be null");
        Objects.requireNonNull(name, "Product name cannot be null");
        Objects.requireNonNull(category, "Product category cannot be null");
        Objects.requireNonNull(createdDate, "Created date cannot be null");
        Objects.requireNonNull(modifiedDate, "Modified date cannot be null");

        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10, was: " + rating);
        }

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative, was: " + price);
        }

        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("Modified date cannot be before created date");
        }

        name = name.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return String.format("%s (%s) - Rating: %d/10 - Price: %.2f kr",
                name, category, rating, price);
    }

    public boolean isModified() {
        return !createdDate.equals(modifiedDate);
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private double price;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public Builder() {
            LocalDateTime now = LocalDateTime.now();
            this.createdDate = now;
            this.modifiedDate = now;
            this.price = 0.0;
        }

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

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Builder withCurrentTimestamps() {
            LocalDateTime now = LocalDateTime.now();
            this.createdDate = now;
            this.modifiedDate = now;
            return this;
        }

        public Builder asNewProduct() {
            return withCurrentTimestamps();
        }

        public Builder asModifiedProduct() {
            this.modifiedDate = LocalDateTime.now();
            return this;
        }

        public Product build() {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID must be set before building");
            }
            if (name == null) {
                throw new IllegalArgumentException("Product name must be set before building");
            }
            if (category == null) {
                throw new IllegalArgumentException("Product category must be set before building");
            }

            return new Product(id, name, category, rating, price, createdDate, modifiedDate);
        }
    }
}