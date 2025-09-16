package org.example.entities;

import java.time.LocalDate;

public class Product implements Sellable{

    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;
    private final double price;

    private Product(Builder builder) {

        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.rating = builder.rating;
        this.createdDate = builder.createdDate != null ? builder.createdDate : LocalDate.now();
        this.modifiedDate = builder.modifiedDate != null ? builder.modifiedDate: this.createdDate;
        this.price = builder.price;

        if (id == null || id.isBlank()) throw new IllegalArgumentException("Id cannot be null or empty");
        if (name == null ||name.isBlank()) throw new IllegalArgumentException("Name cannot be null or empty");
        if (category == null) throw new IllegalArgumentException("Category cannot be null");
        if (rating < 0 || rating > 10) throw new IllegalArgumentException("Rating should be between 0 and 10");
        if (modifiedDate.isBefore(createdDate)) throw new IllegalArgumentException("Modified date cannot be before created date");
    }

    // Getters
    @Override
    public String getId() {return id;}
    @Override
    public String getName() {return name;}
    @Override
    public double getPrice() {return price;}
    public Category category() {return category;}
    public int rating() {return rating;}
    public LocalDate createdDate() {return createdDate;}
    public LocalDate modifiedDate() {return modifiedDate;}

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", price=" + price +
                '}';
    }

    // Builder
    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;
        private double price;

        public Builder id(String id) { this.id = id; return this;}
        public Builder name(String name) { this.name = name; return this;}
        public Builder category(Category category) { this.category = category; return this;}
        public Builder rating(int rating) { this.rating = rating; return this;}
        public Builder createdDate(LocalDate createdDate) { this.createdDate = createdDate; return this;}
        public Builder modifiedDate(LocalDate modifiedDate) { this.modifiedDate = modifiedDate; return this;}
        public Builder price(double price) { this.price = price; return this;}

        public Product build() {
            return new Product(this);
        }
    }
}

