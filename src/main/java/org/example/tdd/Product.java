package org.example.tdd;

public record Product(String ID, String name, Category category, int rating) {
    public Product {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
    }
}
