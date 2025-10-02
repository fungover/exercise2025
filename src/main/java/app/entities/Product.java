package app.entities;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public record Product(
        int ID,
        String name,
        Category category,
        int rating,
        ZonedDateTime createdDate,
        ZonedDateTime modifiedDate
) {
    private Product(Builder builder) {
        this(
                builder.id,
                builder.name,
                builder.category,
                builder.rating,
                builder.createdDate !=null ? builder.createdDate:ZonedDateTime.now(ZoneId.of("Europe/Stockholm")),
                builder.modifiedDate !=null ? builder.modifiedDate:builder.createdDate
        );

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        if (createdDate == null) {
            System.out.println("Created date is null");
        }
    }

    public static class Builder {
        private int id;
        private String name;
        private Category category;
        private int rating;
        private ZonedDateTime createdDate;
        private ZonedDateTime modifiedDate;

        public Builder id(int id) {
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

        public Builder createdDate(ZonedDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(ZonedDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    public Product(
            int ID,
            String Name,
            Category category,
            int rating
    ) {
       this (
               ID,
               Name,
               category,
               rating,
               ZonedDateTime.now(ZoneId.of("Europe/Stockholm")),
               null

       );
    }

}
