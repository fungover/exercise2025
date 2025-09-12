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
        products.add(product);
    }

    public boolean updateProduct(String id, String name, Category category, int rating) {
        if (id == null) throw new IllegalArgumentException("ID must not be null");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("ID must not be empty");
        if (name == null) throw new IllegalArgumentException("Name must not be null");
        if (name.trim().isEmpty()) throw new IllegalArgumentException("Name must not be empty");
        if (category == null) throw new IllegalArgumentException("Category must not be null");
        if (rating < 0 || rating > 10) throw new IllegalArgumentException("Rating must be between 0 and 10");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id().equals(id)) {
                products.set(i, new Product(id, name, category, rating, p.createdDate(), LocalDate.now()));
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
        return Collections.emptyList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return Collections.emptyList();
    }

    public List<Product> getModifiedProducts() {
        return Collections.emptyList();
    }
}