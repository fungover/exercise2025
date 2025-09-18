package exercise3.repository;

import exercise3.entities.Product;

import java.util.List;

public interface ProductRepository {

    void addProduct(Product product);

    void updateProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(String id);


}
