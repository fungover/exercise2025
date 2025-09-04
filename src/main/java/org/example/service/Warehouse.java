package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Warehouse {
    private final List<Product> inventory = new ArrayList<>();


    public boolean addProduct(Product product) {
        if (!product.name()
                    .isEmpty()) {
            inventory.add(product);
            return true;
        }
        return false;
    }

    public void updateProduct(String id, String name, Category category,
                              int rating) {
        var idToLong = Long.parseLong(id);

        //gets our product from id
        Product existing = getProductById(idToLong);

        //we keep id and createdDate, and update the rest
        Product updated = new Product(existing.id(), name, category, rating,
          existing.createdDate(), LocalDate.now());

//        System.out.println("exisiting " + existing);
//        System.out.println("updated " + updated);

        inventory.remove(existing);
        inventory.add(updated);
    }

    public Product getProductById(long id) {

        return inventory.stream()
                        .filter(p -> p.id()
                                      .equals(id))
                        .findFirst()
                        .orElseThrow(
                          () -> new IllegalArgumentException("Product not found"));
    }


    public void getProducts() {
        inventory.stream()
                 .forEach(System.out::println);
    }


}
