package repository;

import entities.Product;
import java.util.List;
import java.util.Optional;

/**
 * A contract, method signature. "All repository classes that works with this
 * will have to do these things"
 **/
public interface ProductRepository {
    void addProduct(Product product);

    Optional<Product> getProductById(String id);

    List<Product> getAllProducts();

    void updateProduct(Product product);

    boolean existsById(String id);
}
