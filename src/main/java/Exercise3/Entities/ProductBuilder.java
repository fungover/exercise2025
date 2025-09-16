package Exercise3.Entities;

import java.time.LocalDate;
import java.util.Objects;

public class ProductBuilder {
    private String id;
    private String name;
    private Category category;
    private int rating;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

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