package exersice4.enteties;

import java.time.LocalDateTime;
import java.util.UUID;


public final class Product {
    private final String id;
    private String name;
    private Category category;
    private Integer rating;
    private final LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Product(String name) {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = createdDate;
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public Product(String name, Category category, Integer rating) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        createdDate = LocalDateTime.now();
        modifiedDate = createdDate;
        this.id = UUID.randomUUID().toString();
    }
    public Product(String name, Category category, Integer rating, LocalDateTime createdDate) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        modifiedDate = LocalDateTime.now();
        this.id = UUID.randomUUID().toString();
    }
    public Product(String name, Category category, Integer rating, LocalDateTime createdDate, String id) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdDate = createdDate;
        modifiedDate = LocalDateTime.now();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
  /*  public void setName(String name){
        if(name == null){
            System.out.println("Name cannot be null");
        }else{
            this.name = name;
        }
    }*/

    public Category getCategory() {
        return category;
    }

 /*   public void setCategory(Category category) {
        if(category == null){
            System.out.println("Category cannot be null");
        }else{
            this.category = category;
        }
    }*/

    public Integer getRating() {
        return rating;
    }

  /*  public void setRating(Integer rating) {
        if(rating == null){
            System.out.println("Rating cannot be null");
            return;
        }
        if(rating < 0 || rating > 10){
            System.out.println("Rating must be between 0 and 10");
            return;
        }
        this.rating = rating;
    }*/

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

   /* public void setModifiedDate(LocalDateTime modifiedDate) {
        if(modifiedDate == null){
            System.out.println("ModifiedDate cannot be null");
            return;
        }
        this.modifiedDate = modifiedDate;
    }*/

}
