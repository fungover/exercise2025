package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


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

        //gets our product from id
        Product existing = getProductById(id);

        //we keep id and createdDate, and update the rest
        Product updated = new Product(existing.id(), name, category, rating,
          existing.createdDate(), LocalDate.now());

        inventory.remove(existing);
        inventory.add(updated);
    }

    public Product getProductById(String id) {
        Long idToLong = Long.parseLong(id);
        return inventory.stream()
                        .filter(p -> p.id()
                                      .equals(idToLong))
                        .findFirst()
                        .orElseThrow(
                          () -> new IllegalArgumentException("Product not found"));
    }

    public Product removeProductById(String id) {

        Product removed = getProductById(id);
        inventory.remove(removed);
        return removed;
    }


    public List<Product> getAllProducts() {
        return List.copyOf(inventory); //returns a copy so no one can .clean()

    }

    @Override public String toString() {
        return "Warehouse Inventory:\n" + inventory.stream()
                                                   .map(Product::toString)
                                                   .collect(
                                                     Collectors.joining("\n"));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return inventory.stream()
                        .filter(p -> p.category() == category)
                        .sorted(Comparator.comparing(Product::name))
                        .collect(Collectors.toUnmodifiableList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return inventory.stream()
                        .filter(p -> p.createdDate()
                                      .isAfter(date))
                        .collect(Collectors.toUnmodifiableList());
    }

    public List<Product> getModifiedProducts() {
        return inventory.stream()
                        .filter(p -> p.createdDate() != p.modifiedDate())
                        .collect(Collectors.toUnmodifiableList());
    }
}
