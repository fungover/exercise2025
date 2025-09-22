package org.example.entities;

import java.time.LocalDateTime;

// att göra:
//valideringen i product constructor.
// fortsätt med uppg 4

public record Product (
        String id,
        String name,
        Category category,
        int rating,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
)
{  private Product(String id, String name, Category category, int rating) {
    this(
            id,
            name,
            category,
            rating,
            LocalDateTime.now(),
            LocalDateTime.now()
    );
}

    public static class ProductBuilder {
      private  String id;
      private String name;
      private Category category;
      private int rating;
      private LocalDateTime createdDate;
      private LocalDateTime modifiedDate;

      public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }
      public ProductBuilder id(String id) {
          this.id = id;
          return this;
      }
      public ProductBuilder category(Category category) {
          this.category = category;
          return this;
      }
      public ProductBuilder rating(int rating) {
          this.rating = rating;
          return this;
      }
      public ProductBuilder createdDate(LocalDateTime createdDate) {
          this.createdDate = createdDate;
          return this;
      }
      public ProductBuilder modifiedDate(LocalDateTime modifiedDate) {
          this.modifiedDate = modifiedDate;
          return this;
      }
      public Product build() {

          if (name == null || name.isBlank()) {
              throw new IllegalArgumentException("name is required");
          }
          if (id == null || id.isBlank()) {
              throw new IllegalArgumentException("id is required");
          }
          if (category == null) {
              throw new IllegalArgumentException("category is required");
          }
          if (rating < 0 || rating > 10) {
              throw new IllegalArgumentException("rating must be between 0 and 10");
          }

          return new Product(id, name, category, rating, createdDate, modifiedDate);
      }
    }
}



