package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WarehouseGetProductCreatedAfterTest {
    private Warehouse warehouse;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        product1 = new Product("1","Laptop", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        product2 = new Product("2","Speaker", Category.BOOKS,8, LocalDate.of(2025, 9, 2), LocalDate.of(2025, 9, 2));
    }

    @Test
    void getProductsCreatedAfter_ValidDate_ReturnsProductAfterDate() {
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        assertThat(warehouse.getProductsCreatedAfter(LocalDate.of(2025, 9, 1)))
                .containsExactly(product2);
    }

    @Test
    void getProductsCreatedAfter_EmptyWarehouse_ReturnsEmptyList() {
        assertThat(warehouse.getProductsCreatedAfter(LocalDate.of(2025, 9, 3))).isEmpty();
    }

    @Test
    void getProductsCreatedAfter_NullDate_ReturnsEmptyList() {
        warehouse.addProduct(product1);
        assertThat(warehouse.getProductsCreatedAfter(null)).isEmpty();
    }

    @Test
    void getProductsCreatedAfter_NoProductsCreatedAfterDate_ReturnsEmptyList() {
        warehouse.addProduct(product1);
        assertThat(warehouse.getProductsCreatedAfter(LocalDate.of(2025, 9, 2))).isEmpty();
    }

}
