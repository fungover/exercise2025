package org.warehouse.entities;

import java.time.LocalDate;
import java.util.Objects;

public final class Product {
  private final String id;
  private final String name;
  private final Category category;
  private final int rating;
  private final LocalDate createdDate;
  private final LocalDate modifiedDate;

  private Product(String id,
                  String name,
                  Category category,
                  int rating,
                  LocalDate createdDate,
                  LocalDate modifiedDate) {
    if (id == null) throw new IllegalArgumentException("id must not be null");
    if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be null or blank");
    if (category == null) throw new IllegalArgumentException("category must not be null");
    if (rating < 0 || rating > 10) throw new IllegalArgumentException("rating must be between 0 and 10");
    if (createdDate == null) throw new IllegalArgumentException("createdDate must not be null");
    if (modifiedDate == null) throw new IllegalArgumentException("modifiedDate must not be null");
    if (modifiedDate.isBefore(createdDate)) throw new IllegalArgumentException("modifiedDate must not be before createdDate");

    this.id = id;
    this.name = name;
    this.category = category;
    this.rating = rating;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  public String id() {
    return id;
  }
  public String name() {
    return name;
  }
  public Category category() {
    return category;
  }
  public int rating() {
    return rating;
  }
  public LocalDate createdDate() {
    return createdDate;
  }
  public LocalDate modifiedDate() {
    return modifiedDate;
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
      LocalDate now = LocalDate.now();
      if(createdDate == null) createdDate = now;
      if(modifiedDate == null) modifiedDate = now;

      return new Product(id, name, category, rating, createdDate, modifiedDate);
    }
  }
}