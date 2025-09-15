package org.example.entities;

import java.time.LocalDate;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {

    private Product(Builder builder) {
        this(
                builder.id,
                builder.name,
                builder.category,
                builder.rating,
                builder.createdDate,
                builder.modifiedDate
        );
    }

    public static class Builder {
        private String name;
        private String id;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;

        public Builder() {

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

        public Builder createdDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDate modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Product build() {

            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID is required");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }

            if (category == null) {
                throw new IllegalArgumentException("Category is required");
            }

            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("Rating must be between 0 and 10");
            }

            if (createdDate == null) {
                this.createdDate = LocalDate.now();
            }
            if (modifiedDate == null) {
                this.modifiedDate = LocalDate.now();
            }
            return new Product(this);
        }

    }
}
