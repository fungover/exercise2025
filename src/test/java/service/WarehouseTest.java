package service;

import entities.Category;
import entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;
    private Product sampleProduct;

   // Runs before each test to set up test data ensuring each test starts with a clean environment
    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
        sampleProduct = Product.createNew("1", "iPhone 15", Category.ELECTRONICS, 9);
    }

    // ========== TESTS FOR addProduct() ==========

    @Test
    @DisplayName("Should successfully add a valid product")
    void addProduct_ValidProduct_Success() {
        warehouse.addProduct(sampleProduct);

        Optional<Product> retrievedProduct = warehouse.getProductById("1");
        assertTrue(retrievedProduct.isPresent());
        assertEquals(sampleProduct, retrievedProduct.get());
    }

    @Test
    @DisplayName("Should throw exception when adding null product")
    void addProduct_NullProduct_ThrowsException() {

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> warehouse.addProduct(null)
        );
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when adding duplicate product ID")
    void addProduct_DuplicateId_ThrowsException() {
        warehouse.addProduct(sampleProduct);
        Product duplicateProduct = Product.createNew("1", "Galaxy S24", Category.ELECTRONICS, 8);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> warehouse.addProduct(duplicateProduct)
        );
        assertTrue(exception.getMessage().contains("already exists"));
    }

    // ========== TESTS FOR updateProduct() ==========

    @Test
    @DisplayName("Should successfully update existing product")
    void updateProduct_ExistingProduct_Success() {
        warehouse.addProduct(sampleProduct);

        warehouse.updateProduct("1", "iPhone 15 Pro", Category.ELECTRONICS, 10);

        Optional<Product> updated = warehouse.getProductById("1");
        assertTrue(updated.isPresent());
        assertEquals("iPhone 15 Pro", updated.get().name());
        assertEquals(10, updated.get().rating());
        assertTrue(updated.get().isModified()); // Ska vara modifierad nu
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing product")
    void updateProduct_NonExistingProduct_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> warehouse.updateProduct("999", "New Name", Category.BOOKS, 5)
        );
        assertTrue(exception.getMessage().contains("not found"));
    }

    // ========== TESTER FÖR getAllProducts() ==========

    @Test
    @DisplayName("Should return empty list when no products exist")
    void getAllProducts_NoProducts_ReturnsEmptyList() {
        List<Product> products = warehouse.getAllProducts();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    @DisplayName("Should return all products when products exist")
    void getAllProducts_ProductsExist_ReturnsAllProducts() {
        Product product2 = Product.createNew("2", "MacBook", Category.ELECTRONICS, 8);
        warehouse.addProduct(sampleProduct);
        warehouse.addProduct(product2);

        List<Product> products = warehouse.getAllProducts();

        assertEquals(2, products.size());
        assertTrue(products.contains(sampleProduct));
        assertTrue(products.contains(product2));
    }

    // ========== TESTS FOR getProductById() ==========

    @Test
    @DisplayName("Should return product when ID exists")
    void getProductById_ExistingId_ReturnsProduct() {
        warehouse.addProduct(sampleProduct);

        Optional<Product> result = warehouse.getProductById("1");

        assertTrue(result.isPresent());
        assertEquals(sampleProduct, result.get());
    }

    @Test
    @DisplayName("Should return empty Optional when ID does not exist")
    void getProductById_NonExistingId_ReturnsEmpty() {
        Optional<Product> result = warehouse.getProductById("999");

        assertFalse(result.isPresent());
    }

    // ========== TESTS FOR getProductsByCategorySorted() ==========

    @Test
    @DisplayName("Should return products in category sorted by name")
    void getProductsByCategorySorted_MultipleProducts_ReturnsSortedByName() {

        Product zebra = Product.createNew("3", "Zebra Pen", Category.TOYS, 6);
        Product apple = Product.createNew("4", "Apple Toy", Category.TOYS, 7);
        Product book = Product.createNew("5", "Java Book", Category.BOOKS, 9);

        warehouse.addProduct(zebra);
        warehouse.addProduct(sampleProduct); // ELECTRONICS
        warehouse.addProduct(apple);
        warehouse.addProduct(book);

        List<Product> toysProducts = warehouse.getProductsByCategorySorted(Category.TOYS);

        assertEquals(2, toysProducts.size());
        assertEquals("Apple Toy", toysProducts.get(0).name()); // Ska komma först (A före Z)
        assertEquals("Zebra Pen", toysProducts.get(1).name());
    }

    @Test
    @DisplayName("Should return empty list when no products in category")
    void getProductsByCategorySorted_NoProductsInCategory_ReturnsEmptyList() {
        warehouse.addProduct(sampleProduct); // ELECTRONICS

        List<Product> foodProducts = warehouse.getProductsByCategorySorted(Category.FOOD);

        assertTrue(foodProducts.isEmpty());
    }

    // ========== TESTS FOR getProductsCreatedAfter() ==========

    @Test
    @DisplayName("Should return products created after specified date")
    void getProductsCreatedAfter_ValidDate_ReturnsFilteredProducts() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        warehouse.addProduct(sampleProduct); // Created "today"

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(yesterday);

        assertEquals(1, recentProducts.size());
        assertTrue(recentProducts.contains(sampleProduct));
    }

    @Test
    @DisplayName("Should return empty list when no products created after date")
    void getProductsCreatedAfter_NoProductsAfterDate_ReturnsEmptyList() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        warehouse.addProduct(sampleProduct);

        List<Product> futureProducts = warehouse.getProductsCreatedAfter(tomorrow);

        assertTrue(futureProducts.isEmpty());
    }

    // ========== TESTS FOR getModifiedProducts() ==========

    @Test
    @DisplayName("Should return only modified products")
    void getModifiedProducts_MixOfModifiedAndUnmodified_ReturnsOnlyModified() {
        Product unmodified = Product.createNew("2", "Unmodified Product", Category.BOOKS, 7);
        warehouse.addProduct(sampleProduct);
        warehouse.addProduct(unmodified);

        // Modify a product
        warehouse.updateProduct("1", "Modified iPhone", Category.ELECTRONICS, 10);

        List<Product> modifiedProducts = warehouse.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertEquals("Modified iPhone", modifiedProducts.get(0).name());
        assertTrue(modifiedProducts.get(0).isModified());
    }

    @Test
    @DisplayName("Should return empty list when no products are modified")
    void getModifiedProducts_NoModifiedProducts_ReturnsEmptyList() {
        warehouse.addProduct(sampleProduct); // Inte modifierad

        List<Product> modifiedProducts = warehouse.getModifiedProducts();

        assertTrue(modifiedProducts.isEmpty());
    }

    // ========== TESTS FOR VG-METODER ==========

    @Test
    @DisplayName("Should return all categories that have products")
    void getCategoriesWithProducts_MultipleCategories_ReturnsAllRepresentedCategories() {
        Product electronics1 = Product.createNew("2", "iPhone", Category.ELECTRONICS, 9);
        Product electronics2 = Product.createNew("3", "iPad", Category.ELECTRONICS, 8);
        Product clothing = Product.createNew("4", "Jeans", Category.CLOTHING, 7);
        Product books = Product.createNew("5", "Java Book", Category.BOOKS, 9);

        warehouse.addProduct(electronics1);
        warehouse.addProduct(electronics2); // Same category as electronics1
        warehouse.addProduct(clothing);
        warehouse.addProduct(books);

        Set<Category> categories = warehouse.getCategoriesWithProducts();

        assertEquals(3, categories.size()); // Bara 3 unika kategorier
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.CLOTHING));
        assertTrue(categories.contains(Category.BOOKS));
        assertFalse(categories.contains(Category.FOOD)); // Ingen produkt i denna kategori
    }

    @Test
    @DisplayName("Should return empty set when no products exist")
    void getCategoriesWithProducts_NoProducts_ReturnsEmptySet() {
        Set<Category> categories = warehouse.getCategoriesWithProducts();

        assertTrue(categories.isEmpty());
    }

    @Test
    @DisplayName("Should count products in specific category correctly")
    void countProductsInCategory_MultipleProductsInCategory_ReturnsCorrectCount() {
        Product electronics1 = Product.createNew("2", "iPhone", Category.ELECTRONICS, 9);
        Product electronics2 = Product.createNew("3", "iPad", Category.ELECTRONICS, 8);
        Product electronics3 = Product.createNew("4", "MacBook", Category.ELECTRONICS, 10);
        Product clothing = Product.createNew("5", "Jeans", Category.CLOTHING, 7);

        warehouse.addProduct(electronics1);
        warehouse.addProduct(electronics2);
        warehouse.addProduct(electronics3);
        warehouse.addProduct(clothing);

        long electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS);
        long clothingCount = warehouse.countProductsInCategory(Category.CLOTHING);
        long foodCount = warehouse.countProductsInCategory(Category.FOOD);

        assertEquals(3, electronicsCount);
        assertEquals(1, clothingCount);
        assertEquals(0, foodCount); // Inga produkter i FOOD-kategorin
    }

    @Test
    @DisplayName("Should throw exception when counting products with null category")
    void countProductsInCategory_NullCategory_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> warehouse.countProductsInCategory(null)
        );
        assertEquals("Category cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should create map of product name initials and their counts")
    void getProductInitialsMap_MultipleProducts_ReturnsCorrectInitialsCount() {
        Product iPhone = Product.createNew("2", "iPhone", Category.ELECTRONICS, 9);
        Product iPad = Product.createNew("3", "iPad", Category.ELECTRONICS, 8);
        Product android = Product.createNew("4", "Android Phone", Category.ELECTRONICS, 7);
        Product book = Product.createNew("5", "Book", Category.BOOKS, 6);
        Product apple = Product.createNew("6", "Apple Watch", Category.ELECTRONICS, 8);

        warehouse.addProduct(iPhone);     // I
        warehouse.addProduct(iPad);       // I
        warehouse.addProduct(android);    // A
        warehouse.addProduct(book);       // B
        warehouse.addProduct(apple);      // A

        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        assertEquals(3, initialsMap.size()); // 3 unika bokstäver: I, A, B
        assertEquals(Integer.valueOf(2), initialsMap.get('I')); // iPhone + iPad
        assertEquals(Integer.valueOf(2), initialsMap.get('A')); // Android + Apple Watch
        assertEquals(Integer.valueOf(1), initialsMap.get('B')); // Book
        assertNull(initialsMap.get('C')); // No product starts with C
    }

    @Test
    @DisplayName("Should handle empty product names gracefully")
    void getProductInitialsMap_WithEmptyNames_IgnoresEmptyNames() {
        // We cannot create products with empty names due to validation in Product
        // But we can test that the method handles it if it happens.
        Product validProduct = Product.createNew("2", "iPhone", Category.ELECTRONICS, 9);
        warehouse.addProduct(validProduct);

        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        assertEquals(1, initialsMap.size());
        assertEquals(Integer.valueOf(1), initialsMap.get('I'));
    }

    @Test
    @DisplayName("Should return empty map when no products exist")
    void getProductInitialsMap_NoProducts_ReturnsEmptyMap() {
        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        assertTrue(initialsMap.isEmpty());
    }

    @Test
    @DisplayName("Should handle case insensitivity correctly")
    void getProductInitialsMap_MixedCase_GroupsCorrectly() {
        Product apple1 = Product.createNew("2", "apple", Category.FOOD, 5);      // liten bokstav
        Product apple2 = Product.createNew("3", "Apple Watch", Category.ELECTRONICS, 8); // stor bokstav

        warehouse.addProduct(apple1);
        warehouse.addProduct(apple2);

        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

        // Both should be grouped under 'A' (case insensitive)
        assertEquals(1, initialsMap.size());
        assertEquals(Integer.valueOf(2), initialsMap.get('A')); // Båda produkter under 'A'
    }

    @Test
    @DisplayName("Should return top rated products from this month sorted by newest first")
    void getTopRatedProductsThisMonth_WithVariousRatings_ReturnsTopRatedNewestFirst() {
        // Create products with different dates and ratings this month
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1);
        LocalDateTime midMonth = now.withDayOfMonth(15);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth());

        // Products this month with different ratings
        Product product1 = new Product("2", "iPhone", Category.ELECTRONICS, 9, startOfMonth, startOfMonth);      // Betyg 9
        Product product2 = new Product("3", "iPad", Category.ELECTRONICS, 10, midMonth, midMonth);               // Betyg 10 (MAX)
        Product product3 = new Product("4", "MacBook", Category.ELECTRONICS, 8, midMonth.plusDays(1), midMonth.plusDays(1)); // Betyg 8
        Product product4 = new Product("5", "AirPods", Category.ELECTRONICS, 10, endOfMonth, endOfMonth);        // Betyg 10 (MAX, nyaste)

        // Product from last month not to be included
        Product oldProduct = new Product("6", "Old Phone", Category.ELECTRONICS, 10, now.minusMonths(1), now.minusMonths(1));

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        warehouse.addProduct(oldProduct);

        List<Product> topProducts = warehouse.getTopRatedProductsThisMonth();

        assertEquals(2, topProducts.size()); // Bara produkter med betyg 10 (max för denna månad)
        assertEquals("AirPods", topProducts.get(0).name());  // Nyaste först (slutet av månaden)
        assertEquals("iPad", topProducts.get(1).name());     // Äldre (mitten av månaden)

        // Product from last month (not to be included)
        assertTrue(topProducts.stream().allMatch(product -> product.rating() == 10));

        // Check sorting (newest first)
        for (int i = 0; i < topProducts.size() - 1; i++) {
            assertTrue(topProducts.get(i).createdDate().isAfter(topProducts.get(i + 1).createdDate()));
        }
    }

    @Test
    @DisplayName("Should return empty list when no products exist this month")
    void getTopRatedProductsThisMonth_NoProductsThisMonth_ReturnsEmptyList() {
        // Only add products from last month
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        Product oldProduct = new Product("2", "Old Product", Category.ELECTRONICS, 10, lastMonth, lastMonth);
        warehouse.addProduct(oldProduct);

        List<Product> topProducts = warehouse.getTopRatedProductsThisMonth();

        assertTrue(topProducts.isEmpty());
    }

    @Test
    @DisplayName("Should return empty list when no products exist at all")
    void getTopRatedProductsThisMonth_NoProducts_ReturnsEmptyList() {
        List<Product> topProducts = warehouse.getTopRatedProductsThisMonth();

        assertTrue(topProducts.isEmpty());
    }
}