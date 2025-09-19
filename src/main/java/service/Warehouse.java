package service;


import entities.Category;
import entities.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product must not be null");
        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        if (products.stream().anyMatch(p -> p.id() == product.id())) {
            throw new IllegalArgumentException("Duplicate product id: " + product.id());
        }
        products.add(product);
    }

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void updateProduct(int id, String name, Category category, int rating) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name can not be empty");
        }
        Objects.requireNonNull(category, "Category must not be null");
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            if (p.id() == id) {
                Product updated = new Product(id, name, category, rating, p.createdAt(), LocalDateTime.now());
                products.set(i, updated);
                return;
            }
        }
        throw new NoSuchElementException("Product with id " + id + " not found");
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .toList();

    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.createdAt().toLocalDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
            return products.stream()
                    .filter(p -> !p.createdAt().equals(p.modifiedAt()))
                    .toList();
        }
    }
