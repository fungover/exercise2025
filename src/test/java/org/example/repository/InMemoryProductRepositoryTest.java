package org.example.repository;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @Test
    void shouldAddProduct() {
        Product product = Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(6)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build();

        productRepository.addProduct(product);
        assertEquals(1, productRepository.getAllProducts().size());
        assertTrue(productRepository.getAllProducts().contains(product));
    }

    @Test
    void shouldThrowExceptionWhenAddingNullProduct() {
        assertThrows(IllegalArgumentException.class, () ->
                productRepository.addProduct(null));
    }

    @Test
    void shouldGetProductById() {
        Product product = Product.builder()
                .id("123")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build();

        productRepository.addProduct(product);
        Optional<Product> found = productRepository.getProductById("123");

        assertTrue(found.isPresent());
        assertEquals(product, found.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFound() {
        Optional<Product> found = productRepository.getProductById("999");
        assertFalse(found.isPresent());
    }

    @Test
    void shouldThrowExceptionForEmptyId() {
        assertThrows(IllegalArgumentException.class, () ->
                productRepository.getProductById(""));
    }

    @Test
    void shouldUpdateProduct() {
        Product original = Product.builder()
                .id("1")
                .name("Original")
                .category(Category.ELECTRONICS)
                .rating(5)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build();

        productRepository.addProduct(original);

        Product updated = Product.builder()
                .id("1")
                .name("Updated")
                .category(Category.SPORTS)
                .rating(9)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(79.99))
                .build();

        productRepository.updateProduct(updated);

        Optional<Product> found = productRepository.getProductById("1");
        assertTrue(found.isPresent());
        assertEquals("Updated", found.get().name());
        assertEquals(Category.SPORTS, found.get().category());
    }

    @Test
    void shouldRemoveProduct() {
        Product product = Product.builder()
                .id("1")
                .name("Test")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build();

        productRepository.addProduct(product);
        productRepository.removeProduct(product);

        assertTrue(productRepository.getAllProducts().isEmpty());
        assertFalse(productRepository.getProductById("1").isPresent());
    }
}
