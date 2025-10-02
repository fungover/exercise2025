package app.repository;

import app.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void addProduct(Product product);

    Optional<Product> getProductByID(int ID);

    List<Product> getAllProducts();

    void updateProduct(Product product);
}
