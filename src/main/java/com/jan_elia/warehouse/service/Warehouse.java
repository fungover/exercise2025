package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;

import java.time.Clock;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final Clock clock;
    private final Map<String, Product> products = new HashMap<>();

    public Warehouse(Clock clock) {
        this.clock = clock;
    }

    // ---- validation helpers ----
    private void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be empty");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
    }

    private void validateCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
    }

    private void validateRating(int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be 0..10");
        }
    }

    private Product requireExisting(String id) {
        Product p = products.get(id);
        if (p == null) {
            throw new NoSuchElementException("product not found");
        }
        return p;
    }

    // ---- public API methods ----

    // addProduct: validate and store
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        validateId(product.getId());
        validateName(product.getName());
        validateCategory(product.getCategory());
        validateRating(product.getRating());
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("id already exists");
        }
        products.put(product.getId(), product);
    }

    // updateProduct: replace immutably, keep createdDate, update modifiedDate
    public void updateProduct(String id, String name, Category category, int rating) {
        validateId(id);
        validateName(name);
        validateCategory(category);
        validateRating(rating);

        Product old = requireExisting(id);

        Product updated = new Product.Builder()
                .id(old.getId())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(old.getCreatedDate())   // keep original createdDate
                .modifiedDate(LocalDate.now(clock))  // set new modifiedDate
                .build();

        products.put(id, updated);
    }

    // getAllProducts: defensive return
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    // getProductById: validate id and return, or throw if missing
    public Product getProductById(String id) {
        validateId(id);
        return requireExisting(id);
    }

    // getProductsByCategorySorted: filter + sort A-Z case-insensitive
    public List<Product> getProductsByCategorySorted(Category category) {
        validateCategory(category);
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(p -> p.getCategory().equals(category))
                        .sorted(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER))
                        .collect(Collectors.toList())
        );
    }

    // getProductsCreatedAfter: strictly after
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(p -> p.getCreatedDate().isAfter(date))
                        .collect(Collectors.toList())
        );
    }

    // getModifiedProducts: where createdDate != modifiedDate
    public List<Product> getModifiedProducts() {
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(p -> !p.getModifiedDate().equals(p.getCreatedDate()))
                        .collect(Collectors.toList())
        );
    }
}
