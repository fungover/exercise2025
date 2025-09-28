package org.example.entities;

import java.time.LocalDate;

public record Product(
        int id,
        String name,
        CategoryEnum category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
    public Product {}
}
