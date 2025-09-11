package exercise3.enteties;

import exercise3.service.Category;

import java.time.LocalDate;
import java.util.UUID;


public final class Product {
    private final String id;
    private String name;
    private Category category;
    private Integer rating;
    private final LocalDate createdDate;
    private LocalDate modifiedDate;

    public Product(String name) {
        this.createdDate = LocalDate.now();
        this.modifiedDate = LocalDate.now();
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public Product(String name, Category category, Integer rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        createdDate = LocalDate.now();
        modifiedDate = LocalDate.now();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if(rating >= 0 && rating <= 10){
            this.rating = rating;
        }
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

}
