package Exercise3.Entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {

    public Product{
        if (id.trim().isEmpty() || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }
    }

    public Product updateProduct(String id, String name, Category category, int rating) {

        if (id.trim().isEmpty() || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }

        return new Product(id,name,category,rating,this.createdDate, LocalDate.now());
    }
}
