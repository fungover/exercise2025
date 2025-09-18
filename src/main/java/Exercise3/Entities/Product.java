package exercise3.entities;

import java.time.LocalDate;
import java.util.Objects;

public final class Product {

    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    private Product(String id, String name, Category category, int rating, LocalDate createdDate,
                   LocalDate modifiedDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return rating == product.rating && Objects.equals(id, product.id) && Objects.equals(name, product.name) && category == product.category && Objects.equals(createdDate, product.createdDate) && Objects.equals(modifiedDate, product.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, rating, createdDate, modifiedDate);
    }

    public static class ProductBuilder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate;
        private LocalDate modifiedDate = LocalDate.now();

        public ProductBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public ProductBuilder setRating(int rating) {
            this.rating = rating;
            return this;
        }

        public ProductBuilder setCreatedDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public ProductBuilder setModifiedDate(LocalDate modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public ProductBuilder from(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.category = product.getCategory();
            this.rating = product.getRating();
            this.createdDate = product.getCreatedDate();
            this.modifiedDate = product.getModifiedDate();
            return this;
        }

        public Product createProduct() {

            id = Objects.requireNonNull(id, "Id is null").trim();
            name = Objects.requireNonNull(name, "Name is null").trim();
            Objects.requireNonNull(category, "Category is null");
            Objects.requireNonNull(createdDate, "Created date is null");
            Objects.requireNonNull(modifiedDate, "Modified Date is null");

            if(id.isEmpty() || name.isEmpty()){
                throw new IllegalArgumentException("Id or name is empty");
            }

            if(rating < 0 || rating > 10) {
                throw new IllegalArgumentException("Invalid rating");
            }

            if(createdDate.isAfter(modifiedDate)){
                throw new IllegalArgumentException("Modified date is before now");
            }

            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }
    }



}
