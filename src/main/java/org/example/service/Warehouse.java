package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    // Getters
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Optional<Product> getProductById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }

        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    // Methods
    public void addProduct(Product product) {
        if (product == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        products.add(product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (rating < 1 || rating > 10){
            throw new IllegalArgumentException("Rating must be between 1 and 10");
        }

        int index = IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Product existingProduct = products.get(index);
        Product updatedProduct = existingProduct.update(name, category, rating);

        products.set(index, updatedProduct);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return products.stream()
                .filter(p -> p.getCategory() == category)
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        return products.stream()
                .filter(p -> p.getCreatedDate().isAfter(date) || p.getCreatedDate().isEqual(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> p.getModifiedDate().isAfter(p.getCreatedDate()))
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }
}
