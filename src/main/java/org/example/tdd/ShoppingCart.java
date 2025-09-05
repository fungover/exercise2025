package org.example.tdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingCart {
    private final List<Product> products = new ArrayList<>();

    public void add(Product product) {
        products.add(product);
    }

    public List<Product> products() {
        return Collections.unmodifiableList(products);
    }

    public float totalPrice() {
       return (float) products.stream()
                .mapToDouble(Product::price)
                .sum();
    }

    public void removeProduct(Product product) {
        if( !products.remove(product))
            throw new IllegalArgumentException("No such product");
    }
}
