package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.entities.Category.ELECTRONICS;

public class WarehouseTest {

    // Helper methods
    private Product addProductToWarehouse(Warehouse warehouse, String id, String name, Category category, int rating) {
        Product product = new Product(id, name, category, rating);
        warehouse.addProduct(product);
        return product;
    }

    private Product createProductWithMockedDate(String id, String name, Category category, int rating, int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime mockedDate = LocalDateTime.of(year, month, day, hour, minute, second, 0);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(mockedDate);
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


        warehouse.updateProduct("1", "Updated Test Product", ELECTRONICS, 5);

        Product updatedProduct = warehouse.getProductById("1").get();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Test Product");
        assertThat(updatedProduct.getCategory()).isEqualTo(ELECTRONICS);
        assertThat(updatedProduct.getRating()).isEqualTo(5);
        assertThat(updatedProduct.getId()).isEqualTo("1");
        assertThat(warehouse.getAllProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("updateProduct: updates an existing Product without changing createdDate")
    public void updateProductWithoutChangingCreatedDate() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = createProductWithMockedDate("1", "Test Product", Category.GENERAL, 1, 2024, 1, 5, 18, 0, 0);
        warehouse.addProduct(testProduct);

        assertThat(warehouse.getProductById("1").get().getCreatedDate()).isEqualTo(testProduct.getCreatedDate());
    }

    @Test
    @DisplayName("updateProduct: throws IllegalArgumentException if Product ID is not found")
    public void throwExceptionIfNoProductFoundToUpdate() {
        Warehouse warehouse = new Warehouse();
        Product testProduct = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct);

        assertThatThrownBy(() -> warehouse.updateProduct("2", "Updated Product", Category.GENERAL, 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("testBlankIncludeNull")
    @DisplayName("updateProduct: throws IllegalArgumentException for invalid inputs")
    void throwExceptionIfInvalidInputToUpdate(String id, String name, Category category, int rating) {
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(new Product("1", "Laptop", Category.GENERAL, 1));

        assertThatThrownBy(() -> warehouse.updateProduct(id, name, category, rating))
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testBlankIncludeNull() {
        return Stream.of(
                //Id
                Arguments.of(null, "Test Product", Category.GENERAL, 5),
                Arguments.of("", "Test Product", Category.GENERAL, 5),
                Arguments.of(" ", "Test Product", Category.GENERAL, 5),

                //Name
                Arguments.of("1", null, Category.GENERAL, 5),
                Arguments.of("1", "", Category.GENERAL, 5),
                Arguments.of("1", " ", Category.GENERAL, 5),

                //Category
                Arguments.of("1", "Test Product", null, 5),

                //Rating
                Arguments.of("1", "Test Product", Category.GENERAL, 11),
                Arguments.of("1", "Test Product", Category.GENERAL, 0)
        );
    }

    @Test
    @DisplayName("getAllProducts: returns all Products in Warehouse")
    public void getAllProducts() {
        Warehouse warehouse = new Warehouse();

        Product testProduct1 = new Product("1", "Test Product", Category.GENERAL, 1);
        warehouse.addProduct(testProduct1);

        Product testProduct2 = new Product("2", "Test Product2", Category.GENERAL, 1);
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
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL, 1);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Television", Category.GENERAL, 1);
        Product testProduct4 = addProductToWarehouse(warehouse, "4", "Sandwich", Category.GENERAL, 1);
        Product testProduct5 = addProductToWarehouse(warehouse, "5", "Candy", Category.GENERAL, 1);
        Product testProduct6 = addProductToWarehouse(warehouse, "6", "VR Glasses", Category.GENERAL, 1);
        Product testProduct7 = addProductToWarehouse(warehouse, "7", "Android TV", ELECTRONICS, 1);

        assertThat(warehouse.getProductsByCategorySorted(Category.GENERAL))
                .extracting(Product::getName)
                .containsExactly("Candy", "Computer", "Laptop", "Sandwich", "Television", "VR Glasses");
    }

    @Test
    @DisplayName("getProductsByCategorySorted: returns empty list if Category is empty")
    public void getProductsByCategorySortedEmptyCategory() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL, 1);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Television", Category.GENERAL, 1);

        assertThat(warehouse.getProductsByCategorySorted(ELECTRONICS)).isEmpty();
    }

    @Test
    @DisplayName("getProductsByCategorySorted: throws exception if invalid input")
    public void throwsExceptionIfNoCategoryProvided() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL, 1);

