package org.warehouse.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(String id, String name, Category category, int rating, LocalDate createdDate,
                      LocalDate modifiedDate) {
  public Product {
    Objects.requireNonNull(id, "id must not be null");
    Objects.requireNonNull(name, "name must not be null");
    if (name.isBlank()) throw new IllegalArgumentException("name must not be blank");
    Objects.requireNonNull(category, "category must not be null");
    Objects.requireNonNull(createdDate, "createdDate must not be null");
    Objects.requireNonNull(modifiedDate, "modifiedDate must not be null");
    if (rating < 0 || rating > 10) {
      throw new IllegalArgumentException("rating must be between 0 and 10");
      }
    if (modifiedDate.isBefore(createdDate)) {
      throw new IllegalArgumentException("modifiedDate must not be before createdDate");
      }
    }
}
