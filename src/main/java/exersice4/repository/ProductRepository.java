package exersice4.repository;

import exersice4.enteties.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void addProduct(Product product);
    Optional<Product> getProductById(String id);
    List<Product> getAllProducts();
    void updateProduct(Product product);

}
