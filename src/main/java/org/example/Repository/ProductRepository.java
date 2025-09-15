package org.example.Repository;

import org.example.entities.Product;
import java.util.List;
import java.util.Optional;

/**
 * Interface for the product data access.
 * Used to add products,
 * Get a product by its ID,
 * Returns all products in the repository,
 * Updates an existing product in the repository.
 */

public interface ProductRepository {
    void addProduct(Product product);
    Optional<Product> getProduct(String id);
    List<Product> getAllProducts();
    void updateProduct(Product product);
}
