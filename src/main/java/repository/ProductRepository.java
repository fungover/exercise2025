package repository;

import entities.Product;
import java.util.List;
import java.util.Optional;

// Defines the contract for how we store and retrieve products.

public interface ProductRepository {

    // Saves a product to the repository (add or update)
    void save(Product product);

    // Adding a new product to the repository
    void addProduct(Product product);

    // Retrieves a product based on its ID
    Optional<Product> getProductById(String id);

    // Finds a product by ID (alias for getProductById)
    Optional<Product> findById(String id);

    // Retrieves all products in repository
    List<Product> getAllProducts();

    // Finds all products (alias for getAllProducts)
    List<Product> findAll();

    // Updating an existing product
    void updateProduct(Product product);

    // Deletes a product based on ID
    boolean removeProduct(String id);

    // Checks if a product with a given ID exists
    boolean existsById(String id);

    // Retrieves total number of products
    long count();
}