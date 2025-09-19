package org.example.service;
import java.time.LocalDateTime;
import org.example.entities.Category;
import org.example.entities.Product;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;

public class Warehouse {

    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        if (products.containsKey(product.id())){
        throw new IllegalArgumentException("Product already exists");
        }
        products.put(product.id(), product);
    }

    public void updateProduct(String id, String name, Category category, int rating){
        Product existing = products.get(id);
        if (existing == null) {
            throw new IllegalArgumentException("Product with ID " + id + " is not found");
        }

        Product updated = new Product(
                id,
                name,
                category,
                rating,
                existing.createdDate(),
                LocalDateTime.now().plusNanos(1)
        );

        products.put(id, updated);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    public Product getProductById(String id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
        return products.get(id);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.values().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime dateTime) {
        return products.values().stream()
                .filter(p -> p.createdDate().isAfter(dateTime))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return products.values().stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .collect(Collectors.toList());
    }
}