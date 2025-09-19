package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceGetModifiedProductsTest {
    private ProductService productService;
    private Product unmodifiedProduct;
    private Product modifiedProduct;


    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        unmodifiedProduct = new Product("1","Speaker", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        modifiedProduct = new Product("2","Book", Category.BOOKS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 2));
    }

    @Test
    void getModifiedProducts_HasModifiedProducts_ReturnsModifiedProducts() {
        productService.addProduct(unmodifiedProduct);
        productService.addProduct(modifiedProduct);
        assertThat(productService.getModifiedProducts()).containsExactly(modifiedProduct);
    }

    @Test
    void getModifiedProducts_DoesNotHaveModifiedProducts_ReturnsEmptyList() {
        productService.addProduct(unmodifiedProduct);
        assertThat(productService.getModifiedProducts()).isEmpty();
    }

    @Test
    void getModifiedProducts_EmptyWarehouse_ReturnsEmptyList() {
        assertThat(productService.getModifiedProducts()).isEmpty();
    }

}
