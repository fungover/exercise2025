package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach void warehouseSetup() {
        warehouse = new Warehouse(); //before each test
    }

    @Test void productAddedToWareHouseInventory() {
        Product laptop = new Product("Laptop", Category.ELECTRONICS, 4);
        assertTrue(warehouse.addProduct(laptop));
    }

    @Test void productAddedWithEmptyName() {
        Warehouse warehouse = new Warehouse();
        Product laptop = new Product("", Category.ELECTRONICS, 4);
        assertFalse(warehouse.addProduct(laptop));

    }

    @Test void updateProduct_Valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        warehouse.addProduct(lamp);
        warehouse.updateProduct("1", "blueLaptop", Category.FOOD, 3);


        //get our updated product
        Product updated = warehouse.getProductById(lamp.id());

        assertEquals(1, updated.id());
        assertEquals("blueLaptop", updated.name());
        assertEquals(Category.FOOD, updated.category());
        assertEquals(3, updated.rating());
    }

    @Test void updateProduct_Invalid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        warehouse.addProduct(lamp);
        assertThrows(IllegalArgumentException.class,
          () -> warehouse.updateProduct("3", "blueLaptop", Category.FOOD, 3));
    }

}