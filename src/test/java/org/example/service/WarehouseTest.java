package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();

    }

    // ========================================
    // TESTS FOR addProduct(Product product)
    // ========================================

    @Test
    @DisplayName("Should add product successfully")
    void addProductSuccessfully() {

        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        warehouse.addProduct(testProduct); // Add the product to the warehouse

        List<Product> products = warehouse.getAllProducts(); // Retrieve all products to verify the addition
        assertEquals(1, products.size()); // Check that the size of the product list is 1
        assertTrue(products.contains(testProduct)); // Check that the added product is in the list
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when adding product with empty name")
    void addProductWithEmptyName() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Product.Builder()
                        .id("2")
                        .name("   ")
                        .category(Category.BOOKS)
                        .rating(5)
                        .build()
        );
        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when adding null product")
    void addNullProductThrowsException() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> warehouse.addProduct(null)
        );
        assertEquals("Product cannot be null", exception.getMessage());
    }

    // ========================================
    // TESTS FOR updateProduct()
    // ========================================

    @Test
    @DisplayName("Should update a product successfully")
    void updateProductSuccessfully() {

        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        Product originalProduct = new Product.Builder()
                .id("1")
                .name("Original Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(threeDaysAgo) // Set created date to 3 days ago
                .modifiedDate(threeDaysAgo) // Set modified date to 3 days ago
                .build();


        warehouse.addProduct(originalProduct);

        warehouse.updateProduct("1", "Updated Product", Category.BOOKS, 9);

        Optional<Product> updated = warehouse.getProductById("1"); // Retrieve the updated product
        assertTrue(updated.isPresent()); // Check that the product is present
        assertEquals("Updated Product", updated.get().name()); // Verify that the name has been updated
        assertEquals(Category.BOOKS, updated.get().category()); // Verify that the category has been updated
        assertEquals(9, updated.get().rating()); // Verify that the rating has been updated
        assertEquals(originalProduct.createdDate(), updated.get().createdDate()); // Verify that the created date remains unchanged
        assertNotEquals(originalProduct.modifiedDate(), updated.get().modifiedDate()); // Verify that the modified date has been updated
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updating a non-existent product")
    void updateProductThatDoesNotExist() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> warehouse.updateProduct("999", "New Name", Category.BOOKS, 8) // Attempt to update a product that doesn't exist
        );
        assertEquals("Product with id 999 not found", exception.getMessage()); // Verify the exception message
    }

    @Test
    @DisplayName("Should throw exception when updating with empty name")
    void updateProductWithEmptyName() {

        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        warehouse.addProduct(testProduct); // Add the product to the warehouse

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, // Expect an IllegalArgumentException
                () -> warehouse.updateProduct("1", "   ", Category.BOOKS, 8) // Attempt to update with an empty name
        );
        assertEquals("Name is required", exception.getMessage()); // Verify the exception message
    }

    // ========================================
    // TESTS FOR getAllProducts()
    // ========================================

    @Test
    @DisplayName("Should return all products")
    void getAllProductsSuccessfully() {

        Product product1 = new Product.Builder()
                .id("1")
                .name("Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        LocalDate fiveDaysAgo = LocalDate.now().minusDays(5); // Set created and modified date to 5 days ago
        Product product2 = new Product.Builder()
                .id("2")
                .name("Product 2")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(fiveDaysAgo)
                .modifiedDate(fiveDaysAgo)
                .build();

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        List<Product> products = warehouse.getAllProducts();

        assertEquals(2, products.size()); // Verify that there are 2 products in the list
        assertTrue(products.contains(product1)); // Verify that product1 is in the list
        assertTrue(products.contains(product2)); // Verify that product2 is in the list
    }

    @Test
    @DisplayName("Should return empty list when no products exists")
    void getAllProductsWhenThereIsNoProducts() {
        List<Product> products = warehouse.getAllProducts(); // Retrieve all products from an empty warehouse
        assertTrue(products.isEmpty()); // Verify that the product list is empty
    }

    // ========================================
    // TESTS FOR getProductById()
    // ========================================

    @Test
    @DisplayName("Should return a product by correct ID")
    void getProductByIdSuccessfully() {
        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        warehouse.addProduct(testProduct);

        Optional<Product> result = warehouse.getProductById("1"); // Retrieve the product by its ID

        assertTrue(result.isPresent()); // Verify that the product is found
        assertEquals(testProduct, result.get()); // Verify that the retrieved product matches the added product
    }

    @Test
    @DisplayName("Should return empty Optional when product ID does not exist")
    void getProductByIdWhenIdDoesNotExist() {
        Optional<Product> result = warehouse.getProductById("999"); // Attempt to retrieve a product with a non-existent ID
        assertTrue(result.isEmpty()); // Verify that the result is an empty Optional
    }

    // ========================================
    // TESTS FOR getProductsByCategorySorted()
    // ========================================

    @Test
    @DisplayName("Should return products in category sorted by name (A-Z)")
    void getProductsByCategorySortedSuccessfullyByName() {

        Product laptop = new Product.Builder()
                .id("1")
                .name("Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .build();
        Product mouse = new Product.Builder()
                .id("2")
                .name("Gaming Mouse")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();
        Product book = new Product.Builder()
                .id("3")
                .name("Java Book")
                .category(Category.BOOKS)
                .rating(10)
                .build();

        warehouse.addProduct(laptop);
        warehouse.addProduct(mouse);
        warehouse.addProduct(book);

        List<Product> electronics = warehouse.getProductsByCategorySorted(Category.ELECTRONICS); // Retrieve products in the ELECTRONICS category sorted by name

        assertEquals(2, electronics.size()); // Verify that there are 2 products in the ELECTRONICS category
        assertEquals("Gaming Laptop", electronics.get(0).name()); // Verify that the first product is "Gaming Laptop" (L comes before M
        assertEquals("Gaming Mouse", electronics.get(1).name()); // Verify that the second product is "Gaming Mouse"
    }

    @Test
    @DisplayName("Should return empty list when no products in the specified category")
    void getProductsByCategorySortedWhenNoProductsInCategory() {

        Product electronicsProduct = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        warehouse.addProduct(electronicsProduct);

        List<Product> toys = warehouse.getProductsByCategorySorted(Category.TOYS); // Attempt to retrieve products in the TOYS category when none exist

        assertTrue(toys.isEmpty()); // Verify that the result is an empty list
    }

    // ========================================
    // TESTS FOR getProductsCreatedAfter()
    // ========================================

    @Test
    @DisplayName("Should return products created after a specified date")
    void getProductsCreatedAfterSpecifiedDateSuccessfully() {

        LocalDate baseDate = LocalDate.of(2025, 9, 2); // Fixed base date for testing

        Product oldProduct = new Product.Builder()
                .id("1")
                .name("Old Product")
                .category(Category.BOOKS)
                .rating(6)
                .createdDate(baseDate.minusDays(2)) // Created 2 days before baseDate (2025-09-01)
                .modifiedDate(baseDate.minusDays(2))
                .build();

        Product newProduct = new Product.Builder()
                .id("2")
                .name("New Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(baseDate.plusDays(2)) // Created 2 days after baseDate (2025-09-04)
                .modifiedDate(baseDate.plusDays(2))
                .build();

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(newProduct);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(baseDate); // Retrieve products created after baseDate (2025-09-02)

        assertEquals(1, recentProducts.size()); // Verify that there is 1 product created after the specified date
        assertEquals("New Product", recentProducts.get(0).name()); // Verify that the product is "New Product"
    }

    @Test
    @DisplayName("Should return empty list when no products created after the specified date")
    void getProductsCreatedAfterSpecifiedDateWhenThereIsNoProducts() {

        LocalDate baseDate = LocalDate.of(2025, 9, 1); // Fixed base date for testing

        Product oldProduct = new Product.Builder()
                .id("1")
                .name("Old Product")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(baseDate.minusDays(3)) // Created 3 days before baseDate (2025-08-29)
                .modifiedDate(baseDate.minusDays(3))
                .build();
        warehouse.addProduct(oldProduct);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(baseDate); // Attempt to retrieve products created after baseDate (2025-09-01)

        assertTrue(recentProducts.isEmpty()); // Verify that the result is an empty list
    }

    // ========================================
    // TESTS FOR getModifiedProducts()
    // ========================================

    @Test
    @DisplayName("Should return modified products when products are modified")
    void getModifiedProductsSuccessfullyWhenThereAreModifiedProducts() {

        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);

        Product originalProduct = new Product.Builder()
                .id("1")
                .name("Original Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(threeDaysAgo) // Set created date to 3 days ago
                .modifiedDate(threeDaysAgo) // Set modified date to 3 days ago
                .build();

        Product unmodifiedProduct = new Product.Builder()
                .id("2")
                .name("Unmodified Product")
                .category(Category.BOOKS)
                .rating(7)
                .build();

        warehouse.addProduct(originalProduct);
        warehouse.addProduct(unmodifiedProduct);

        warehouse.updateProduct("1", "Modified Product", Category.ELECTRONICS, 9); // Modify the first product
        List<Product> modifiedProducts = warehouse.getModifiedProducts(); // Retrieve the list of modified products

        assertEquals(1, modifiedProducts.size()); // Verify that there is 1 modified product
        assertEquals("Modified Product", modifiedProducts.get(0).name()); // Verify that the modified product has the updated name
    }

    @Test
    @DisplayName("Should return empty when when no products have been modified")
    void getModifiedProductsWhenNoProductsHaveBeenModified() {

        Product testProduct = new Product.Builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        warehouse.addProduct(testProduct); // Add the product to the warehouse

        List<Product> modifiedProducts = warehouse.getModifiedProducts(); // Attempt to retrieve modified products when none have been modified

        assertTrue(modifiedProducts.isEmpty()); // Verify that the result is an empty list
    }

    // ========================================
    // TESTS FOR getCategoriesWithProducts()
    // ========================================

    @Test
    @DisplayName("Should return all categories with atleast one product.")
    void getCategoriesWithProductsSuccessfullyWhenThereAreProducts() {

        Product laptop = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .build();
        Product book = new Product.Builder()
                .id("2")
                .name("Book")
                .category(Category.BOOKS)
                .rating(8)
                .build();
        Product mouse = new Product.Builder()
                .id("3")
                .name("Mouse")
                .category(Category.ELECTRONICS)
                .rating(7)
                .build();

        warehouse.addProduct(laptop);
        warehouse.addProduct(book);
        warehouse.addProduct(mouse);

        Set<Category> categories = warehouse.getCategoriesWithProducts(); // Retrieve the set of categories with at least one product

        assertEquals(2, categories.size()); // Verify that there are 2 unique categories
        assertTrue(categories.contains(Category.ELECTRONICS)); // Verify that the ELECTRONICS category is included
        assertTrue(categories.contains(Category.BOOKS)); // Verify that the BOOKS category is included
    }

    @Test
    @DisplayName("Should return empty set when there are no products")
    void getCategoriesWithProductsWhenThereAreNoProducts() {

        Set<Category> categories = warehouse.getCategoriesWithProducts(); // Attempt to retrieve categories when no products exist

        assertTrue(categories.isEmpty()); // Verify that the result is an empty set
    }

    // ========================================
    // TESTS FOR countProductsInCategory(Category category)
    // ========================================

    @Test
    @DisplayName("Should count product in a category correctly")
    void countProductsInCategorySuccessfullyWhenThereAreProductsInCategory() {

        Product laptop = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .build();
        Product mouse = new Product.Builder()
                .id("2")
                .name("Mouse")
                .category(Category.ELECTRONICS)
                .rating(7)
                .build();
        Product book = new Product.Builder()
                .id("3")
                .name("Book")
                .category(Category.BOOKS)
                .rating(3)
                .build();

        warehouse.addProduct(laptop);
        warehouse.addProduct(mouse);
        warehouse.addProduct(book);

        long electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS); // Count products in the ELECTRONICS category
        long booksCount = warehouse.countProductsInCategory(Category.BOOKS); // Count products in the BOOKS category

        assertEquals(2, electronicsCount); // Verify that there are 2 products in the ELECTRONICS category
        assertEquals(1, booksCount); // Verify that there is 1 product in the BOOKS category
    }

    @Test
    @DisplayName("Should return 0 when no products exists in the specified category")
    void countProductsInCategoryWhenThereIsNoProductsInTheSpecifiedCategory() {
        Product laptop = new Product.Builder()
                .id("1")
                .name("Laptop")
                .category(Category.ELECTRONICS)
                .rating(8)
                .build();

        warehouse.addProduct(laptop);

        long toysCount = warehouse.countProductsInCategory(Category.TOYS); // Attempt to count products in the TOYS category when none exist

        assertEquals(0, toysCount); // Verify that the count is 0
    }

//    // ========================================
//    // TESTS FOR getProductsInitialsMap()
//    // ========================================
//
//    @Test
//    @DisplayName("Should return products initials map correctly")
//    void getProductInitialsMapSuccessfullyWhenThereAreProducts() {
//
//        Product apple = Warehouse.createProduct("1", "Apple", Category.FOOD, 8);
//        Product banana = Warehouse.createProduct("2", "Banana", Category.FOOD, 7);
//        Product appleJuice = Warehouse.createProduct("3", "Apple Juice", Category.FOOD, 9);
//
//        warehouse.addProduct(apple);
//        warehouse.addProduct(banana);
//        warehouse.addProduct(appleJuice);
//
//        Map<Character, Integer> initials = warehouse.getProductInitialsMap(); // Retrieve the map of product initials and their counts
//
//        assertEquals(2, initials.size()); // Verify that there are 2 unique initials
//        assertEquals(2, initials.get('A')); // 'A' for Apple and Apple Juice
//        assertEquals(1, initials.get('B')); // 'B' for Banana
//    }
//
//    @Test
//    @DisplayName("Should return empty map when there are no products")
//    void getProductInitialsMapWhenThereAreNoProducts() {
//
//        Map<Character, Integer> initials = warehouse.getProductInitialsMap(); // Attempt to retrieve the map of product initials when no products exist
//        assertTrue(initials.isEmpty()); // Verify that the result is an empty map
//    }
//
//    // ========================================
//    // TESTS FOR getTopRatedProductsThisMonth()
//    // ========================================
//
//    @Test
//    @DisplayName("Should return top rated products created this month sorted by newest first")
//    void getTopRatedProductsThisMonthSuccessfullyWhenThereAreTopRatedProducts() {
//
//        /*
//        To ensure we dont break this test in the future when the currect month changes,
//        we calculate the first day of the current month and create products based on that date.
//        Even though this might create product in the future, it will still be in the current month.
//        And the logic in the method we are testing only cares about the month, not the exact date.
//         */
//
//        LocalDate now = LocalDate.now(); // Current date
//        LocalDate startOfMonth = now.withDayOfMonth(1); // First day of the current month
//
//        Product olderProduct = Warehouse.createProductWithDate("1", "Older Product", Category.ELECTRONICS, 10,
//                startOfMonth.plusDays(2)); // Day 3 of current month
//        Product newerProduct = Warehouse.createProductWithDate("2", "Newer Product", Category.BOOKS, 10,
//                startOfMonth.plusDays(5)); // Day 6 of current month
//        Product newestProduct = Warehouse.createProductWithDate("3", "Newest Product", Category.SPORTS, 10,
//                startOfMonth.plusDays(8)); // Day 9 of current month
//        Product lowerRatedProduct = Warehouse.createProductWithDate("4", "Lower Rated", Category.FOOD, 8,
//                startOfMonth.plusDays(10)); // Should not appear in results
//
//        warehouse.addProduct(olderProduct);
//        warehouse.addProduct(newerProduct);
//        warehouse.addProduct(newestProduct);
//        warehouse.addProduct(lowerRatedProduct);
//
//        List<Product> topRated = warehouse.getTopRatedProductsThisMonth(); // Retrieve the top-rated products created this month
//
//        assertEquals(3, topRated.size());
//        assertTrue(topRated.stream().allMatch(product -> product.rating() == 10)); // Verify that all returned products have the highest rating of 10
//
//        assertEquals("Newest Product", topRated.get(0).name()); // Verify that the newest product is first
//        assertEquals("Newer Product", topRated.get(1).name()); // Verify that the newer product is second
//        assertEquals("Older Product", topRated.get(2).name()); // Verify that the older product is last
//    }
//
//    @Test
//    @DisplayName("Should return empty list when no products are created this month.")
//    void getTopRatedProductsWhenThereAreNoTopRatedProducts() {
//
//        LocalDate augustDate = LocalDate.of(2025, 8, 15); // A date in August 2025
//        Product oldProduct = Warehouse.createProductWithDate("1", "Old Product", Category.BOOKS, 10, augustDate); // Created in August 2025
//        warehouse.addProduct(oldProduct);
//
//        List<Product> topRated = warehouse.getTopRatedProductsThisMonth(); // Attempt to retrieve top-rated products created this month (September 2025)
//
//        assertTrue(topRated.isEmpty()); // Verify that the result is an empty list since no products were created this month
//    }
//
//    // ========================================
//    // TESTS for Product creation validations
//    // ========================================
//
//    @Test
//    @DisplayName("Should throw exception when creating product with empty ID")
//    void createProductWithEmptyIdThrowsException() {
//
//        IllegalArgumentException exception = assertThrows(
//                IllegalArgumentException.class,
//                () -> new Product("   ", "Test Product", Category.ELECTRONICS, 8, LocalDate.now(), LocalDate.now())
//        );
//        assertEquals("ID cannot be empty", exception.getMessage());
//    }

}
