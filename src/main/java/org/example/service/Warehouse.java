package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Warehouse {
    private final InMemoryProductRepository products;

    public Warehouse(InMemoryProductRepository inMemoryProductRepository) {
    this.products = inMemoryProductRepository;
    }

    // Methods
    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return products.getAllProducts().stream()
                .filter(p -> p.getCategory() == category)
                .sorted((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        return products.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().isAfter(date) || p.getCreatedDate().isEqual(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.getAllProducts().stream()
                .filter(p -> p.getModifiedDate().isAfter(p.getCreatedDate()))
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return products.getAllProducts().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public long countProductsInCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return products.getAllProducts().stream()
                .filter(p -> p.getCategory() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return products.getAllProducts().stream()
                .map(Product::getName)
                .filter(name -> !name.isEmpty())
                .map(name -> name.charAt(0))
                .collect(Collectors.toMap(name -> name, name -> 1, Integer::sum));
    }


    public List<Product> getTopRatedProductsThisMonth() {
        LocalDateTime now = LocalDateTime.now();
        int maxRating = products.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().getMonth().equals(now.getMonth())
                        && p.getCreatedDate().getYear() == now.getYear())
                .map(Product::getRating).max(Integer::compareTo).orElse(0);

        return products.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().getMonth().equals(now.getMonth())
                        && p.getCreatedDate().getYear() == now.getYear())
                .filter(p -> p.getRating() == maxRating)
                .sorted((p1, p2) -> p2.getCreatedDate().compareTo(p1.getCreatedDate()))
                .toList();
    }
}
