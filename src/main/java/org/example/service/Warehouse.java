package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    // Getters
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(String id) {
      return products.stream()
              .filter(p -> p.getId().equals(id))
              .findFirst()
              .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    // Methods
    public void addProduct(Product product) {
        if (product == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        products.add(product);
    }

    public void updateProduct(Product product) {
        if (product == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }

        int index = IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId() == product.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        products.set(index, product);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.getCategory() == category)
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        return products.stream()
                .filter(p -> p.getCreatedDate().isAfter(date) || p.getCreatedDate().isEqual(date))
                .toList();
    }
}
