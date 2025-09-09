package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.stream.Collectors;


public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.rating() < 0 || product.rating() > 10) {
            throw new IllegalArgumentException("Product rating must be between 0 and 10");
        }
        products.add(product);
    }


    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            if (product.id().equals(id)) {
                Product updated = new Product(
                        product.id(),
                        name,
                        category,
                        rating,
                        product.createdDate(),
                        LocalDate.now()
                );
                products.set(i, updated);
                return;
            }
        }
        throw new IllegalArgumentException("Product not found");
    }
    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }
    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return products.stream()
                .filter(p -> p.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }
    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .collect(Collectors.toList());
     }
    }



