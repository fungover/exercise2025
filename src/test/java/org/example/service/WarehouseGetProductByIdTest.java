package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WarehouseGetProductByIdTest {
    private Warehouse warehouse;
    private Product product;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        product = new Product(
            "1",
            "Laptop",
            Category.ELECTRONICS,
            8,
            LocalDate.of(2025, 9, 1),
            LocalDate.of(2025, 9, 1));
            warehouse.addProduct(product);
    }

    @Test
    void getProductById_ValidId_ReturnsProduct() {
        assertThat(warehouse.getProductById("1")).isEqualTo(product);
    }

    @Test
    void getProductById_InvalidId_ReturnsNull() {
        assertThat(warehouse.getProductById("999")).isNull();
    }

    @Test
    void getProductById_EmptyId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> warehouse.getProductById(""))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID must not be empty");
    }

    @Test
    void getProductById_NullId_ThrowsIllegalArgumentException() {
        assertThatThrownBy(() -> warehouse.getProductById(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID must not be null");
    }
}
