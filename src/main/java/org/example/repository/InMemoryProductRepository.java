package org.example.repository;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        if (product.name().isEmpty()) {
            System.out.println("Product name must not be empty");
        } else {
            products.add(product);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Optional<Product> getProductById(String idNumber) {
        Optional<Product> productOptional = products.stream()
                .filter(product -> product.id().equals(idNumber))
                .findFirst();

        if (productOptional.isEmpty())
            throw new NoSuchElementException("Product with id " + idNumber +
                    " not found");

        return productOptional;
    }

    @Override
    public void updateProduct(Product product) {
        Optional<Product> oldProduct = products.stream()
                .filter(p -> p.id().equals(product.id()))
                .findFirst();

        if (oldProduct.isEmpty())
            throw new NoSuchElementException("Product with id " + product.id() +
                    " not found");

        oldProduct.ifPresent(products::remove);
        product.modifyDateToNow();
        products.add(product);
    }
}
