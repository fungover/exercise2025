package org.example.tdd;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public record Product(
        String ID,
        String name,
        Category category,
        int rating,
        ZonedDateTime createdDate,
        ZonedDateTime modifiedDate
) {
    public Product {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        if (createdDate == null) {
            createdDate = ZonedDateTime.now(ZoneId.of("Europe/Stockholm"));
        }
        if (modifiedDate == null) {
            modifiedDate = createdDate;
        }
    }

    public Product(
            String ID,
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
