package entities;

import java.time.LocalDateTime;

public record Product(int id, String name, Category category, int rating, LocalDateTime createdAt,
                      LocalDateTime modifiedAt) {

    public static class Builder {
        private int id;
        private String name;
        private Category category;
        private int rating;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public Builder id(int id) {
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

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder modifiedAt(LocalDateTime modifiedAt) {
            this.modifiedAt = modifiedAt;
            return this;
        }

        public Product build() {
            return new Product(id, name, category, rating, createdAt, modifiedAt);
        }

    }


    @Override
    public String toString() {
        return "Product{id=" + id + ", name='" + name + "', category=" + category + ", rating=" + rating
                + "created at=" + createdAt + "modified at=" + modifiedAt + "}";

    }


}
