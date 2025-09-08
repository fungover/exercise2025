package org.example.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WarehouseTest {

    @Test
    void shouldCreateEmptyWarehouse() {
        Warehouse warehouse = new Warehouse();

        assertNotNull(warehouse);
    }
}
