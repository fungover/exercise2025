package org.example.entities;
import java.time.LocalDate;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    //Private constructor for builder
    private Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate ) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.rating = rating;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
    }
    // Getters
    public String id() {return id;}
    public String name() {return name;}
    public Category category() {return category;}
    public int rating() {return rating;}
    public LocalDate createdDate() {return createdDate;}
    public LocalDate modifiedDate() {return modifiedDate;}

    // Builder
    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;

        //Setter methods that return this.
        public Builder id(String id) {this.id = id; return this;}
        public Builder name(String name) {this.name = name; return this;}
        public Builder category(Category category) {this.category = category; return this;}
        public Builder rating(int rating) {this.rating = rating; return this;}
        public Builder createdDate(LocalDate createdDate) {this.createdDate = createdDate; return this;}
        public Builder modifiedDate(LocalDate modifiedDate) {this.modifiedDate = modifiedDate; return this;}


        public Product build() {
            if (id == null || id.isBlank()) {
                throw new IllegalArgumentException("Product id cannot be empty");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            if (createdDate == null) {
                createdDate = LocalDate.now();
            }
            if (modifiedDate == null) {
                modifiedDate = LocalDate.now();
            }
            return new Product(id, name, category, rating, createdDate, modifiedDate);
            }

    }




}


