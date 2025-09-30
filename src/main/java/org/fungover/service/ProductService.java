package org.fungover.service;

import org.fungover.entities.Category;
import org.fungover.entities.Product;
import org.fungover.repository.ProductRepository;

import java.time.LocalDate;
import java.util.*;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        productRepository.updateProduct(new Product(id, name, rating, category, null, null));
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public Optional<Product> getProduct(String id) {
        return productRepository.getProductById(id);
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.getProductsByCategory(category);
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.getProductsCreatedAfter(date);
    }

    public List<Product> getModifiedProducts() {
        return productRepository.getModifiedProducts();
    }

}
