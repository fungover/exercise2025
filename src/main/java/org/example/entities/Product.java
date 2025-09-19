package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Product implements Sellable {
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
        this.createdDate = builder.createdDate;
        this.modifiedDate = builder.modifiedDate;
        this.price = builder.price;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public int rating() {
        return rating;
    }

    public LocalDate createdDate() {
        return createdDate;
    }

    public LocalDate modifiedDate() {
        return modifiedDate;
    }

    public double price() {
        return price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String name;
        private Category category;
        private Integer rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate;
        private Double price;

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

        public Builder rating(Integer rating) {
            this.rating = rating;
            return this;
        }

        public Builder createdDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDate modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Builder price(Double price) {
            this.price = price;
            return this;
        }


        public Product build() {
            // Default värden om man inte sätter createdDate, modifiedDate eller price
            if (createdDate == null) {
                createdDate = LocalDate.now();
            }
            if (modifiedDate == null) {
                modifiedDate = createdDate;
            }
            if (price == null) {
                price = 0.0;
            }

            // Validering som ser till att alla krav följs
            if (id == null) {
                throw new IllegalArgumentException("id cannot be null");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("name cannot be blank");
            }
            if (category == null) {
                throw new IllegalArgumentException("category cannot be null");
            }
            if (rating == null) {
                throw new IllegalArgumentException("rating must be set");
            }
            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("rating must be between 0 and 10");
            }
            if (createdDate == null) {
                throw new IllegalArgumentException("createdDate cannot be null");
            }
            if (modifiedDate == null) {
                throw new IllegalArgumentException("modifiedDate cannot be null");
            }
            if (modifiedDate.isBefore(createdDate)) {
                throw new IllegalArgumentException("modifiedDate cannot be before createdDate");
            }
            if (price < 0) {
                throw new IllegalArgumentException("price cannot be negative");
            }

            // Skapa färdig produkt
            return new Product(this);
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.category, that.category) &&
                this.rating == that.rating &&
                Objects.equals(this.createdDate, that.createdDate) &&
                Objects.equals(this.modifiedDate, that.modifiedDate) &&
                Double.compare(this.price, that.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, rating, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "Product[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "category=" + category + ", " +
                "rating=" + rating + ", " +
                "createdDate=" + createdDate + ", " +
                "modifiedDate=" + modifiedDate + ']' +
                "price=" + price + ']';
    }

    // Implementering av Sellable interface
    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getId() {
        return id;
    }

}