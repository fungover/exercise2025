package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.entities.Category.ELECTRONICS;

public class ProductServiceTest {
    private InMemoryProductRepository inMemoryProductRepository;
    private ProductService productService;

    @BeforeEach
    void beforeEach() {
        inMemoryProductRepository = new InMemoryProductRepository();
        productService = new ProductService(inMemoryProductRepository);
    }

    // Helper methods
    private Product addProductToWarehouse(String id, String name, Category category, int rating, double price) {
        Product product = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .build();

        productService.addProduct(product);
        return product;
    }

    private Product createProductWithMockedDate(String id, String name, Category category, int rating, double price, int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime mockedDate = LocalDateTime.of(year, month, day, hour, minute, second, 0);
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockedStatic.when(LocalDateTime::now).thenReturn(mockedDate);
            return new Product.Builder()
                    .id(id)
                    .name(name)
                    .category(category)
                    .rating(rating)
                    .price(price)
                    .build();
        }
    }

    // Tests
    @Test
    @DisplayName("addProduct: adds a new Product to Warehouse")
    public void addProduct() {
        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();

        productService.addProduct(testProduct);

        assertThat(productService.getAllProducts()).contains(testProduct);
    }

    @Test
    @DisplayName("addProduct: throws IllegalArgumentException if Product name is blank")
    public void throwExceptionIfNoProductNameProvided() {
        assertThatThrownBy(() -> new Product.Builder()
                .id("1")
                .name("")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("updateProduct: updates an existing Product by ID")
    public void updateProduct() {
        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();
        productService.addProduct(testProduct);

        Product updatedProductData = new Product.Builder()
                .id("1")
                .name("Updated Test Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(1)
                .build();
        productService.updateProduct(updatedProductData);

        Product updatedProduct = productService.getProductById("1").get();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Test Product");
        assertThat(updatedProduct.getCategory()).isEqualTo(ELECTRONICS);
        assertThat(updatedProduct.getRating()).isEqualTo(5);
        assertThat(updatedProduct.getId()).isEqualTo("1");
        assertThat(productService.getAllProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("updateProduct: updates an existing Product without changing createdDate")
    public void updateProductWithoutChangingCreatedDate() {
        Product testProduct = createProductWithMockedDate("1", "Test Product", Category.GENERAL, 1, 1, 2024, 1, 5, 18, 0, 0);
        productService.addProduct(testProduct);

        Product updatedProductData = new Product.Builder()
                .id("1")
                .name("Updated Product")
                .category(Category.ELECTRONICS)
                .rating(5)
                .price(1)
                .build();
        productService.updateProduct(updatedProductData);

        Product updated = productService.getProductById("1").get();
        assertThat(updated.getCreatedDate()).isEqualTo(testProduct.getCreatedDate());
        assertThat(updated.getModifiedDate()).isAfter(updated.getCreatedDate());
        assertThat(updated.getName()).isEqualTo("Updated Product");
        assertThat(updated.getCategory()).isEqualTo(Category.ELECTRONICS);
        assertThat(updated.getRating()).isEqualTo(5);
    }

    @Test
    @DisplayName("updateProduct: throws IllegalArgumentException if Product ID is not found")
    public void throwExceptionIfNoProductFoundToUpdate() {
        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();

        productService.addProduct(testProduct);

        assertThatThrownBy(() -> productService.updateProduct(new Product.Builder()
                .id("2")
                .name("Updated Product")
                .category(Category.GENERAL)
                .rating(2)
                .price(5)
                .build()
        ))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("testBlankIncludeNull")
    @DisplayName("updateProduct: throws IllegalArgumentException for invalid inputs")
    void throwExceptionIfInvalidInputToUpdate(String id, String name, Category category, int rating, double price) {
        productService.addProduct(new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build()
        );

        assertThatThrownBy(() -> productService.updateProduct(new Product.Builder()
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .build())
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> testBlankIncludeNull() {
        return Stream.of(
                //Id
                Arguments.of(null, "Test Product", Category.GENERAL, 5, 1),
                Arguments.of("", "Test Product", Category.GENERAL, 5, 1),
                Arguments.of(" ", "Test Product", Category.GENERAL, 5, 1),

                //Name
                Arguments.of("1", null, Category.GENERAL, 5, 1),
                Arguments.of("1", "", Category.GENERAL, 5, 1),
                Arguments.of("1", " ", Category.GENERAL, 5, 1),

                //Category
                Arguments.of("1", "Test Product", null, 5, 1),

                //Rating
                Arguments.of("1", "Test Product", Category.GENERAL, 11, 1),
                Arguments.of("1", "Test Product", Category.GENERAL, 0, 1),

                //Price
                Arguments.of("1", "Test Product", Category.GENERAL, 11, -5)
        );
    }

    @Test
    @DisplayName("getAllProducts: returns all Products in Warehouse")
    public void getAllProducts() {
        Product testProduct1 = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();
        productService.addProduct(testProduct1);

        Product testProduct2 = new Product.Builder()
                .id("2")
                .name("Test Product2")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();
        productService.addProduct(testProduct2);

        assertThat(productService.getAllProducts()).contains(testProduct1, testProduct2);
        assertThat(productService.getAllProducts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("getAllProducts: returns empty list if Warehouse is empty")
    public void getAllProductsEmptyWarehouse() {

        assertThat(productService.getAllProducts()).isEmpty();
    }

    @Test
    @DisplayName("getProductById: returns a Product by id")
    public void getProductById() {
        Product testProduct = new Product.Builder()
                .id("99")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();
        productService.addProduct(testProduct);

        assertThat(productService.getProductById("99")).contains(testProduct);
    }

    @Test
    @DisplayName("getProductById: returns empty Optional if Product ID is not found")
    public void returnsEmptyOptionalIfNoProductFound() {
        Product testProduct = new Product.Builder()
                .id("99")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();
        productService.addProduct(testProduct);

        assertThat(productService.getProductById("100")).isEmpty();
    }

    @Test
    @DisplayName("getProductById: throws exception if invalid input")
    public void throwsExceptionIfNoProductIdProvided() {
        Product testProduct = new Product.Builder()
                .id("99")
                .name("Test Product")
                .category(Category.GENERAL)
                .rating(1)
                .price(1)
                .build();
        productService.addProduct(testProduct);

        assertThatThrownBy(() -> productService.getProductById(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getProductsByCategorySorted: returns Products in a category, sorted A-Z by name")
    public void getProductsByCategorySorted() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", Category.GENERAL, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Computer", Category.GENERAL, 1, 1);
        Product testProduct3 = addProductToWarehouse("3", "Television", Category.GENERAL, 1, 1);
        Product testProduct4 = addProductToWarehouse("4", "Sandwich", Category.GENERAL, 1, 1);
        Product testProduct5 = addProductToWarehouse("5", "Candy", Category.GENERAL, 1, 1);
        Product testProduct6 = addProductToWarehouse("6", "VR Glasses", Category.GENERAL, 1, 1);
        Product testProduct7 = addProductToWarehouse("7", "Android TV", ELECTRONICS, 1, 1);

        assertThat(productService.getProductsByCategorySorted(Category.GENERAL))
                .extracting(Product::getName)
                .containsExactly("Candy", "Computer", "Laptop", "Sandwich", "Television", "VR Glasses");
    }

    @Test
    @DisplayName("getProductsByCategorySorted: returns empty list if Category is empty")
    public void getProductsByCategorySortedEmptyCategory() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", Category.GENERAL, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Computer", Category.GENERAL, 1, 1);
        Product testProduct3 = addProductToWarehouse("3", "Television", Category.GENERAL, 1, 1);

        assertThat(productService.getProductsByCategorySorted(ELECTRONICS)).isEmpty();
    }

    @Test
    @DisplayName("getProductsByCategorySorted: throws exception if invalid input")
    public void throwsExceptionIfNoCategoryProvided() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", Category.GENERAL, 1, 1);

        assertThatThrownBy(() -> productService.getProductsByCategorySorted(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getProductsCreatedAfter(LocalDateTime date): returns Products created after a given date")
    public void getProductsCreatedAfter() {
        Product testProduct1 = createProductWithMockedDate("1", "Laptop", Category.GENERAL, 1, 1, 2025, 1, 5, 18, 0, 0);
        Product testProduct2 = createProductWithMockedDate("2", "Notebook", Category.GENERAL, 1, 1, 1997, 1, 27, 18, 0, 0);
        Product testProduct3 = createProductWithMockedDate("3", "Desk Chair", Category.GENERAL, 1, 1, 1997, 3, 31, 18, 0, 0);
        Product testProduct4 = createProductWithMockedDate("4", "Water Bottle", Category.GENERAL, 1, 1, 1782, 8, 24, 18, 0, 0);
        Product testProduct5 = createProductWithMockedDate("5", "Pen Set", Category.GENERAL, 1, 1, 2002, 1, 1, 18, 0, 0);

        productService.addProduct(testProduct1);
        productService.addProduct(testProduct2);
        productService.addProduct(testProduct3);
        productService.addProduct(testProduct4);
        productService.addProduct(testProduct5);

        LocalDateTime testDate = LocalDateTime.of(1997, 2, 1, 18, 0, 0, 0);
        assertThat(productService.getProductsCreatedAfter(testDate)).extracting(Product::getName).containsExactly("Laptop", "Desk Chair", "Pen Set");
    }

    @Test
    @DisplayName("getProductsCreatedAfter(LocalDate date): throws an exception if invalid input")
    public void getProductsCreatedAfterEmptyList() {
        Product testProduct1 = createProductWithMockedDate("1", "Laptop", Category.GENERAL, 1, 1, 2025, 1, 5, 18, 0, 0);
        Product testProduct2 = createProductWithMockedDate("2", "Notebook", Category.GENERAL, 1, 1, 1997, 1, 27, 18, 0, 0);
        Product testProduct3 = createProductWithMockedDate("3", "Desk Chair", Category.GENERAL, 1, 1, 1997, 3, 31, 18, 0, 0);
        Product testProduct4 = createProductWithMockedDate("4", "Water Bottle", Category.GENERAL, 1, 1, 1782, 8, 24, 18, 0, 0);
        Product testProduct5 = createProductWithMockedDate("5", "Pen Set", Category.GENERAL, 1, 1, 2002, 1, 1, 18, 0, 0);

        productService.addProduct(testProduct1);
        productService.addProduct(testProduct2);
        productService.addProduct(testProduct3);
        productService.addProduct(testProduct4);
        productService.addProduct(testProduct5);

        assertThatThrownBy(() -> productService.getProductsCreatedAfter(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getModifiedProducts(): returns Products where createdDate != modifiedDate")
    public void getModifiedProductsNotEqualToCreatedDate() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", Category.GENERAL, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Computer", Category.GENERAL, 1, 1);
        Product testProduct3 = addProductToWarehouse("3", "Television", Category.GENERAL, 1, 1);

        //Delay a bit to make sure modifiedDate is different from createdDate
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        productService.updateProduct(new Product.Builder()
                .id("1")
                .name("Desktop")
                .category(Category.GENERAL)
                .rating(10)
                .price(1)
                .build());

        assertThat(productService.getModifiedProducts()).extracting(Product::getName).containsExactly("Desktop");
        assertThat(productService.getModifiedProducts().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("getModifiedProducts(): returns an empty list if no products found")
    public void getModifiedProductsEmptyList() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", Category.GENERAL, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Computer", Category.GENERAL, 1, 1);

        assertThat(productService.getModifiedProducts()).isEmpty();
    }

    @Test
    @DisplayName("getCategoriesWithProducts(): return all categories with at least one product")
    public void getCategoriesWithProducts() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", ELECTRONICS, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Television", ELECTRONICS, 1, 1);
        Product testProduct3 = addProductToWarehouse("3", "Apple", Category.FOOD, 1, 1);

        assertThat(productService.getCategoriesWithProducts().size()).isEqualTo(2);
        assertThat(productService.getCategoriesWithProducts()).containsExactlyInAnyOrder(ELECTRONICS, Category.FOOD);
    }

    @Test
    @DisplayName("getCategoriesWithProducts(): return empty list if no products found")
    public void getCategoriesWithProductsEmptyList() {
        assertThat(productService.getCategoriesWithProducts()).isEmpty();
    }

    @Test
    @DisplayName("countProductsInCategory(): returns number of products in a category")
    public void countProductsInCategoryCountsProducts() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", ELECTRONICS, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Computer", Category.GENERAL, 1, 1);
        Product testProduct3 = addProductToWarehouse("3", "Television", ELECTRONICS, 1, 1);
        Product testProduct4 = addProductToWarehouse("4", "Sandwich", Category.FOOD, 1, 1);

        assertThat(productService.countProductsInCategory(Category.ELECTRONICS)).isEqualTo(2);
        assertThat(productService.countProductsInCategory(Category.GENERAL)).isEqualTo(1);
        assertThat(productService.countProductsInCategory(Category.FOOD)).isEqualTo(1);
    }

    @Test
    @DisplayName("countProductsInCategory(): returns 0 if no products found")
    public void countProductsInCategoryEmptyList() {
        assertThat(productService.countProductsInCategory(Category.ELECTRONICS)).isEqualTo(0);
    }

    @Test
    @DisplayName("countProductsInCategory(): throws exception if invalid input")
    public void countProductsInCategoryThrowsException() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", ELECTRONICS, 1, 1);

        assertThatThrownBy(() -> productService.countProductsInCategory(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("getProductInitialsMap(): returns a Map<Character, Integer> of first letters in product names and their counts")
    public void getProductInitialsMap() {
        Product testProduct1 = addProductToWarehouse("1", "Laptop", ELECTRONICS, 1, 1);
        Product testProduct2 = addProductToWarehouse("2", "Computer", Category.GENERAL, 1, 1);
        Product testProduct3 = addProductToWarehouse("3", "Camera", ELECTRONICS, 1, 1);
        Product testProduct4 = addProductToWarehouse("4", "Sandwich", Category.FOOD, 1, 1);
        Product testProduct5 = addProductToWarehouse("5", "Sushi", Category.FOOD, 1, 1);

        Map<Character, Integer> expectedMap = productService.getProductInitialsMap();

        assertThat(expectedMap.size()).isEqualTo(3);
        assertThat(expectedMap.get('L')).isEqualTo(1);
        assertThat(expectedMap.get('C')).isEqualTo(2);
        assertThat(expectedMap.get('S')).isEqualTo(2);
    }

    @Test
    @DisplayName("getProductInitialsMap(): returns an empty Map if no products found")
    public void getProductInitialsMapEmptyList() {
        assertThat(productService.getProductInitialsMap().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("getTopRatedProductsThisMonth(): returns Products with max rating, created this month, sorted by newest first")
    public void getTopRatedProductsThisMonth() {
        Product testProduct1 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 9, 1, 2002, 1, 1, 18, 0, 0);
        Product testProduct2 = createProductWithMockedDate("3", "Water Bottle", Category.GENERAL, 10, 1, 1782, 8, 24, 18, 0, 0);
        productService.addProduct(testProduct1);
        productService.addProduct(testProduct2);

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime date1 = LocalDateTime.of(2025, 1, 18, 18, 0, 0, 0);
            LocalDateTime date2 = LocalDateTime.of(2025, 1, 18, 18, 0, 1, 0);
            LocalDateTime date3 = LocalDateTime.of(2025, 1, 18, 18, 0, 2, 0);

            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(date1)
                    .thenReturn(date2)
                    .thenReturn(date3);

            Product testProduct3 = addProductToWarehouse("1", "Laptop", ELECTRONICS, 10, 1);
            Product testProduct4 = addProductToWarehouse("2", "Television", ELECTRONICS, 10, 1);
            Product testProduct5 = addProductToWarehouse("5", "Computer", Category.GENERAL, 9, 1);

            List<Product> expectedProducts = productService.getTopRatedProductsThisMonth();

            assertThat(expectedProducts.size()).isEqualTo(2);
            assertThat(expectedProducts).containsExactly(testProduct4, testProduct3);
        }
    }

    @Test
    @DisplayName("getTopRatedProductsThisMonth(): returns an empty list if no products found")
    public void getTopRatedProductsThisMonthEmptyList() {
        assertThat(productService.getTopRatedProductsThisMonth()).isEmpty();
        Product testProduct1 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 9, 1, 2025, 1, 31, 23, 59, 59);
        productService.addProduct(testProduct1);

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime date1 = LocalDateTime.of(2025, 2, 1, 00, 00, 00, 0);

            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(date1);

            List<Product> expectedProducts = productService.getTopRatedProductsThisMonth();

            assertThat(expectedProducts).isEmpty();
        }
    }

    @Test
    @DisplayName("getTopRatedProductsThisMonth(): excludes product from the same month but a different year")
    public void getTopRatedProductsThisMonthExcludesProductFromDifferentYear() {
        Product testProduct1 = createProductWithMockedDate("4", "Pen Set", Category.GENERAL, 9, 1, 2024, 1, 1, 13, 59, 59);
        productService.addProduct(testProduct1);

        try (MockedStatic mockedStatic = Mockito.mockStatic(LocalDateTime.class, Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime date1 = LocalDateTime.of(2025, 1, 1, 13, 00, 00, 0);
            mockedStatic.when(LocalDateTime::now)
                    .thenReturn(date1);

            List<Product> expectedProducts = productService.getTopRatedProductsThisMonth();

            assertThat(expectedProducts).isEmpty();
        }
    }

}
