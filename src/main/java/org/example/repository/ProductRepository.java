package org.example.repository;

import org.example.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void addProduct(Product product);
    Optional<Product> getProductById(String id);
    List<Product> getAllProducts();
    void updateProduct(Product product);
}
