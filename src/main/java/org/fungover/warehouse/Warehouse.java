package org.fungover.warehouse;

import org.fungover.entities.Category;
import org.fungover.entities.Product;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

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
        ZoneId zone = ZoneId.systemDefault();
        Instant threshold = date.atStartOfDay(zone).toInstant();

        return products.stream().filter(p -> p.createdDate().isAfter(threshold)).toList();
    }

    public List<Product> getModifiedProducts() {
        return products.stream().filter(p -> !p.createdDate().equals(p.lastModifiedDate())).toList();
    }

}
