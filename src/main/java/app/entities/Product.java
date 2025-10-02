package app.entities;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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
                builder.createdDate !=null
                        ?builder.createdDate.truncatedTo(ChronoUnit.MINUTES)
                        :ZonedDateTime.now(ZoneId.of("Europe/Stockholm")).truncatedTo(ChronoUnit.MINUTES),

                builder.modifiedDate !=null
                        ?builder.modifiedDate
                        :null
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
            if (createdDate != null) {
                this.createdDate = createdDate.truncatedTo(ChronoUnit.MINUTES);
            } else {
                this.createdDate = null;
            }
            return this;
        }

        public Builder modifiedDate(ZonedDateTime modifiedDate) {
            if (modifiedDate != null) {
                this.modifiedDate = modifiedDate.truncatedTo(ChronoUnit.MINUTES);

            } else {
                this.modifiedDate = null;
            }
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
