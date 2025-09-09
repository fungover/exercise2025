package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseTest {

    @Test
    void addProduct_storesProductInWarehouse() {
        // Skapa ett tomt lager och en produkt
        Warehouse warehouse = new Warehouse();
        LocalDate today = LocalDate.now();
        Product tv = new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, today, today);

        warehouse.addProduct(tv);

        // Kontrollera att produkten finns i lagret
        List<Product> allProducts = warehouse.getAllProducts();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts).contains(tv);
    }
}