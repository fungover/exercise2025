package org.example.service;


import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product); // remove validation from here because its now done in the build method
    } //färdig

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Product existing = products.get(id);

        if (existing == null) {
            throw new IllegalArgumentException("Product not found");
        }

        Product updated = new Product.ProductBuilder()
                .id(existing.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existing.createdDate())
                .modifiedDate(LocalDateTime.now())
                .build();

        products.put(id, updated);
    } //färdig

    public Product getProductById(String id) {
        Product product = products.get(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        return product;
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.values().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.values().stream()
                .filter(p -> p.createdDate().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return products.values().stream()
                .filter(p -> !p.createdDate().truncatedTo(ChronoUnit.SECONDS)
                        .equals(p.modifiedDate().truncatedTo(ChronoUnit.SECONDS)))
                .collect(Collectors.toList());
    }
}
