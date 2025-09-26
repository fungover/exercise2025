package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    private Product(String id, String name, Category category, int rating, LocalDateTime createdAt,
            LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

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
                + "created at=" + createdAt + ", modified at=" + modifiedAt + "}";

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

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public LocalDateTime modifiedAt() {
        return modifiedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.category, that.category) &&
                this.rating == that.rating &&
                Objects.equals(this.createdAt, that.createdAt) &&
                Objects.equals(this.modifiedAt, that.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, rating, createdAt, modifiedAt);
    }


}
