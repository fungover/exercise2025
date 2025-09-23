package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceGetProductCreatedAfterTest {
    private ProductService productService;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        product1 = new Product("1","Laptop", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        product2 = new Product("2","Speaker", Category.BOOKS,8, LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 2));
    }

    @Test
    void getProductsCreatedAfter_ValidDate_ReturnsProductAfterDate() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        assertThat(productService.getProductsCreatedAfter(LocalDate.of(2025, 9, 1)))
                .containsExactly(product2);
    }

    @Test
    void getProductsCreatedAfter_EmptyWarehouse_ReturnsEmptyList() {
        assertThat(productService.getProductsCreatedAfter(LocalDate.of(2025, 9, 3))).isEmpty();
    }

    @Test
    void getProductsCreatedAfter_NullDate_ReturnsEmptyList() {
        productService.addProduct(product1);
        assertThat(productService.getProductsCreatedAfter(null)).isEmpty();
    }

    @Test
    void getProductsCreatedAfter_NoProductsCreatedAfterDate_ReturnsEmptyList() {
        productService.addProduct(product1);
        assertThat(productService.getProductsCreatedAfter(LocalDate.of(2025, 9, 2))).isEmpty();
    }

}
