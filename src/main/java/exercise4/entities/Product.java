package exercise4.entities;

import java.time.LocalDateTime;
import java.util.UUID;


public final class Product {
    private final String id;
    private final String name;
    private Category category;
    private Integer rating;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.category = builder.category;
        this.rating = builder.rating;
        this.createdDate = builder.createdDate;
        this.modifiedDate = builder.modifiedDate;
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


    public Integer getRating() {
        return rating;
    }


    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private Integer rating;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        public Builder(){
            this.id = UUID.randomUUID().toString();
            createdDate = LocalDateTime.now();
            modifiedDate = createdDate;
        }
        public Builder setId(String id) {
            this.id = id;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setCategory(Category category){
            this.category = category;
            return this;
        }
        public Builder setRating(Integer rating){
            this.rating = rating;
            return this;
        }
        public Builder setCreatedDate(LocalDateTime createdDate){
            this.createdDate = createdDate;
            return this;
        }
        public Builder setModifiedDate(LocalDateTime modifiedDate){
            this.modifiedDate = modifiedDate;
            return this;
        }
        public Product build() {
            return new Product(this);
        }

    }

}
