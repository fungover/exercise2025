package entities;

import java.time.LocalDate;
import java.util.Objects;

/**
 * All methods in my warehouse will be working with this Product object.
 * It is an immutable class that provides me with methods <p> fields, getters, equals,
 * hashCode & toString</p> These I typed myself when making a manual class
 * before converting Product to a record.
 **/
public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {

    public Product {
        id = Objects.requireNonNull(id, "id can not be null").trim();
        name = Objects.requireNonNull(name, "name can not be null").trim();
        category = Objects.requireNonNull(category, "category can not be null");
        createdDate = Objects.requireNonNull(createdDate, "createdDate can not be null");
        modifiedDate = Objects.requireNonNull(modifiedDate, "modifiedDate can not be null");

        if (id.isEmpty()) {
            throw new IllegalArgumentException("Id can not be empty");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("modifiedDate can not be before createdDate");
        }
    }

    /**
     * If I want to change the object in the future, without breaking immutability
     **/
    public Product withName(String newName, LocalDate newModifiedDate) {
        return new Product(id, newName, category, rating, createdDate, modifiedDate);
    }

    //
    public Product withCategory(Category newCategory, LocalDate newModifiedDate) {
        return new Product(id, name, newCategory, rating, createdDate, newModifiedDate);
    }

    public Product withRating(int newRating, LocalDate newModifiedDate) {
        return new Product(id, name, category, rating, createdDate, modifiedDate);
    }


    /**
     * I will keep this even though it is not necessary when using a record
     **/
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
