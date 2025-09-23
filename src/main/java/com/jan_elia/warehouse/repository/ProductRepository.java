package com.jan_elia.warehouse.repository;

import com.jan_elia.warehouse.entities.Product;

import java.util.List;
import java.util.Optional;

// Repository interface - defines data access methods
public interface ProductRepository {

    // Add a product
    void addProduct(Product product);

    // Get a product by id
    Optional<Product> getProductById(String id);

    // Get all products
    List<Product> getAllProducts();

    // Update an existing product
    void updateProduct(Product product);
}
