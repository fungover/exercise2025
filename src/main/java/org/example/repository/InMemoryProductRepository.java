package org.example.repository;

import org.example.entities.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new HashMap<>();

    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product.id())){
            throw new IllegalArgumentException("Product already exists");
        }
        products.put(product.id(), product);
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    @Override
    public void updateProduct(Product product) {
        if (!products.containsKey(product.id())){
            throw new IllegalArgumentException("Product was not found. Product id: " + product.id());
        }
        products.put(product.id(), product);
    }
}
