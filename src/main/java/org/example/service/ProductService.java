package org.example.service;


import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Product existing = productRepository.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Product updated = new Product.Builder()
                .id(existing.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existing.createdDate())
                .modifiedDate(LocalDateTime.now())
                .build();

        productRepository.updateProduct(updated);
    }

    public Product getProductById(String id) {
        return productRepository.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.createdDate().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(p -> !p.createdDate().truncatedTo(ChronoUnit.SECONDS)
                        .equals(p.modifiedDate().truncatedTo(ChronoUnit.SECONDS)))
                .collect(Collectors.toList());
    }
}
