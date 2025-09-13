package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WarehouseGetModifiedProductsTest {
    private Warehouse warehouse;
    private Product unmodifiedProduct;
    private Product modifiedProduct;


    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        unmodifiedProduct = new Product("1","Speaker", Category.ELECTRONICS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 1));
        modifiedProduct = new Product("2","Book", Category.BOOKS,8, LocalDate.of(2025, 9, 1), LocalDate.of(2025, 9, 2));
    }

    @Test
    void getModifiedProducts_HasModifiedProducts_ReturnsModifiedProducts() {
        warehouse.addProduct(unmodifiedProduct);
        warehouse.addProduct(modifiedProduct);
        assertThat(modifiedProduct.createdDate()).isNotEqualTo(modifiedProduct.modifiedDate());
        assertThat(unmodifiedProduct.createdDate()).isEqualTo(unmodifiedProduct.modifiedDate());
    }

    @Test
    void getModifiedProducts_DoesNotHaveModifiedProducts_ReturnsEmptyList() {
        warehouse.addProduct(unmodifiedProduct);
        warehouse.addProduct(modifiedProduct);
        assertThat(unmodifiedProduct.createdDate()).isEqualTo(unmodifiedProduct.modifiedDate());
        assertThat(warehouse.getModifiedProducts(null)).isEmpty();
    }

    @Test
    void getModifiedProducts_EmptyWarehouse_ReturnsEmptyList() {
        assertThat(warehouse.getModifiedProducts(null)).isEmpty();
    }

}
