package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseTest {

    @Test
    @DisplayName("addProduct: adds a new product to Warehouse")
    public void addProduct() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThat(warehouse.getAllProducts()).contains(testProduct);
    }

    @Test
    @DisplayName("addProduct: throws IllegalArgumentException if product name is blank")
    public void throwExceptionIfNoProductNameProvided() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "", Category.GENERAL, 1);

        assertThatThrownBy(() -> warehouse.addProduct(testProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("updateProduct: updates an existing product by ID")
    public void updateProduct() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        Product updatedTestProduct = new Product("1", "Update Test Product", Category.GENERAL, 1);
        warehouse.updateProduct(updatedTestProduct);

        assertThat(warehouse.getAllProducts()).contains(updatedTestProduct);
        assertThat(warehouse.getAllProducts()).doesNotContain(testProduct);
        assertThat(warehouse.getAllProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("updateProduct: throws IllegalArgumentException if product ID is not found")
    public void throwExceptionIfNoProductFoundToUpdate() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        Product updatedTestProduct = new Product("2", "Update Test Product", Category.GENERAL, 1);
        assertThatThrownBy(() -> warehouse.updateProduct(updatedTestProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getAllProducts: returns all products in warehouse")
    public void getAllProducts() {
        Warehouse warehouse = new Warehouse();

        Product testProduct1 = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct1);

        Product testProduct2 = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct2);

        assertThat(warehouse.getAllProducts()).contains(testProduct1, testProduct2);
        assertThat(warehouse.getAllProducts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getAllProducts: returns empty list if warehouse is empty")
    public void getAllProductsEmptyWarehouse() {
        Warehouse warehouse = new Warehouse();
        assertThat(warehouse.getAllProducts()).isEmpty();
    }

    @Test
    @DisplayName("getProductById: returns a Product by id")
    public void getProductById() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("99", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThat(warehouse.getProductById("99")).isEqualTo(testProduct);
    }

    @Test
    @DisplayName("getProductById: throws IllegalArgumentException if product ID is not found")
    public void throwExceptionIfNoProductFound() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("99", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThatThrownBy(() -> warehouse.getProductById("100"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
