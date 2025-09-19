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

class ProductServiceGetProductsByCategorySortedTest {
    private ProductService productService;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        product1 = new Product("1","Speaker", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        product2 = new Product("2","Book", Category.BOOKS,8, LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 2));
        product3 = new Product("3","Laptop", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 3), LocalDate.of(2025, 9, 3));
    }

    @Test
    void getProductsByCategorySorted_ValidCategory_ReturnsSortedProductList() {
        productService.addProduct(product1);
        productService.addProduct(product3);
        assertThat(productService.getProductsByCategorySorted(Category.ELECTRONICS)).containsExactly(product3, product1); // A-Z order
    }

    @Test
    void getProductsByCategorySorted_EmptyWarehouse_ReturnsEmptyList() {
        for (Category category : Category.values()) {
            assertThat(productService.getProductsByCategorySorted(category)).isEmpty();
        }
    }

    @Test
    void getProductsByCategorySorted_NoProductsInCategory_ReturnsEmptyList() {
        assertThat(productService.getProductsByCategorySorted(Category.FOOD)).isEmpty();
    }

    @Test
    void getProductsByCategorySorted_NullCategory_ReturnsEmptyList() {
        productService.addProduct(product1);
        assertThat(productService.getProductsByCategorySorted(null)).isEmpty();
    }

    @Test
    void getProductsByCategorySorted_ReturnedListIsUnmodifiable() {
        productService.addProduct(product1);
        productService.addProduct(product3);
        List<Product> products = productService.getProductsByCategorySorted(Category.ELECTRONICS);
        assertThat(products).containsExactly(product3, product1);
        assertThatThrownBy(() -> products.add(product2))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
