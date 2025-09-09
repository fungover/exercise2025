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

    @Test
    void getProductsCreatedAfter_returnsOnlyNewer() {
        Warehouse warehouse = new Warehouse();
        var d1 = LocalDate.of(2025, 1, 1);
        var d2 = LocalDate.of(2025, 1, 2);
        var d3 = LocalDate.of(2025, 1, 3);
        var d4 = LocalDate.of(2025, 1, 4);
        var d5 = LocalDate.of(2025, 1, 5);
        var d6 = LocalDate.of(2025, 1, 6);

        warehouse.addProduct(new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, d1, d1));
        warehouse.addProduct(new Product("p-002", "Budget Laptop", Category.COMPUTER, 7, d2, d2));
        warehouse.addProduct(new Product("p-003", "Pro Laptop", Category.COMPUTER, 8, d3, d3));
        warehouse.addProduct(new Product("p-004", "Another Laptop", Category.COMPUTER, 7, d4, d4));
        warehouse.addProduct(new Product("p-005", "Iphone", Category.PHONE, 7, d5, d5));
        warehouse.addProduct(new Product("p-006", "Refrigerator", Category.APPLIANCES, 7, d6, d6));

        var afterJan3 = warehouse.getProductsCreatedAfter(LocalDate.of(2025, 1, 3));
        assertThat(afterJan3)
                .extracting(Product::id)
                .containsExactly("p-004", "p-005", "p-006");
    }

    @Test
    void getModifiedProducts_returnsWhereCreatedNotEqualModified() {
        Warehouse warehouse = new Warehouse();
        var createdDate = LocalDate.of(2025, 1, 1);

        // CreatedDate är samma som ModifiedDate
        warehouse.addProduct(new Product("p-001", "Samsung 55\" 4K", Category.TV, 8, createdDate, createdDate));

        // CreatedDate är inte samma som ModifiedDate
        warehouse.addProduct(new Product("p-002", "Budget Laptop", Category.COMPUTER, 7, createdDate, createdDate.plusDays(1)));

        var modifiedDate = warehouse.getModifiedProducts();

        assertThat(modifiedDate)
                .extracting(Product::id)
                .containsExactly("p-002");
    }
}