package repository;

import entities.Product;
import java.util.List;
import java.util.Optional;

 //Repository interface for Product data access operations.
//Defines the contract for how we store and retrieve products.

public interface ProductRepository {

     // Adding a new product to the repository
    void addProduct(Product product);

    // Retrieves a product based on its ID
    Optional<Product> getProductById(String id);

    // Retrieves all products in repository
    List<Product> getAllProducts();

    //Updating an existing product
    void updateProduct(Product product);

    // Deletes a product based on ID
    boolean removeProduct(String id);

    // Checks if a product with a given ID exists
    boolean existsById(String id);

    //Retrieves total number of products
    long count();
}