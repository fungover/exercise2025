package org.example.repository;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> warehouseProducts = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        warehouseProducts.add(product);
    }

    @Override
    public Optional<Product> getProduct(String id) {
        return warehouseProducts.stream()
            .filter(product -> product.id().toString().equals(id))
            .findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(warehouseProducts);
    }

    @Override
    public void updateProduct(Product updatedProduct) {
        for (int i = 0; i< warehouseProducts.size(); i++) {
            Product currentProduct = warehouseProducts.get(i);

            if (currentProduct.id().equals(updatedProduct.id())) {
                warehouseProducts.set(i, updatedProduct);
                return;
            }
        }

        throw new IllegalArgumentException("The product with id " + updatedProduct.id() + " does not exist");
    }
}
