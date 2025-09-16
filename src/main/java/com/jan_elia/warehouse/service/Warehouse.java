package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;


public class Warehouse {

    private final Clock clock;
    private final Map<String, Product> products = new HashMap<>();

    public Warehouse(Clock clock) {
        this.clock = clock;
    }

    // addProduct: validate and store
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (product.getCategory() == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        if (product.getRating() < 0 || product.getRating() > 10) {
            throw new IllegalArgumentException("rating must be 0..10");
        }
        if (product.getId() == null || product.getId().isBlank()) {
            throw new IllegalArgumentException("id cannot be empty");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("id already exists");
        }

        products.put(product.getId(), product);
    }
    public void updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be empty");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating must be 0..10");
        }

        Product old = products.get(id);
        if (old == null) {
            throw new NoSuchElementException("product not found");
        }

        Product updated = new Product(
                old.getId(),
                name,
                category,
                rating,
                old.getCreatedDate(),              // keep original createdDate
                LocalDate.now(clock)               // set new modifiedDate
        );

        products.put(id, updated);
    }

    // getAllProducts:
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    // getProductById: validate id and return, or throw if missing
    public Product getProductById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be empty");
        }
        Product found = products.get(id);
        if (found == null) {
            throw new NoSuchElementException("product not found");
        }
        return found;
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(p -> p.getCategory().equals(category))
                        .sorted(Comparator.comparing(p -> p.getName().toLowerCase(Locale.ROOT)))
                        .collect(Collectors.toList())
        );
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(p -> p.getCreatedDate().isAfter(date)) // strictly after
                        .collect(Collectors.toList())
        );
    }

    public List<Product> getModifiedProducts() {
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(p -> !p.getModifiedDate().equals(p.getCreatedDate()))
                        .collect(Collectors.toList())
        );
    }
}
