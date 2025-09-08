package org.example.service;


import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
public class Warehouse {

    private final Map<String, Product> products = new HashMap<>();

    // --- G-level methods ---

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product with this ID already exists");
        }

        products.put(product.getId(), product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or empty");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        Product existing = products.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Product with id " + id + " does not exist");
        }

        Product updated = existing.withUpdates(name, category, rating);
        products.put(id, updated);
    }

    /**
     * Retrieve all products in a given category, sorted alphabetically by name.
     *
     * @param category the category to filter by
     * @return a list of products in the category, sorted A–Z
     */

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.values()
                .stream()
                .filter(p -> p.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return null; // TODO: implement
    }

    public List<Product> getModifiedProducts() {
        return null; // TODO: implement
    }

    // --- VG-level methods ---

    public Set<Category> getCategoriesWithProducts() {
        return null; // TODO: implement
    }

    public long countProductsInCategory(Category category) {
        return 0; // TODO: implement
    }

    public Map<Character, Long> getProductInitialsMap() {
        return null; // TODO: implement
    }

    public List<Product> getTopRatedProductsThisMonth() {
        return null; // TODO: implement
    }
}
