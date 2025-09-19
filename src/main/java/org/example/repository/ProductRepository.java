package org.example.repository;

import org.example.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getAllProducts();
    void addProduct(Product product);
    Optional<Product> getProductById(String id);
    void updateProduct(Product product);
}
