package org.example;

import org.example.entities.Product;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private final List<Product> warehouseProducts = new ArrayList<>();

    public void addProduct(Product product) {
        warehouseProducts.add(product);
    }
}
