package org.example.entities;
import java.time.LocalDate;

public record Product (
    String id,
    String name,
    Category category,
    int rating,
    LocalDate createdDate,
    LocalDate modifiedDate
    )
{}
