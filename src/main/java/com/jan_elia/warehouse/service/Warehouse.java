package com.jan_elia.warehouse.service;

import com.jan_elia.warehouse.entities.Category;
import com.jan_elia.warehouse.entities.Product;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    public void updateProduct(String id, String name, Category category, int rating) { }

    // getAllProducts: defensive, unmodifiable list
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

    public List<Product> getProductsByCategorySorted(Category category) { return null; }

    public List<Product> getProductsCreatedAfter(LocalDate date) { return null; }

    public List<Product> getModifiedProducts() { return null; }
}
