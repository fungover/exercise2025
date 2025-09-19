package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {
    private final List<Product> products;

    public Warehouse() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product must not be null");
        boolean exists = products.stream().anyMatch(p -> p.id().equals(product.id()));
        if (exists) throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        products.add(product);
    }

    public boolean updateProduct(String id, String name, Category category, int rating) {
        if (id == null) throw new IllegalArgumentException("ID must not be null");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("ID must not be empty");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id().equals(id)) {
                Product updatedProduct = new Product.Builder()
                        .id(id)
                        .name(name)
                        .category(category)
                        .rating(rating)
                        .createdDate(p.createdDate())
                        .modifiedDate(LocalDate.now())
                        .build();
            products.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductById(String id) {
        if (id == null) throw new IllegalArgumentException("ID must not be null");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("ID must not be empty");
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) return Collections.emptyList();
        return products.stream()
                .filter(p -> p.category().equals(category))
                .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) return Collections.emptyList();

        return products.stream()
                .filter(p -> p.createdDate() != null && p.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> p.modifiedDate() != null && !p.modifiedDate().equals(p.createdDate()))
                .toList();
    }
}