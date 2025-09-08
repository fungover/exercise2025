package org.example.entities;


import java.time.ZonedDateTime;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        ZonedDateTime createdDate,
        ZonedDateTime modifiedDate
) {}
