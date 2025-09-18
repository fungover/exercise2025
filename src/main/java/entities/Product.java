package entities;

import java.time.LocalDate;
import java.util.Objects;

/** Changed from record to immutable class **/
public final class Product {
    private final String id;
    private final String name;
    private final Category category;
    private final int rating;
    private final LocalDate createdDate;
    private final LocalDate modifiedDate;

    /**
     * Private constructor available only for the builder
     */
    private Product(String id, String name, Category category, int rating, LocalDate createdDate, LocalDate modifiedDate) {
        this.id = Objects.requireNonNull(id, "id can not be null").trim();
        this.name = Objects.requireNonNull(name, "name can not be null").trim();
        this.category = Objects.requireNonNull(category, "category can not be null");
        this.createdDate = Objects.requireNonNull(createdDate, "createdDate can not be null");
        this.modifiedDate = Objects.requireNonNull(modifiedDate, "modifiedDate can not be null");

        if (id.isEmpty()) {
            throw new IllegalArgumentException("Id can not be empty");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        if (modifiedDate.isBefore(createdDate)) {
            throw new IllegalArgumentException("modifiedDate can not be before createdDate");
        }

        this.rating = rating;
    }

    /**
     * Getters (Instead of record functions)
     */
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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    //Builder class
    public static class Builder {
        private String id;
        private String name;
        private Category category;
        private int rating;
        private LocalDate createdDate = LocalDate.now();
        private LocalDate modifiedDate = LocalDate.now();

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

        public Builder createdDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder modifiedDate(LocalDate modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }

        public Product build() {
            return new Product(id, name, category, rating, createdDate, modifiedDate);
        }

    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}
