package entities;

import java.time.LocalDateTime;
import java.util.Objects;

// Product record that implements Sellable interface

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        double price,  // ← NYTT: pris-attribut
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) implements Sellable {  // ← NYTT: implementerar Sellable

    // Public constructor for validation
    public Product {
        // Input validation
        Objects.requireNonNull(id, "Product ID cannot be null");
        Objects.requireNonNull(name, "Product name cannot be null");
        Objects.requireNonNull(category, "Product category cannot be null");
        Objects.requireNonNull(createdDate, "Created date cannot be null");
        Objects.requireNonNull(modifiedDate, "Modified date cannot be null");

        // Check that the name is not empty (after trimming whitespace)
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        // Check that the rating is within a valid range
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10, was: " + rating);
        }

        // Validate price
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative, was: " + price);
        }

        // Ensure modifiedDate is not before createdDate
        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("Modified date cannot be before created date");
        }

        // Trim the name to remove unnecessary whitespace
        name = name.trim();
    }

    // === SELLABLE INTERFACE METHODS ===

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

    // Checks if the product has been modified since it was created
    public boolean isModified() {
        return !createdDate.equals(modifiedDate);
    }

    // === BUILDER CLASS ===

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private double price;  // ← NYTT: pris i Builder
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        // Constructor
        public Builder() {
            // Set default values
            LocalDateTime now = LocalDateTime.now();
            this.createdDate = now;
            this.modifiedDate = now;
            this.price = 0.0;  // Default pris
        }

        // Setter methods that return Builder instance for method chaining
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

        // Special method to set both created and modified date to current time
        public Builder withCurrentTimestamps() {
            LocalDateTime now = LocalDateTime.now();
            this.createdDate = now;
            this.modifiedDate = now;
            return this;
        }

        // Special method to create a new product sets both timestamps to now
        public Builder asNewProduct() {
            return withCurrentTimestamps();
        }

        // Build method - creates and returns the Product instance
        public Product build() {
            // Additional validation can be added here if needed
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID must be set before building");
            }
            if (name == null) {
                throw new IllegalArgumentException("Product name must be set before building");
            }
            if (category == null) {
                throw new IllegalArgumentException("Product category must be set before building");
            }

            // Create and return the product using the record constructor
            return new Product(id, name, category, rating, price, createdDate, modifiedDate);
        }
    }
}