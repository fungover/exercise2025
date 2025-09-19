package org.example.repository;

import org.example.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void addProduct(Product product);
    void updateProduct(Product product);
    List<Product> getAllProducts();
    Optional<Product> getProductById(String id);
}