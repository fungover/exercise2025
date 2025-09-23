package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {

    // Public constructor for validation (required for records)
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

        // Ensure modifiedDate is not before createdDate
        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("Modified date cannot be before created date");
        }

        // Trim the name to remove unnecessary whitespace
        name = name.trim();
    }

    // Create a new product with current time (kept for backward compatibility)
    public static Product createNew(String id, String name, Category category, int rating) {
        LocalDateTime now = LocalDateTime.now();
        return new Product(id, name, category, rating, now, now);
    }

    // Creates a copy of this product with updated values
    public Product withUpdatedValues(String newName, Category newCategory, int newRating) {
        return new Product(
                this.id,
                newName,
                newCategory,
                newRating,
                this.createdDate,
                LocalDateTime.now()
        );
    }

    // Checks if the product has been modified since it was created
    public boolean isModified() {
        return !createdDate.equals(modifiedDate);
    }

    // Builder class - public static nested class
    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        // Constructor
        public Builder() {
            // Set default values
            LocalDateTime now = LocalDateTime.now();
            this.createdDate = now;
            this.modifiedDate = now;
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

        // Special method to create a new product (sets both timestamps to now)
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
            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }
    }
}