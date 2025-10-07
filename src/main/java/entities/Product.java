package entities;

import java.time.LocalDate;


public record Product (    //Record för att köra immutable (objekt som inte kan ändras) samt renare och kortare kod.
    String id,
    String name,
    entities.Category category,
    int rating,
    LocalDate createdDate,
    LocalDate modifiedDate
) {

    public static class Builder {
        private String id;
        public String name;
        private entities.Category category;
        private int rating;
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

        public Builder category(entities.Category category) {
            this.category = category;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
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
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Product name cannot be empty.");
            }

            LocalDate now = LocalDate.now();
            LocalDate created = (createdDate != null) ? createdDate : now;
            LocalDate modified = (modifiedDate != null) ? modifiedDate : now;

            return new Product(id, name, category, rating, created, modified);
        }
    }
}
