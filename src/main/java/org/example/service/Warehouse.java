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
        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
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

    public List<Product> getProductsCreatedAfter(LocalDate date) {
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
                .map(p -> p.name().charAt(0))
                .map(Character::toUpperCase)
                .collect(Collectors.toMap(
                        c -> c,
                        c -> 1,
                        Integer::sum
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
                    int cmp = p2.createdDate().compareTo(p1.createdDate()); // newest first
                    if (cmp != 0) return cmp;
                    return p1.name().compareToIgnoreCase(p2.name()); // tie-breaker
                })
                .toList();
    }
}