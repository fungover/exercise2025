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

class ProductServiceGetProductByIdTest {
    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        product = new Product(
            "1",
            "Laptop",
            Category.ELECTRONICS,
            8,
            LocalDate.of(2025, 9, 1),
            LocalDate.of(2025, 9, 1));
            productService.addProduct(product);
    }

    @Test
    void getProductById_ValidId_ReturnsProduct() {
        assertThat(productService.getProductById("1")).isEqualTo(product);
    }

    @Test
    void getProductById_InvalidId_ReturnsNull() {
        assertThat(productService.getProductById("999")).isNull();
    }

    @Test
    void getProductById_EmptyId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.getProductById(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID must not be empty");
    }

    @Test
    void getProductById_NullId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.getProductById(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID must not be null");
    }
}
