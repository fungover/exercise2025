package org.fungover.entities;

import java.util.Objects;
import java.util.UUID;
import java.time.Instant;

public record Product(String identifier, String name, int rating, Category category, Instant createdDate,
                      Instant lastModifiedDate) {

    private Product(String name, Category category, int rating) {
        this(UUID.randomUUID().toString(), name, rating, category, Instant.now(), Instant.now());
    }

    public static final class Builder {
        private String identifier;
        private String name;
        private int rating;
        private Category category;
        private Instant createdDate;
        private Instant lastModifiedDate;

        public Product build() {
            Instant now = Instant.now();
            String id = identifier == null ? UUID.randomUUID().toString() : identifier;
            String n = validateName(name);
            int r = validateRating(Objects.requireNonNullElse(rating, 0));
            Category c = Objects.requireNonNull(category, "Category is required");
            Instant created = createdDate == null ? now : createdDate;
            Instant lastModified = lastModifiedDate == null ? now : lastModifiedDate;
            return new Product(id, n, r, c, created, lastModified);
        }

        public Builder identifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder createdDate(Instant createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder lastModifiedDate(Instant lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }
    }

    private static String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return name;
    }

    private static int validateRating(int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be between 0 and 10");
        }
        return rating;
    }

    public Product updateFields(String id, String name, Category category, int rating) {
        return new Product(id, validateName(name), validateRating(rating), category, createdDate, Instant.now());
    }

}
