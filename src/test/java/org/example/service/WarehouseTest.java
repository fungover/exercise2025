package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void addProduct_nullProduct_throwsException() {
        Warehouse warehouse = new Warehouse();

        assertThatThrownBy(() -> warehouse.addProduct(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("product");
    }

    @Test
    void addProduct_duplicatedId_throwsException() {
        Warehouse warehouse = new Warehouse();
        LocalDate today = LocalDate.now();
        Product first = new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, today, today);
        Product duplicateId = new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, today, today);

        warehouse.addProduct(first);

        assertThatThrownBy(() -> warehouse.addProduct(duplicateId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");

    }

    @Test
    void getProductById_returnsMatchingProduct() {
        Warehouse warehouse = new Warehouse();
        LocalDate today = LocalDate.now();
        Product tv = new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, today, today);
        Product laptop = new Product("p-002", "Budget Laptop", Category.COMPUTER, 8, today, today);

        warehouse.addProduct(tv);
        warehouse.addProduct(laptop);

        Product found = warehouse.getProductById("p-001");
        assertThat(found).isEqualTo(tv);
    }

    @Test
    void getProductById_unknownId_throwsException() {
        Warehouse warehouse = new Warehouse();
        LocalDate today = LocalDate.now();
        warehouse.addProduct(new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, today, today));

        assertThatThrownBy(() -> warehouse.getProductById("p-000"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");
    }

    @Test
    void getProductsByCategorySorted_returnsAtoZByName() {
        Warehouse warehouse = new Warehouse();
        LocalDate today = LocalDate.now();
        warehouse.addProduct(new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, today, today));
        warehouse.addProduct(new Product("p-002", "Budget Laptop", Category.COMPUTER, 7, today, today));
        warehouse.addProduct(new Product("p-003", "Pro Laptop", Category.COMPUTER, 8, today, today));
        warehouse.addProduct(new Product("p-004", "Another Laptop", Category.COMPUTER, 7, today, today));

        var computers = warehouse.getProductsByCategorySorted(Category.COMPUTER);

        assertThat(computers)
                .extracting(Product::name)
                .containsExactly("Another Laptop", "Budget Laptop", "Pro Laptop");
    }
}