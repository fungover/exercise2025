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
        Objects.requireNonNull(product, "product");
        if (product.getId() == null || product.getId().isBlank())
            throw new IllegalArgumentException("Product id cannot be blank");
        if (product.getName().isBlank())
           throw new IllegalArgumentException("Product name cannot be blank");
        if (productMap.putIfAbsent(product.getId(), product) != null)
           throw new IllegalStateException("Product with ID already exists: " + product.getId());
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Objects.requireNonNull(id, "id");
         if (name == null || name.isBlank())
             throw new IllegalArgumentException("Product name cannot be blank");
         productMap.compute(id, (k, existing) -> {
             if (existing == null)
                 throw new NoSuchElementException("Product with ID not found: " + id);
             return existing.withUpdatedValues(name, category, rating, LocalDate.now());
         });
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
