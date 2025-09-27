package org.example.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Product implements Sellable {
  private final String id;
  private final String name;
  private final Category category;
  private final int rating;
  private final BigDecimal price;
  private final LocalDate createdDate;
  private final LocalDate modifiedDate;

  // Private constructor - can only be used by the Builder
  private Product(String id, String name, Category category, int rating, BigDecimal price, LocalDate createdDate, LocalDate modifiedDate) {
    Objects.requireNonNull(id, "ID cannot be null");
    Objects.requireNonNull(name, "Name cannot be null");
    Objects.requireNonNull(category, "Category cannot be null");
    Objects.requireNonNull(price, "Price cannot be null");
    Objects.requireNonNull(createdDate, "Created date cannot be null");
    Objects.requireNonNull(modifiedDate, "Modified date cannot be null");

    if (name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    if (rating < 0 || rating > 10) {
      throw new IllegalArgumentException("Rating must be between 0 and 10");
    }
    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Price cannot be negative");
    }

    this.id = id;
    this.name = name.trim();
    this.category = category;
    this.rating = rating;
    this.price = price;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  //-- replacing record accessors--
  public String id() { return id; }
  public String name() { return name; }
  public Category category() { return category; }
  public int rating() { return rating; }
  public BigDecimal price() { return price; }
  public LocalDate createdDate() { return createdDate; }
  public LocalDate modifiedDate() { return modifiedDate; }

  //-- Sellable interface implementation --
  @Override
  public String getId() { return id(); }

  @Override
  public String getName() { return name(); }

  @Override
  public BigDecimal getPrice() { return price(); }

  //-- Builder Pattern --
  public static class Builder {
    private String id;
    private String name;
    private Category category;
    private int rating;
    private BigDecimal price;
    private LocalDate createdDate;
    private LocalDate modifiedDate;

    public Builder id(String id) {
      this.id = id;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder category(Category category) {
      this.category = category;
      return this;
    }

    public Builder rating(int rating) {
      this.rating = rating;
      return this;
    }

    public Builder price(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Builder createdDate(LocalDate createdDate) {
      this.createdDate = createdDate;
      return this;
    }

    public Builder modifiedDate(LocalDate modifiedDate) {
      this.modifiedDate = modifiedDate;
      return this;
    }

    public Product build() {
      // Set default values if there is not
      if (createdDate == null) {
        createdDate = LocalDate.now();
      }
      if (modifiedDate == null) {
        modifiedDate = LocalDate.now();
      }
      if (price == null) {
        price = BigDecimal.ZERO;
      }


      return new Product(id, name, category, rating, price, createdDate, modifiedDate);
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

    return new Product.Builder()
        .id(this.id)
        .name(newName)
        .category(newCategory)
        .rating(newRating)
        .price(this.price)
        .createdDate(this.createdDate)
        .modifiedDate(modifiedDate)
        .build();
  }

  // --replacing record--
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Product product = (Product) obj;
    return rating == product.rating &&
           Objects.equals(id, product.id) &&
           Objects.equals(name, product.name) &&
           Objects.equals(category, product.category) &&
           Objects.equals(price, product.price) &&
           Objects.equals(createdDate, product.createdDate) &&
           Objects.equals(modifiedDate, product.modifiedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, category, rating, price, createdDate, modifiedDate);
  }

  @Override
  public String toString() {
    return "Product{" +
           "id='" + id + '\'' +
           ", name='" + name + '\'' +
           ", category=" + category +
           ", rating=" + rating +
           ", price=" + price +
           ", createdDate=" + createdDate +
           ", modifiedDate=" + modifiedDate +
           '}';
  }
}