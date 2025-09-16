package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    boolean addProduct(Product product);

    Optional<Product> getProductById(String id);

    List<Product> getAllProducts();

    void updateProduct(Product product);

    Product removeProductById(String id);

    List<Product> findByCategory(Category category);


    List<Product> findCreatedAfter(LocalDate date);

    List<Product> findModified();
}
