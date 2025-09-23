package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WarehouseGetAllProductsTest {
    private Warehouse warehouse;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        product1 = new Product("1", "Laptop", Category.ELECTRONICS, 8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        product2 = new Product("2", "Book", Category.BOOKS, 7, LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 2));
    }

    @Test
    void getAllProducts_EmptyWarehouse_ReturnsEmptyList() {
        assertThat(warehouse.getAllProducts()).isEmpty();
    }

    @Test
    void getAllProducts_NonEmptyWarehouse_ReturnsAllProducts() {
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        assertThat(warehouse.getAllProducts()).containsExactly(product1, product2);
    }

    @Test
    void getAllProducts_ReturnedListIsUnmodifiable() {
        warehouse.addProduct(product1);
        List<Product> products = warehouse.getAllProducts();
        assertThatThrownBy(() -> products.add(product2))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}