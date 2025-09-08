package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseTest {

    // Helper methods
    private Product addProductToWarehouse(Warehouse warehouse, String id, String name, Category category) {
        Product product = new Product(id, name, category, 1);
        warehouse.addProduct(product);
        return product;
    }

    @Test
    @DisplayName("addProduct: adds a new Product to Warehouse")
    public void addProduct() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThat(warehouse.getAllProducts()).contains(testProduct);
    }

    @Test
    @DisplayName("addProduct: throws IllegalArgumentException if Product name is blank")
    public void throwExceptionIfNoProductNameProvided() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "", Category.GENERAL, 1);

        assertThatThrownBy(() -> warehouse.addProduct(testProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("updateProduct: updates an existing Product by ID")
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
    @DisplayName("updateProduct: throws IllegalArgumentException if Product ID is not found")
    public void throwExceptionIfNoProductFoundToUpdate() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        Product updatedTestProduct = new Product("2", "Update Test Product", Category.GENERAL, 1);
        assertThatThrownBy(() -> warehouse.updateProduct(updatedTestProduct))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getAllProducts: returns all Products in Warehouse")
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
    @DisplayName("getAllProducts: returns empty list if Warehouse is empty")
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
    @DisplayName("getProductById: throws IllegalArgumentException if Product ID is not found")
    public void throwExceptionIfNoProductFound() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("99", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThatThrownBy(() -> warehouse.getProductById("100"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getProductsByCategorySorted: returns Products in a category, sorted A-Z by name")
    public void getProductsByCategorySorted() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Television", Category.GENERAL);
        Product testProduct4 = addProductToWarehouse(warehouse, "4", "Sandwich", Category.GENERAL);
        Product testProduct5 = addProductToWarehouse(warehouse, "5", "Candy", Category.GENERAL);
        Product testProduct6 = addProductToWarehouse(warehouse, "6", "VR Glasses", Category.GENERAL);
        Product testProduct7 = addProductToWarehouse(warehouse, "7", "Android TV", Category.ELECTRONICS);

        assertThat(warehouse.getProductsByCategorySorted(Category.GENERAL))
                .extracting(Product::getName)
                .containsExactly("Candy", "Computer", "Laptop", "Sandwich", "Television", "VR Glasses");
    }

    @Test
    @DisplayName("getProductsByCategorySorted: returns empty list if Category is empty")
    public void getProductsByCategorySortedEmptyCategory() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Television", Category.GENERAL);

        assertThat(warehouse.getProductsByCategorySorted(Category.ELECTRONICS)).isEmpty();
    }

}