        assertThatThrownBy(() -> warehouse.getProductsByCategorySorted(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getProductsCreatedAfter(LocalDateTime date): returns Products created after a given date")
    public void getProductsCreatedAfter() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = createProductWithMockedDate("1", "Laptop", Category.GENERAL, 1, 2025, 1, 5, 18, 0, 0);
        Product testProduct2 = createProductWithMockedDate("2", "Notebook", Category.GENERAL, 1, 1997, 1, 27, 18, 0, 0);
        Product testProduct3 = createProductWithMockedDate("3", "Desk Chair", Category.GENERAL, 1, 1997, 3, 31, 18, 0, 0);
        Product testProduct4 = createProductWithMockedDate("4", "Water Bottle", Category.GENERAL, 1, 1782, 8, 24, 18, 0, 0);
        Product testProduct5 = createProductWithMockedDate("5", "Pen Set", Category.GENERAL, 1, 2002, 1, 1, 18, 0, 0);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);
        warehouse.addProduct(testProduct3);
        warehouse.addProduct(testProduct4);
        warehouse.addProduct(testProduct5);

        LocalDateTime testDate = LocalDateTime.of(1997, 2, 1, 18, 0, 0, 0);
        assertThat(warehouse.getProductsCreatedAfter(testDate)).extracting(Product::getName).containsExactly("Laptop", "Desk Chair", "Pen Set");
    }

