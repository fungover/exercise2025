package org.example.service;


import org.example.entities.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Warehouse {

    private final Map<String, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.id(), product);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
