package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final List<Product> products = new ArrayList<>();

    public List<Product> products() {
        return products;
    }

    public void add(Product product) {
        products.add(product);
    }
}
