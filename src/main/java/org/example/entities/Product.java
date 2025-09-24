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
{ // nu tog jag bort den onödiga extra konstruktor jag gjort då den inte används.
    // Ändrade namn på Builder
    //Väntar på hjälp av Martin

    public static class Builder {
      private  String id;
      private String name;
      private Category category;
      private int rating;
      private LocalDateTime createdDate;
      private LocalDateTime modifiedDate;

      public Builder name(String name) {
            this.name = name;
            return this;
        }
      public Builder id(String id) {
          this.id = id;
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
      public Builder createdDate(LocalDateTime createdDate) {
          this.createdDate = createdDate;
          return this;
      }
      public Builder modifiedDate(LocalDateTime modifiedDate) {
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

          if (createdDate == null) createdDate = LocalDateTime.now();
          if (modifiedDate == null) modifiedDate = LocalDateTime.now();

          return new Product(id, name, category, rating, createdDate, modifiedDate);
      }
    }
}



