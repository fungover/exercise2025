package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {

    private Product(Builder builder) {
        this(builder.id, builder.name, builder.category, builder.rating, builder.createdDate, builder.modifiedDate);
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
            StringBuilder error = new StringBuilder();
            if (id == null || id.trim().isEmpty()) error.append("ID must not be null or empty");
            if (name == null || name.trim().isEmpty()) error.append("Name must not be null or empty");
            if (category == null) error.append("Category must not be null");
            if (rating < 0 || rating > 10) error.append("Rating must be between 0 and 10");
            if (createdDate == null) createdDate = LocalDate.now();
            if (modifiedDate == null) modifiedDate = LocalDate.now();
            if (!error.isEmpty()) throw new IllegalArgumentException(error.toString());
            return new Product(this);
        }
    }
}