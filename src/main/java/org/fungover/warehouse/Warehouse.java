package org.fungover.warehouse;

import org.fungover.entities.Category;
import org.fungover.entities.Product;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Warehouse {
    private List<Product> products = new ArrayList<Product>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        products = products.stream().map(p -> p.identifier().equals(id) ? p.updateFields(id, name, category, rating) : p).toList();
    }

    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    public Product getProduct(String id) {
        return products.stream().filter(p -> p.identifier().equals(id)).findFirst().orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public List<Product> getProductsByCategory(Category category) {
        return products.stream().filter(p -> p.category() == category).sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        Objects.requireNonNull(date, "date must not be null");
        ZoneId zone = ZoneId.systemDefault();
        Instant threshold = date.atStartOfDay(zone).toInstant();

        return products.stream().filter(p -> p.createdDate().isAfter(threshold)).toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream().filter(p -> !p.createdDate().equals(p.lastModifiedDate())).toList();
    }

}
