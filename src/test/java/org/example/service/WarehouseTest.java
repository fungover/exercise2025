package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

public class WarehouseTest {
    private final Warehouse warehouse = new Warehouse();

    @Test
    public void testAddProduct() {
        warehouse.addProduct(new Product("23", "Pants",
                Category.CLOTHES, 5, LocalDateTime.now(), null));

        List<Product> products = warehouse.getProducts();
        assertEquals(1, products.size());
    }
    @Test
    public void testAddProductFail() {
        // A product's name attribute must not be empty.
        warehouse.addProduct(new Product("34", "", Category.PROVISIONS,
                0, LocalDateTime.now(), null));

        assertEquals(0, warehouse.getProducts().size());
    }
}
