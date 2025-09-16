package Exercise3.Entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {

    public Product updateProduct(String id, String name, Category category, int rating) {
        return new ProductBuilder().setId(id).setName(name).setCategory(category).setRating(rating).setCreatedDate(this.createdDate).setModifiedDate(LocalDate.now()).createProduct();
    }
}
