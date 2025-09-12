package entities;

import java.util.*;

public class Warehouse {

    private final Map<String, Product> productById = new HashMap<>();

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can not be null");
        }

        if (productById.containsKey(product.id())) {
            throw new IllegalArgumentException("Product already exists: " + product.id());
        }
        productById.put(product.id(), product);
        return product;
    }

    public Product getProductById(String id) {
        Objects.requireNonNull(id, "Id can not be null");
        Product p = productById.get(id);
        if (p == null) {
            throw new IllegalArgumentException("No product with id: " + id);
        }
        return p;
    }

    public List<Product> getAllProducts() {
        return List.copyOf(productById.values());
    }
}

