package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final Map<UUID, Product> products = new LinkedHashMap<>();

    public void addProduct(Product product) {

        UUID productId;
        try {
            productId = UUID.fromString(product.id());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Product ID must be a valid UUID");
        }

        if (products.containsKey(productId)) {
            throw new IllegalArgumentException("Product ID already exists");
        }

        products.put(productId, product);
    }

    public List<Product> getAllProducts() {
        return products.values()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Product> getProductById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.ofNullable(products.get(uuid));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    //sorted by name a-z
    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return products.values()
                .stream()
                .filter(product -> category.equals(product.category()))
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Product> updateProduct(String id, String name, Category category, int rating) {

        UUID productId;
        try {
            productId = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Product ID must be a valid UUID");
        }

        return Optional.ofNullable(products.computeIfPresent(productId, (key, existingProduct) ->
                new Product(
                        existingProduct.id(),
                        name,
                        category,
                        rating,
                        existingProduct.createdDate(),
                        ZonedDateTime.now()
                )
        ));
    }

    public List<Product> getProductsCreatedAfter(ZonedDateTime dateTime) {
        return products.values().stream()
                .filter(product -> product.createdDate().isAfter(dateTime))
                .collect(Collectors.toList());
    }

    //returns products that have been modified since the given date
    public List<Product> getModifiedProducts() {
        return products.values().stream()
                .filter(product -> !product.createdDate().isEqual(product.modifiedDate()))
                .collect(Collectors.toUnmodifiableList());
    }
}
