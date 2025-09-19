package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public boolean updateProduct(String id, String name, Category category, int rating) {
        if (id == null) throw new IllegalArgumentException("ID must not be null");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("ID must not be empty");
        Optional<Product> existingProduct = productRepository.getProductById(id);
        if (existingProduct.isPresent()) {
            Product p = existingProduct.get();
            Product updatedProduct = new Product.Builder()
                    .id(p.id())
                    .name(name)
                    .category(category)
                    .rating(rating)
                    .createdDate(p.createdDate())
                    .modifiedDate(LocalDate.now())
                    .build();
            productRepository.updateProduct(updatedProduct);
            return true;

        }
        return false;
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Product getProductById(String id) {
        if (id == null) throw new IllegalArgumentException("ID must not be null");
        if (id.trim().isEmpty()) throw new IllegalArgumentException("ID must not be empty");
        return productRepository.getProductById(id).orElse(null);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        if (category == null) return Collections.emptyList();
        return productRepository.getAllProducts().stream()
                .filter(p -> p.category().equals(category))
                .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) return Collections.emptyList();

        return productRepository.getAllProducts().stream()
                .filter(p -> p.createdDate() != null && p.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.modifiedDate() != null && !p.modifiedDate().equals(p.createdDate()))
                .toList();
    }
}