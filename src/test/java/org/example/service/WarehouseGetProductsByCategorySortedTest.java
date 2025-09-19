package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WarehouseGetProductsByCategorySortedTest {
    private Warehouse warehouse;
    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        product1 = new Product("1","Speaker", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        product2 = new Product("2","Book", Category.BOOKS,8, LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 2));
        product3 = new Product("3","Laptop", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 3), LocalDate.of(2025, 9, 3));
    }

    @Test
    void getProductsByCategorySorted_ValidCategory_ReturnsSortedProductList() {
        warehouse.addProduct(product1);
        warehouse.addProduct(product3);
        assertThat(warehouse.getProductsByCategorySorted(Category.ELECTRONICS)).containsExactly(product3, product1); // A-Z order
    }

    @Test
    void getProductsByCategorySorted_EmptyWarehouse_ReturnsEmptyList() {
        for (Category category : Category.values()) {
            assertThat(warehouse.getProductsByCategorySorted(category)).isEmpty();
        }
    }

    @Test
    void getProductsByCategorySorted_NoProductsInCategory_ReturnsEmptyList() {
        assertThat(warehouse.getProductsByCategorySorted(Category.FOOD)).isEmpty();
    }

    @Test
    void getProductsByCategorySorted_NullCategory_ReturnsEmptyList() {
        warehouse.addProduct(product1);
        assertThat(warehouse.getProductsByCategorySorted(null)).isEmpty();
    }

    @Test
    void getProductsByCategorySorted_ReturnedListIsUnmodifiable() {
        warehouse.addProduct(product1);
        warehouse.addProduct(product3);
        List<Product> products = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);
        assertThat(products).containsExactly(product3, product1);
        assertThatThrownBy(() -> products.add(product2))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
