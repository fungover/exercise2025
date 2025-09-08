package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("Product cannot be null");
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }
}
