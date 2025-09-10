package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name() == null || product.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Optional<Product> getProductById(String id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst();
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate cutoffDate) {
        return products.stream()
                .filter(product -> product.createdDate().isAfter(cutoffDate))
                .collect(Collectors.toList());
    }
}
