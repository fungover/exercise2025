package org.warehouse.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(String id,
                      String name,
                      Category category,
                      int rating,
                      LocalDate createdDate,
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

    public static class Builder {
    private String id;
    private String name;
    private Category category;
    private int rating;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setCategory(Category category) {
      this.category = category;
      return this;
    }

    public Builder setRating(int rating) {
      this.rating = rating;
      return this;
    }

    public Builder setCreatedDate(LocalDate createdDate) {
      this.createdDate = createdDate;
      return this;
    }

    public Builder setModifiedDate(LocalDate modifiedDate) {
      this.modifiedDate = modifiedDate;
      return this;
    }

    public Product build() {
      return new Product(id, name, category, rating, createdDate, modifiedDate);
    }
    }
}