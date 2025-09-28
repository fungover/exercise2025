package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(
        int id,
        String name,
        CategoryEnum category,
        int rating,
        LocalDate createdDate,
        LocalDate modifiedDate
) {
    public Product {
        Objects.requireNonNull(name, "Name can't be null");
    }
}
