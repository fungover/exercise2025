package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        products.add(product);
    }


    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id().equals(id)) {
                Product updated = new Product(
                        p.id(),
                        name,
                        category,
                        rating,
                        p.createdDate(),
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
        }


