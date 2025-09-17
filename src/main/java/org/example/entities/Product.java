package org.example.entities;

import org.example.decorator.Sellable;

import java.time.LocalDate;

public record Product(
        String id,
        String name,
        Category category,
        int rating,
        double price, // Price attribute added for decorator pattern (extra credit assignment).
        LocalDate createdDate,
        LocalDate modifiedDate
) implements Sellable {
    /**
     * Private contructor which takes a Builder-instance as parameter.
     * <p>
     * WHY PRIVATE?
     * - This forces all products to be created through the Builder class.
     * - It ensures direct constructor calls are not possible, like new Product(...).
     * - Ensures validation through the Builder's build() method.
     * <p>
     * WHERE GOES THE DATA?
     * - The Builder-object is sent to this constructor with its fields already set.
     * - We then take the values from the Builder and pass them to the record constructor.
     *
     */

    private Product(Builder builder) {
        this(
                // Here we copy the values from the Builder to the record's fields.
                builder.id,
                builder.name,
                builder.category,
                builder.rating,
                builder.price, // Price field added here aswell.
                builder.createdDate,
                builder.modifiedDate
        );
    }

    /**
     * Static nested Builder class which implements the Builder pattern for creating Product instances.
     * <p>
     * WHY STATIC NESTED CLASS?
     * - static: Kan be used without creating an instance of the outer class (Product).
     * - nested: Logically connected to Product, same file and can access private members.
     * - Can be used as: new Product.Builder().id("1").name("Laptop").build();
     */

    public static class Builder {
        // Builder fields corresponding to Product fields.
        private String id;
        private String name;
        private Category category;
        private int rating;
        private double price;
        private LocalDate createdDate;
        private LocalDate modifiedDate;

        /**
         * Empty constructor for Builder.
         * <p>
         * - WHY IS IT EMPTY?
         * - All data is set through the setter methods like .id(), .name(), etc.
         * - This give maximum flexibility in the order of setting fields.
         * <p>
         * - USAGE:
         * - new Product.Builder() -> Empty builder instance.
         * - .id("1") -> sets the id field with method.
         * - .name("Laptop") -> sets the name field with method.
         * - .build() -> Validates and creates the Product instance.
         */

        public Builder() {
        }

        /**
         * Setter methods which return 'this' for method chaining.
         * <p>
         * - METHOD CHAINING:
         * - Each method return the Builder-object (this).
         * - Makes it possible to chain calls like: .id("1").name("hello") etc.
         * - Without chaining, we would need to call each method separately.
         * - With chaining: new Product.Builder().id("1").name("Laptop").build();
         */

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

        public Builder price(double price) { // Price setter method added for decorator pattern (extra credit assignment).
            this.price = price;
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

        /**
         * build() is the method that actually creates the Product-object.
         * <p>
         * WHAT HAPPENS IN build()?
         * 1. Validates all required fields are set and valid.
         * 2. Sets date today if they are not provided.
         * 3. Calls the private Product constructor with 'this' Builder, which extracts all field values.
         * 4. Returns the newly created, immutable Product-object.
         */

        public Product build() {

            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID is required");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }

            if (category == null) {
                throw new IllegalArgumentException("Category is required");
            }

            if (rating < 0 || rating > 10) {
                throw new IllegalArgumentException("Rating must be between 0 and 10");
            }

            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }

            if (createdDate == null) {
                this.createdDate = LocalDate.now();
            }
            if (modifiedDate == null) {
                this.modifiedDate = LocalDate.now();
            }
            return new Product(this);
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
