package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;
    private Product product;

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

        Product testProduct = Warehouse.createProduct("1", "Test Product", Category.ELECTRONICS, 8);
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
                () -> new Product("2", "    ", Category.BOOKS, 5, LocalDate.now(), LocalDate.now())
        );
        assertEquals("Name cannot be empty", exception.getMessage());
    }

    // ========================================
    // TESTS FOR updateProduct()
    // ========================================

    @Test
    @DisplayName("Should update a product successfully")
    void updateProductSuccessfully() {

        Product originalProduct = Warehouse.createOldProduct("1", "Original Product", Category.ELECTRONICS, 8, 3); // Create a product with a created date 3 days ago
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

        Product testProduct = Warehouse.createProduct("1", "Test Product", Category.ELECTRONICS, 8);
        warehouse.addProduct(testProduct); // Add the product to the warehouse

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, // Expect an IllegalArgumentException
                () -> warehouse.updateProduct("1", "   ", Category.BOOKS, 8) // Attempt to update with an empty name
        );
        assertEquals("Name cannot be empty", exception.getMessage()); // Verify the exception message
    }

    // ========================================
    // TESTS FOR getAllProducts()
    // ========================================

    @Test
    @DisplayName("Should return all products")
    void getAllProductsSuccessfully() {
        Product product1 = Warehouse.createProduct("1", "Product 1", Category.ELECTRONICS, 8);
        Product product2 = Warehouse.createOldProduct("2", "Product 2", Category.BOOKS, 6, 5);
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
        Product testProduct = Warehouse.createProduct("1", "Test Product", Category.ELECTRONICS, 8);
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
    @DisplayName("Should return products in category sorted by name (A-Z")
    void getProductsByCategorySortedSuccessfullyByName() {

        Product laptop = Warehouse.createProduct("1", "Gaming Laptop", Category.ELECTRONICS, 9);
        Product mouse = Warehouse.createProduct("2", "Gaming Mouse", Category.ELECTRONICS, 8);
        Product book = Warehouse.createProduct("3", "Java Book", Category.BOOKS, 10);

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

        Product electronisProduct = Warehouse.createProduct("1", "Laptop", Category.ELECTRONICS, 8);
        warehouse.addProduct(electronisProduct);

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

        Product oldProduct = Warehouse.createProductWithDate("1", "Old Product", Category.BOOKS, 6,
                baseDate.minusDays(2)); // Created 2 days before baseDate (2025-08-31)
        Product newProduct = Warehouse.createProductWithDate("2", "New Product", Category.ELECTRONICS, 8,
                baseDate.plusDays(2)); // Created 2 days after baseDate (2025-09-04)

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(newProduct);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(baseDate); // Retrieve products created after baseDate (2025-09-02)

        assertEquals(1, recentProducts.size()); // Verify that there is 1 product created after the specified date
        assertEquals("New Product", recentProducts.getFirst().name()); // Verify that the product is "New Product"
    }
}
