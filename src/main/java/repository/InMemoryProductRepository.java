package repository;

import entities.Product;
import java.util.*;

/**
 * Implementation of the contract/interface.
 * Contains MAP and CRUD (create, read, update, delete) for storage and the storage logic.
 **/
public class InMemoryProductRepository implements ProductRepository {

    //Storage
    private final Map<String, Product> byId = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product can not be null");
        }

        String key = product.getId();
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Id can not be empty");
        }
        key = key.trim();
        if (byId.containsKey(key)) {
            throw new IllegalArgumentException("Product already exists: " + key);
        }
        byId.put(key, product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        if (id == null) return Optional.empty();
        String key = id.trim();
        if (key.isEmpty()) return Optional.empty();
        return Optional.ofNullable(byId.get(key));
    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(new ArrayList<>(byId.values()));
    }

    @Override
    public void updateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can not be null");
        }

        String key = product.getId();
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Id can not be empty");
        }

        if (!byId.containsKey(key)) {
            throw new IllegalArgumentException("No products with id: " + key);
        }
        byId.put(key, product);
    }

    @Override
    public boolean existsById(String id) {
        return id != null && byId.containsKey(id.trim());
    }
}
