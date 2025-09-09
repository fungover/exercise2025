package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id().equals(id)) {
                Product updated = new Product(
                        id,
                        name,
                        category,
                        rating,
                        p.createdDate(),
                        LocalDate.now()
                );
                products.set(i, updated);
                return updated;
            }
        }
        throw new IllegalArgumentException("Product with ID " + id + " not found");
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted((p1, p2) -> p1.name().compareToIgnoreCase(p2.name()))
                .toList();
    }

    public List<Product> getProductsBasedOnDateSuccess(LocalDate date) {
        return products.stream()
                .filter(p-> p.createdDate().isAfter(date))
                .toList();
    }
}
