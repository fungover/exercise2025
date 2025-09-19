package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryProductRepository implements ProductRepository {
    //moved this from warehouse
    private final List<Product> inventory = new ArrayList<>();


    @Override public boolean addProduct(Product product) {
        if (product == null) return false;
        String name = product.name();
        if (name != null && !name.isBlank()) {
            inventory.add(product);
            return true;
        }
        return false;
    }

    @Override public Optional<Product> getProductById(String id) {
        Long idToLong = Long.parseLong(id);
        return inventory.stream()
                        .filter(p -> p.id()
                                      .equals(idToLong))
                        .findFirst();

    }

    @Override public List<Product> getAllProducts() {
        return List.copyOf(inventory); //returns a copy so no one can .clean()
    }

    @Override public void updateProduct(Product product) {
        // find existing product by id
        Optional<Product> existing = inventory.stream()
                                              .filter(p -> p.id()
                                                            .equals(product.id()))
                                              .findFirst();

        existing.ifPresent(found -> {
            inventory.remove(found);
            inventory.add(product);//adds new version
        });
    }

    @Override public Product removeProductById(String id) {
        Optional<Product> product = getProductById(id);
        if (product.isPresent()) {
            inventory.remove(product.get());
            return product.get();
        }
        throw new IllegalArgumentException("Product with id " + id + " not found");
    }

    @Override public List<Product> findByCategory(Category category) {
        return inventory.stream()
                        .filter(p -> p.category() == category)
                        .collect(Collectors.toUnmodifiableList());
    }

    @Override public List<Product> findCreatedAfter(LocalDate date) {
        return inventory.stream()
                        .filter(p -> p.createdDate()
                                      .isAfter(date))
                        .collect(Collectors.toUnmodifiableList());
    }

    @Override public List<Product> findModified() {
        return inventory.stream()
                        .filter(p -> !p.createdDate()
                                       .isEqual(p.modifiedDate()))
                        .collect(Collectors.toUnmodifiableList());
    }

    //implement other methods?

}
