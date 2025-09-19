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

class ProductServiceUpdateProductTest {
    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        product = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.of(2025, 9, 1))
                .build();
        productService.addProduct(product);
    }

    @Test
    void updateProduct_ValidInput_SuccessfullyUpdated() {
        boolean updated = productService.updateProduct("1", "New Laptop", Category.BOOKS, 9);
        assertThat(updated).isTrue();
        assertThat(productService.getAllProducts())
                .hasSize(1)
                .first()
                .satisfies(p -> {
                    assertThat(p.name()).isEqualTo("New Laptop");
                    assertThat(p.category()).isEqualTo(Category.BOOKS);
                    assertThat(p.rating()).isEqualTo(9);
                    assertThat(p.createdDate()).isEqualTo(LocalDate.of(2025, 9, 1));
                    assertThat(p.modifiedDate()).isEqualTo(LocalDate.now());
                });
    }

    @Test
    void updateProduct_NonExistentId_ReturnsFalse() {
        boolean updated = productService.updateProduct("999", "Tablet", Category.ELECTRONICS, 7);
        assertThat(updated).isFalse();
        assertThat(productService.getAllProducts()).containsExactly(product);
    }

    @Test
    void updateProduct_NullId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.updateProduct(null, "Tablet", Category.ELECTRONICS, 7))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("ID must not be null");
    }

    @Test
    void updateProduct_EmptyId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.updateProduct("", "Tablet", Category.ELECTRONICS, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("ID must not be empty");
    }

    @Test
    void updateProduct_NullName_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.updateProduct("1", null, Category.ELECTRONICS, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be null or empty");
    }

    @Test
    void updateProduct_EmptyName_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.updateProduct("1", "", Category.ELECTRONICS, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Name must not be null or empty");
    }

    @Test
    void updateProduct_NullCategory_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.updateProduct("1", "Tablet", null, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category must not be null");
    }

    @Test
    void updateProduct_InvalidRating_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> productService.updateProduct("1", "Tablet", Category.ELECTRONICS, 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rating must be between 0 and 10");
    }
}