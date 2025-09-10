package Exercise3.Entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {

    public Product(String id, String name, Category category, int rating) {
        if (id.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }

        this(id,name,category,rating,LocalDate.now(),LocalDate.now());
    }

    public Product(String id, String name, Category category, int rating, LocalDate createdDate) {
        if (id.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }

        this(id,name,category,rating,createdDate,LocalDate.now());
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
        if (id.isEmpty() || name.isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }

        return new Product(id,name,category,rating,this.createdDate, LocalDate.now());
    }
}
