package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceUpdateTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        testProduct = new Product.Builder()
            .id("1")
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(yesterday)  // Created yesterday
            .modifiedDate(yesterday) // Initially modified yesterday
            .build();
        productService.addProduct(testProduct);
    }

    @Test
    void updateProduct_Success() {
        // When
        productService.updateProduct("1", "iPhone 15 Pro", Category.ELECTRONICS, 10);

        // Then
        Product updated = productService.getProductById("1").get();
        assertEquals("iPhone 15 Pro", updated.name());
        assertEquals(10, updated.rating());
        assertNotEquals(updated.createdDate(), updated.modifiedDate());
    }

    @Test
    void updateProduct_InvalidName_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct("1", "   ", Category.ELECTRONICS, 8);
        });

        // Verify original product is unchanged
        assertEquals("iPhone 15", productService.getProductById("1").get().name());
    }

    @Test
    void updateProduct_NonExistentId_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct("999", "Some Product", Category.ELECTRONICS, 8);
        });
    }

    @Test
    void updateProduct_NullCategory_ThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct("1", "iPhone 15 Pro", null, 8);
        });
        assertTrue(exception.getMessage().contains("Category cannot be null"));

        // Verify original product is unchanged
        assertEquals("iPhone 15", productService.getProductById("1").get().name());
    }

    @Test
    void updateProduct_InvalidRatingTooLow_ThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct("1", "iPhone 15 Pro", Category.ELECTRONICS, -1);
        });
        assertTrue(exception.getMessage().contains("Rating must be between 0 and 10"));

        // Verify original product is unchanged
        assertEquals(9, productService.getProductById("1").get().rating());
    }

    @Test
    void updateProduct_InvalidRatingTooHigh_ThrowsException() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct("1", "iPhone 15 Pro", Category.ELECTRONICS, 11);
        });
        assertTrue(exception.getMessage().contains("Rating must be between 0 and 10"));

        // Verify original product is unchanged
        assertEquals(9, productService.getProductById("1").get().rating());
    }

    @Test
    void updateProduct_ValidRatingBoundaries_Success() {
        // Test minimum valid rating
        productService.updateProduct("1", "iPhone 15 Pro", Category.ELECTRONICS, 0);
        assertEquals(0, productService.getProductById("1").get().rating());

        // Test maximum valid rating
        productService.updateProduct("1", "iPhone 15 Pro Max", Category.ELECTRONICS, 10);
        assertEquals(10, productService.getProductById("1").get().rating());
    }
}