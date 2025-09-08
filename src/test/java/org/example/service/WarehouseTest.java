package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseTest {

    // Helper methods
    private Product addProductToWarehouse(Warehouse warehouse, String id, String name, Category category) {
        Product product = new Product(id, name, category, 1);
        warehouse.addProduct(product);
        return product;
    }

    private Product createProductWithMockedDate(String id, String name, Category category, int rating, int year, int month, int day) {
        LocalDate mockedDate = LocalDate.of(year, month, day);
        try (MockedStatic<LocalDate> mockedStatic = Mockito.mockStatic(LocalDate.class, Mockito.CALLS_REAL_METHODS)) {
            mockedStatic.when(LocalDate::now).thenReturn(mockedDate);
            return new Product(id, name, category, rating);
        }
    }

    // Tests
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

        assertThat(warehouse.getProductById("99")).contains(testProduct);
    }

    @Test
    @DisplayName("getProductById: returns empty Optional if Product ID is not found")
    public void returnsEmptyOptionalIfNoProductFound() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("99", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThat(warehouse.getProductById("100")).isEmpty();
    }

    @Test
    @DisplayName("getProductById: throws exception if invalid input")
    public void throwsExceptionIfNoProductIdProvided() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("99", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThatThrownBy(() -> warehouse.getProductById(""))
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

    @Test
    @DisplayName("getProductsCreatedAfter(LocalDate date): returns Products created after a given date")
    public void getProductsCreatedAfter() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = createProductWithMockedDate("1", "Laptop", Category.GENERAL, 1, 2025, 1, 5);
        Product testProduct2 = createProductWithMockedDate("2", "Notebook", Category.GENERAL, 1, 1997, 1, 27);
        Product testProduct3 = createProductWithMockedDate("2", "Desk Chair", Category.GENERAL, 1, 1997, 3, 31);
        Product testProduct4 = createProductWithMockedDate("3", "Water Bottle", Category.GENERAL, 1, 1782, 8, 24);
        Product testProduct5 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 1, 2002, 1, 1);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);
        warehouse.addProduct(testProduct3);
        warehouse.addProduct(testProduct4);
        warehouse.addProduct(testProduct5);

        LocalDate testDate = LocalDate.of(1997, 2, 1);
        assertThat(warehouse.getProductsCreatedAfter(testDate)).extracting(Product::getName).containsExactly("Laptop", "Desk Chair", "Pen Set");
    }

    @Test
    @DisplayName("getProductsCreatedAfter(LocalDate date): throws an exception if invalid input")
    public void getProductsCreatedAfterEmptyList() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = createProductWithMockedDate("1", "Laptop", Category.GENERAL, 1, 2025, 1, 5);
        Product testProduct2 = createProductWithMockedDate("2", "Notebook", Category.GENERAL, 1, 1997, 1, 27);
        Product testProduct3 = createProductWithMockedDate("2", "Desk Chair", Category.GENERAL, 1, 1997, 3, 31);
        Product testProduct4 = createProductWithMockedDate("3", "Water Bottle", Category.GENERAL, 1, 1782, 8, 24);
        Product testProduct5 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 1, 2002, 1, 1);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);
        warehouse.addProduct(testProduct3);
        warehouse.addProduct(testProduct4);
        warehouse.addProduct(testProduct5);

        assertThatThrownBy(() -> warehouse.getProductsCreatedAfter(null))
                .isInstanceOf(IllegalArgumentException.class);
    }



}
