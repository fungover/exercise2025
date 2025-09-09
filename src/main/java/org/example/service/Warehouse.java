package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }
    // Returnerar en kopia av lagret för att skydda intern lista
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
}
