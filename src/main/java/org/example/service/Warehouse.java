package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name().isEmpty()) {
            System.out.println("Product name must not be empty");
        } else {
            products.add(product);
        }
    }

    public List<Product> updateProduct(String id, String name,
                                       Category category, int rating) {
        Product oldProduct = getProductById(id);
        products.add(new Product(id, name, category, rating,
                oldProduct.createdDate(), LocalDateTime.now()));
        products.remove(oldProduct);

        return products;
    }


    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String idNumber) {
        return products.stream()
                .filter(product -> product.id().equals(idNumber))
                .toList()
                .getFirst();
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDateTime date) {
        return products.stream()
                .filter(product -> product.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream()
                .filter(product -> product.modifiedDate() != null)
                .toList();
    }
}
