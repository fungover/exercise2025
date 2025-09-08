package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    @DisplayName( "Add a product to the warehouse")
    void addProductToWarehouse(){
        ZonedDateTime now = ZonedDateTime.now();
        Product clicker = ( new Product(
                "clicker-001",
                "Clicker",
                Category.TRAINING,
                5,
                now,
                now
        ));

        warehouse.add(clicker);

        List<Product> products = warehouse.products();

        assertThat(products)
                .hasSize(1)
                .containsExactly(clicker);
    }
}