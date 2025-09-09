package org.example.service;

import org.example.entities.Product;
import org.example.entities.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }

        boolean idExists = products.stream()
                .anyMatch(p -> p.id().equals(product.id()));
        if (idExists) {
            throw new IllegalArgumentException("product with id " + product.id() + " already exists");
        }

        products.add(product);
    }
    // Returnerar en kopia av lagret för att skydda intern lista
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("id not found: " + id));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(p -> !p.modifiedDate().equals(p.createdDate()))
                .toList();
    }

    public List<Category> getCategoriesWithProducts() {
        return products.stream()
                .map(Product::category)
                .distinct()
                .toList();
    }

    public int countProductsByCategory(Category category) {
        return (int) products.stream()
                .filter(p -> p.category().equals(category))
                .count();
    }
}
