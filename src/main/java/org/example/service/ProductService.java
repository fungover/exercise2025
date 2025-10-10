package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        productRepository.addProduct(product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Product existing = productRepository.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with ID not found: " + id));
        Product updated = existing.withUpdatedValues(name, category, rating, LocalDate.now());
        productRepository.updateProduct(updated);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(String id) {
        return productRepository.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with ID not found: " + id));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(p -> !p.getCreatedDate().equals(p.getModifiedDate()))
                .collect(Collectors.toList());
    }
}
