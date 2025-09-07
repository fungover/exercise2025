package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseTest {

    @Test
    @DisplayName("Add a new product to Warehouse")
    public void addProduct() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product(1, "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThat(warehouse.getAllProducts()).contains(testProduct);
    }

    @Test
    @DisplayName("Throw exception if no Product name is provided")
    public void throwExceptionIfNoProductNameProvided() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product(1, "", Category.GENERAL, 1);

        assertThatThrownBy(() -> warehouse.addProduct(testProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Update an existing product in Warehouse by searching with id")
    public void updateProduct() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product(1, "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        Product updatedTestProduct = new Product(1, "Update Test Product", Category.GENERAL, 1);
        warehouse.updateProduct(updatedTestProduct);

        assertThat(warehouse.getAllProducts()).contains(updatedTestProduct);
        assertThat(warehouse.getAllProducts()).doesNotContain(testProduct);
        assertThat(warehouse.getAllProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Throw exception if no Product is found to update")
    public void throwExceptionIfNoProductFoundToUpdate() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product(1, "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        Product updatedTestProduct = new Product(2, "Update Test Product", Category.GENERAL, 1);
        assertThatThrownBy(() -> warehouse.updateProduct(updatedTestProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
