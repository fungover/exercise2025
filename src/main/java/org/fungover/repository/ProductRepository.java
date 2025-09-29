package org.fungover.repository;

import org.fungover.entities.Category;
import org.fungover.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void addProduct(Product product);
    void updateProduct(Product product);
    Optional<Product> getProductById(String id);
    List<Product> getAllProducts();
    List<Product> getProductsCreatedAfter(LocalDate date);
    List<Product> getModifiedProducts();
    List<Product> getProductsByCategory(Category category);
}
