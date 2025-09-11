package org.example.service;

import org.example.entities.Product;
import org.example.entities.Category;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {
    private final Map<String, Product> productMap = new ConcurrentHashMap<>();

    public void addProduct(Product product) {
        if (product.getName().isEmpty())
            throw new IllegalArgumentException("Product name cannot be empty");
        productMap.put(product.getId(), product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Product existing = productMap.get(id);
        if (existing == null)
            throw new NoSuchElementException("Product with ID not found: " + id);
        Product updated = existing.withUpdatedValues(name, category, rating, LocalDate.now());
        productMap.put(id, updated);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    public Product getProductById(String id) {
        Product product = productMap.get(id);
        if (product == null)
            throw new NoSuchElementException("Product with ID not found: " + id);
        return product;
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productMap.values().stream()
                .filter(p -> p.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productMap.values().stream()
                .filter(p -> p.getCreatedDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productMap.values().stream()
                .filter(p -> !p.getCreatedDate().equals(p.getModifiedDate()))
                .collect(Collectors.toList());
    }
}
