package app.repository;

import app.entities.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<Integer, Product> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        products.put(product.ID(),product);
    }

    @Override
    public Optional<Product> getProductByID(int ID) {
        return Optional.ofNullable(products.get(ID));
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products.values());
    }

    @Override
    public void updateProduct(Product product) {
     if (!products.containsKey(product.ID())) {
         throw new IllegalArgumentException("Product does not exist");
     }
        products.put(product.ID(),product);
    }

}
