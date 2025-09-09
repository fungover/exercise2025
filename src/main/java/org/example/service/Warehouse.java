package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }
        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (product.category() == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        if (product.rating() < 0 || product.rating() > 10) {
            throw new IllegalArgumentException("Product rating must be between 0 and 10");
        }
        if (product.createdDate() == null || product.modifiedDate() == null) {
            throw new IllegalArgumentException("Product dates cannot be null");
        }
        if (product.modifiedDate().isBefore(product.createdDate())) {
            throw new IllegalArgumentException("Modified date cannot be before created date");
        }
        if (products.stream().anyMatch(p -> p.id().equals(product.id()))) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }

        products.add(product);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        name = name.trim();
        if (category == null) {
            throw new IllegalArgumentException("Product category cannot be null");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Product rating must be between 0 and 10");
        }

        Product existing = getProductById(id);

        Product updated = new Product(
                id,
                name,
                category,
                rating,
                existing.createdDate(),
                LocalDate.now()
        );

        int index = products.indexOf(existing);
        products.set(index, updated);

        return updated;
    }


    public Product getProductById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null/blank");
        }
        return products.stream()
                .filter(p -> Objects.equals(p.id(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted((p1, p2) -> p1.name().compareToIgnoreCase(p2.name()))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }
        return products.stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return products.stream()
                .map(Product::category)
                .distinct()
                .toList();
    }

    public long countProductsInCategory(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return products.stream()
                .map(p -> Character.toUpperCase(p.name().charAt(0)))
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().intValue()
                ));
    }

    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate now = LocalDate.now();
        List<Product> thisMonth = products.stream()
                .filter(p -> p.createdDate().getMonth() == now.getMonth()
                        && p.createdDate().getYear() == now.getYear())
                .toList();

        OptionalInt maxRating = thisMonth.stream()
                .mapToInt(Product::rating)
                .max();

        if (maxRating.isEmpty()) return Collections.emptyList();

        return thisMonth.stream()
                .filter(p -> p.rating() == maxRating.getAsInt())
                .sorted((p1, p2) -> {
                    int cmp = p2.createdDate().compareTo(p1.createdDate());
                    if (cmp != 0) return cmp;
                    return p1.name().compareToIgnoreCase(p2.name());
                })
                .toList();
    }
}