package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public record Product(
        UUID id,
        String name,
        CategoryEnum category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
    public Product {
        Objects.requireNonNull(name, "Name can't be null");
    }

    public static class Builder {
        private UUID id;
        private String name;
        private CategoryEnum category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder category(CategoryEnum category) {
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
    }
}
