package org.example.entities;

import java.time.LocalDate;
import java.util.UUID;

public record Product(Long id, String name, Category category, int rating,
                      LocalDate createdDate, LocalDate modifiedDate) {

    private static long idCounter = 1;

    //constructor
    public Product(String name, Category category, int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating should be between 0 and 10");

        }
        this(idCounter++, name, category, rating, LocalDate.now(), LocalDate.now());
    }

    //test constructor to give other dates.
    public Product(String name, Category category, int rating,
                   LocalDate createdDate) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating should be between 0 and 10");
        }
        this(idCounter++, name, category, rating, createdDate, createdDate);
    }

    //builder
    public static class Builder {
        private Long id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;

        public Builder id(Long id) {
            this.id = id;
            return this; // this allows chaining like .id("123").name("lamp")
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
            //add validations
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("name cannot be empty");
            }
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException(
                  "rating should be between 0 and 10");
            }
            // if id not set, we generate one
            long newId = (id != null) ? id : idCounter++;
            //same with dates
            LocalDate now = LocalDate.now();
            LocalDate created = (createdDate != null) ? createdDate : now;
            LocalDate modified = (modifiedDate != null) ? modifiedDate : now;

            return new Product(newId, name, category, rating, created, modified);
        }


    }

    @Override public String toString() {
        return String.format(
          //%-15s means that its left-align with a width of 15
          "id=%-2d Name=%-15s Category=%-12s Rating=%-3d Created=%-12s " +
            "Modified=%s", id, name, category, rating, createdDate, modifiedDate);
    }


}
