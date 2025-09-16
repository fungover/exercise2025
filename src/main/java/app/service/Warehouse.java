package app.service;

import app.entities.Product;

import java.util.*;

public class Warehouse {
private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
    products.add(product);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }
}
