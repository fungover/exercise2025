package org.example.service;

import org.example.entities.Product;

import java.util.ArrayList;

public class Warehouse {
    private final ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }
}
