package org.fungover.warehouse;

import org.fungover.entities.Category;
import org.fungover.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private List<Product> products = new ArrayList<Product>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Product newProduct = new Product(name, category, rating);
        products = products.stream().map(p -> {
            if (p.identifier().equals(id)) {
                return p.updateFields(id, name, category, rating);
            }
            return p;
        }).toList();
    }
}
