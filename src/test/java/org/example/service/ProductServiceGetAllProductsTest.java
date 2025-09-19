package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceGetAllProductsTest {
    private ProductService productService;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        product1 = new Product("1", "Laptop", Category.ELECTRONICS, 8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        product2 = new Product("2", "Book", Category.BOOKS, 7, LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 2));
    }

    @Test
    void getAllProducts_EmptyWarehouse_ReturnsEmptyList() {
        assertThat(productService.getAllProducts()).isEmpty();
    }

    @Test
    void getAllProducts_NonEmptyWarehouse_ReturnsAllProducts() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        assertThat(productService.getAllProducts()).containsExactly(product1, product2);
    }

    @Test
    void getAllProducts_ReturnedListIsUnmodifiable() {
        productService.addProduct(product1);
        List<Product> products = productService.getAllProducts();
        assertThatThrownBy(() -> products.add(product2))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}