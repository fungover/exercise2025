package org.example;

import org.example.Repository.InMemoryProductRepository;
import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryProductRepositoryTest {
    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
    }

    @Test
    void addAndGetProduct() {
        Product product = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(999.99)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        repository.addProduct(product);

        Optional<Product> retrieved = repository.getProduct("1");
        assertTrue(retrieved.isPresent());
        assertEquals("Laptop", retrieved.get().getName());
    }

    @Test
    void updateProductReplacesExisting() {
        Product original = new Product.Builder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(7)
                .price(19.99)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        repository.addProduct(original);

        Product updated = new Product.Builder()
                .id("2")
                .name("Updated Book")
                .category(Category.BOOKS)
                .rating(8)
                .price(24.99)
                .createdDate(original.getCreatedDate())
                .modifiedDate(LocalDate.now())
                .build();

        repository.updateProduct(updated);

        Optional<Product> retrieved = repository.getProduct("2");
        assertTrue(retrieved.isPresent());
        assertEquals("Updated Book", retrieved.get().getName());
        assertEquals(8, retrieved.get().getRating());
        assertEquals(24.99, retrieved.get().getPrice());
    }
    @Test
    void getAllProductsReturnsAll() {
        Product p1 = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(999.99)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Product p2 = new Product.Builder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(7)
                .price(19.99)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        repository.addProduct(p1);
        repository.addProduct(p2);

        List<Product> products = repository.getAllProducts();
        assertEquals(2, products.size());
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
    }

    @Test
    void getNonExistentProductReturnsEmpty() {
        Optional<Product> result = repository.getProduct("999");
        assertTrue(result.isEmpty());
    }
}
