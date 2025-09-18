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

    // Create a new product with current time
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
}
