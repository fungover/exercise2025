package Exercise3.Entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {

    public Product{
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
    }

    public Product updateProduct(String id, String name, Category category, int rating) {

        if(id.trim().isEmpty() || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }

        if(rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Invalid rating");
        }

        return new Product(id,name,category,rating,this.createdDate, LocalDate.now());
    }
}
