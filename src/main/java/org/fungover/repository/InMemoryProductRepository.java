package org.fungover.repository;

import org.fungover.entities.Category;
import org.fungover.entities.Product;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private List<Product> products = new ArrayList<Product>();

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public void updateProduct(Product product) {
        products = products.stream().map(p -> p.identifier().equals(product.identifier()) ? p.updateFields(product.identifier(), product.name(), product.category(), product.rating()) : p).toList();
    }

    @Override
    public Optional<Product> getProductById(String id) {
        return products.stream().filter(p -> p.identifier().equals(id)).findFirst();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.copyOf(products);
    }

    @Override
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        Objects.requireNonNull(date, "date must not be null");
        ZoneId zone = ZoneId.systemDefault();
        Instant threshold = date.atStartOfDay(zone).toInstant();

        return products.stream().filter(p -> p.createdDate().isAfter(threshold)).toList();
    }

    @Override
    public List<Product> getProductsByCategory(Category category) {
        return products.stream().filter(p -> p.category() == category).sorted(Comparator.comparing(Product::name, String.CASE_INSENSITIVE_ORDER)).toList();
    }

    @Override
    public List<Product> getModifiedProducts() {
        return products.stream().filter(p -> !p.createdDate().equals(p.lastModifiedDate())).toList();
    }
}
