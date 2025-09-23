package org.example.entities;

import java.time.LocalDate;
import java.util.Objects;

public record Product(
  String id,
  String name,
  Category category,
  int rating,
  LocalDate createdDate,
  LocalDate modifiedDate
) {
  public Product {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(name, "Name cannot be null");
    Objects.requireNonNull(category, "Category cannot be null");
    Objects.requireNonNull(createdDate, "Created date cannot be null");
    Objects.requireNonNull(modifiedDate, "Modified date cannot be null");

    
    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    if (rating < 0 || rating > 10) {
      throw new IllegalArgumentException("Rating must be between 0 and 10");
    }
  }

  //--Helper method to create a new product with updated fields--
  public Product withUpdatedFields(String newName, Category newCategory, int newRating) {
    return withUpdatedFields(newName, newCategory, newRating, LocalDate.now());
  }

  //--Helper method with explicit modified date for better testability--
  public Product withUpdatedFields(String newName, Category newCategory, int newRating, LocalDate modifiedDate) {
    Objects.requireNonNull(newName, "Name cannot be null");
    Objects.requireNonNull(newCategory, "Category cannot be null");
    Objects.requireNonNull(modifiedDate, "Modified date cannot be null");

    if (newName.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    if (newRating < 0 || newRating > 10) {
      throw new IllegalArgumentException("Rating must be between 0 and 10");
    }

    return new Product(id, newName.trim(), newCategory, newRating, createdDate, modifiedDate);
  }
}