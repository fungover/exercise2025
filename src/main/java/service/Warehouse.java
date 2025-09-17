package service;


import entities.Category;
import entities.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if(product.getName().isEmpty()){
            throw new IllegalArgumentException("Name can not be empty");
        }
        products.add(product);

    }

    public List<Product> getProducts() {
        return products;
    }


    public void updateProduct(int number, String name, Category category, int id) {
        products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(
                        "Product with id " + id + " not found"));
    }
}