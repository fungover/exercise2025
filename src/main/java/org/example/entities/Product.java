package org.example.entities;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private Category category;
    private int rating;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public Product(int id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
