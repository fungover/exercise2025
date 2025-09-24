package org.example.entities;
import java.time.LocalDateTime;
import java.util.Objects;

public final class Product {

    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    /*
    Private product constructor that relies on the nested builder in order to work with individual fields being
    validated to ensure they are not null nor consist of empty strings such as for example only whitespace or
    ratings that are not within the range of 0-10.
     */

    private Product(Builder builder){
        this.id = Objects.requireNonNull(builder.id, "ID required, cannot be null");
        this.name = Objects.requireNonNull(builder.name, "Name required, cannot be null");
        this.category = Objects.requireNonNull(builder.category, "Category required, cannot be null");
        this.createdDate = Objects.requireNonNullElse(builder.createdDate, LocalDateTime.now());
        this.modifiedDate = builder.modifiedDate != null ? builder.modifiedDate : this.createdDate;


        if (builder.id.trim().isEmpty()){
            throw new IllegalArgumentException("ID cannot be empty");
        }

        if (builder.name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (builder.rating < 0 || builder.rating > 10){
            throw new IllegalArgumentException("Rating cannot be empty and should be between 0 and 10");
        }
        this.rating = builder.rating;
    }

    //Getters to get read only access to individual private fields.

    public String id() { return id; }
    public String name() { return name; }
    public Category category() { return category; }
    public int rating() { return rating; }
    public LocalDateTime createdDate() { return createdDate; }
    public LocalDateTime modifiedDate() { return modifiedDate; }

    //The nested builder class that goes in as an arg to Product

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        //Setters
        public Builder id(String id) {
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

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}