    @Test
    @DisplayName("getProductsCreatedAfter(LocalDate date): throws an exception if invalid input")
    public void getProductsCreatedAfterEmptyList() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = createProductWithMockedDate("1", "Laptop", Category.GENERAL, 1, 2025, 1, 5, 18, 0, 0);
        Product testProduct2 = createProductWithMockedDate("2", "Notebook", Category.GENERAL, 1, 1997, 1, 27, 18, 0, 0);
        Product testProduct3 = createProductWithMockedDate("3", "Desk Chair", Category.GENERAL, 1, 1997, 3, 31, 18, 0, 0);
        Product testProduct4 = createProductWithMockedDate("4", "Water Bottle", Category.GENERAL, 1, 1782, 8, 24, 18, 0, 0);
        Product testProduct5 = createProductWithMockedDate("5", "Pen Set", Category.GENERAL, 1, 2002, 1, 1, 18, 0, 0);

        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);
        warehouse.addProduct(testProduct3);
        warehouse.addProduct(testProduct4);
        warehouse.addProduct(testProduct5);

        assertThatThrownBy(() -> warehouse.getProductsCreatedAfter(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getModifiedProducts(): returns Products where createdDate != modifiedDate")
    public void getModifiedProductsNotEqualToCreatedDate() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL, 1);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Television", Category.GENERAL, 1);

        //Delay a bit to make sure modifiedDate is different from createdDate
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        warehouse.updateProduct("1", "Desktop", Category.GENERAL, 10);

        assertThat(warehouse.getModifiedProducts()).extracting(Product::getName).containsExactly("Desktop");
        assertThat(warehouse.getModifiedProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("getModifiedProducts(): returns an empty list if no products found")
    public void getModifiedProductsEmptyList() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", Category.GENERAL, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL, 1);

        assertThat(warehouse.getModifiedProducts()).isEmpty();
    }

    @Test
    @DisplayName("getCategoriesWithProducts(): return all categories with at least one product")
    public void getCategoriesWithProducts() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", ELECTRONICS, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Television", ELECTRONICS, 1);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Apple", Category.FOOD, 1);

        assertThat(warehouse.getCategoriesWithProducts().size()).isEqualTo(2);
        assertThat(warehouse.getCategoriesWithProducts()).containsExactlyInAnyOrder(ELECTRONICS, Category.FOOD);
    }

    @Test
    @DisplayName("getCategoriesWithProducts(): return empty list if no products found")
    public void getCategoriesWithProductsEmptyList() {
        Warehouse warehouse = new Warehouse();

        assertThat(warehouse.getCategoriesWithProducts()).isEmpty();
    }

    @Test
    @DisplayName("countProductsInCategory(): returns number of products in a category")
    public void countProductsInCategoryCountsProducts() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", ELECTRONICS, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL, 1);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Television", ELECTRONICS, 1);
        Product testProduct4 = addProductToWarehouse(warehouse, "4", "Sandwich", Category.FOOD, 1);

        assertThat(warehouse.countProductsInCategory(Category.ELECTRONICS)).isEqualTo(2);
        assertThat(warehouse.countProductsInCategory(Category.GENERAL)).isEqualTo(1);
        assertThat(warehouse.countProductsInCategory(Category.FOOD)).isEqualTo(1);
    }

    @Test
    @DisplayName("countProductsInCategory(): returns 0 if no products found")
    public void countProductsInCategoryEmptyList() {
        Warehouse warehouse = new Warehouse();
        assertThat(warehouse.countProductsInCategory(Category.ELECTRONICS)).isEqualTo(0);
    }

    @Test
    @DisplayName("countProductsInCategory(): throws exception if invalid input")
    public void countProductsInCategoryThrowsException() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", ELECTRONICS, 1);

        assertThatThrownBy(() -> warehouse.countProductsInCategory(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getProductInitialsMap(): returns a Map<Character, Integer> of first letters in product names and their counts")
    public void getProductInitialsMap() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = addProductToWarehouse(warehouse, "1", "Laptop", ELECTRONICS, 1);
        Product testProduct2 = addProductToWarehouse(warehouse, "2", "Computer", Category.GENERAL, 1);
        Product testProduct3 = addProductToWarehouse(warehouse, "3", "Camera", ELECTRONICS, 1);
        Product testProduct4 = addProductToWarehouse(warehouse, "4", "Sandwich", Category.FOOD, 1);
        Product testProduct5 = addProductToWarehouse(warehouse, "5", "Sushi", Category.FOOD, 1);

        Map<Character, Integer> expectedMap = warehouse.getProductInitialsMap();

        assertThat(expectedMap.size()).isEqualTo(3);
        assertThat(expectedMap.get('L')).isEqualTo(1);
        assertThat(expectedMap.get('C')).isEqualTo(2);
        assertThat(expectedMap.get('S')).isEqualTo(2);
    }

    @Test
    @DisplayName("getProductInitialsMap(): returns an empty Map if no products found")
    public void getProductInitialsMapEmptyList() {
        Warehouse warehouse = new Warehouse();
        assertThat(warehouse.getProductInitialsMap().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("getTopRatedProductsThisMonth(): returns Products with max rating, created this month, sorted by newest first")
    public void getTopRatedProductsThisMonth() {
        Warehouse warehouse = new Warehouse();
        Product testProduct1 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 9, 2002, 1, 1, 18, 0, 0);
        Product testProduct2 = createProductWithMockedDate("3", "Water Bottle", Category.GENERAL, 10, 1782, 8, 24, 18, 0, 0);
        warehouse.addProduct(testProduct1);
        warehouse.addProduct(testProduct2);

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime date1 = LocalDateTime.of(2025, 1, 18, 18, 0, 0, 0);
            LocalDateTime date2 = LocalDateTime.of(2025, 1, 18, 18, 0, 1, 0);
            LocalDateTime date3 = LocalDateTime.of(2025, 1, 18, 18, 0, 2, 0);

            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(date1)
                    .thenReturn(date2)
                    .thenReturn(date3);

            Product testProduct3 = addProductToWarehouse(warehouse, "1", "Laptop", ELECTRONICS, 10);
            Product testProduct4 = addProductToWarehouse(warehouse, "2", "Television", ELECTRONICS, 10);
            Product testProduct5 = addProductToWarehouse(warehouse, "5", "Computer", Category.GENERAL, 9);

            List<Product> expectedProducts = warehouse.getTopRatedProductsThisMonth();

            assertThat(expectedProducts.size()).isEqualTo(2);
            assertThat(expectedProducts).containsExactly(testProduct4, testProduct3);
        }
    }

    @Test
    @DisplayName("getTopRatedProductsThisMonth(): returns an empty list if no products found")
    public void getTopRatedProductsThisMonthEmptyList() {
        Warehouse warehouse = new Warehouse();
        assertThat(warehouse.getTopRatedProductsThisMonth()).isEmpty();
        Product testProduct1 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 9, 2025, 1, 31, 23, 59, 59);
        warehouse.addProduct(testProduct1);

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime date1 = LocalDateTime.of(2025, 2, 1, 00, 00, 00, 0);

            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(date1);

            List<Product> expectedProducts = warehouse.getTopRatedProductsThisMonth();

            assertThat(expectedProducts).isEmpty();
        }
    }

}
