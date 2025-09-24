package service;

import entities.Category;
import entities.Product;
import repository.InMemoryProductRepository;
import repository.ProductRepository;
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

class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private Product sampleProduct;

    // Runs before each test to set up test data ensuring each test starts with a clean environment
    @BeforeEach
    void setUp() {
        // Skapa repository och service med dependency injection
        productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);

        // Använd Builder Pattern för att skapa testprodukt
        sampleProduct = new Product.Builder()
                .id("1")
                .name("iPhone 15")
                .category(Category.ELECTRONICS)
                .rating(9)
                .asNewProduct()
                .build();
    }

    // ========== TESTS FOR addProduct() ==========

    @Test
    @DisplayName("Should successfully add a valid product")
    void addProduct_ValidProduct_Success() {
        productService.addProduct(sampleProduct);

        Optional<Product> retrievedProduct = productService.getProductById("1");
        assertTrue(retrievedProduct.isPresent());
        assertEquals(sampleProduct, retrievedProduct.get());
    }

    @Test
    @DisplayName("Should throw exception when adding null product")
    void addProduct_NullProduct_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct(null)
        );
        assertEquals("Product cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when adding duplicate product ID")
    void addProduct_DuplicateId_ThrowsException() {
        productService.addProduct(sampleProduct);

        Product duplicateProduct = new Product.Builder()
                .id("1")  // Samma ID
                .name("Galaxy S24")
                .category(Category.ELECTRONICS)
                .rating(8)
                .asNewProduct()
                .build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.addProduct(duplicateProduct)
        );
        assertTrue(exception.getMessage().contains("already exists"));
    }

    // ========== TESTS FOR addNewProduct() convenience method ==========

    @Test
    @DisplayName("Should successfully add new product using convenience method")
    void addNewProduct_ValidParameters_Success() {
        productService.addNewProduct("CONV001", "Convenience Product", Category.BOOKS, 7);

        Optional<Product> retrievedProduct = productService.getProductById("CONV001");
        assertTrue(retrievedProduct.isPresent());
        assertEquals("Convenience Product", retrievedProduct.get().name());
        assertEquals(Category.BOOKS, retrievedProduct.get().category());
        assertEquals(7, retrievedProduct.get().rating());
        assertFalse(retrievedProduct.get().isModified()); // Ny produkt ska inte vara modifierad
    }

    // ========== TESTS FOR updateProduct() ==========

    @Test
    @DisplayName("Should successfully update existing product")
    void updateProduct_ExistingProduct_Success() {
        productService.addProduct(sampleProduct);

        productService.updateProduct("1", "iPhone 15 Pro", Category.ELECTRONICS, 10);

        Optional<Product> updated = productService.getProductById("1");
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
                () -> productService.updateProduct("999", "New Name", Category.BOOKS, 5)
        );
        assertTrue(exception.getMessage().contains("not found"));
    }

    // ========== TESTS FOR getAllProducts() ==========

    @Test
    @DisplayName("Should return empty list when no products exist")
    void getAllProducts_NoProducts_ReturnsEmptyList() {
        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    @DisplayName("Should return all products when products exist")
    void getAllProducts_ProductsExist_ReturnsAllProducts() {
        Product product2 = new Product.Builder()
                .id("2")
                .name("MacBook")
                .category(Category.ELECTRONICS)
                .rating(8)
                .asNewProduct()
                .build();

        productService.addProduct(sampleProduct);
        productService.addProduct(product2);

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertTrue(products.contains(sampleProduct));
        assertTrue(products.contains(product2));
    }

    // ========== TESTS FOR getProductById() ==========

    @Test
    @DisplayName("Should return product when ID exists")
    void getProductById_ExistingId_ReturnsProduct() {
        productService.addProduct(sampleProduct);

        Optional<Product> result = productService.getProductById("1");

        assertTrue(result.isPresent());
        assertEquals(sampleProduct, result.get());
    }

    @Test
    @DisplayName("Should return empty Optional when ID does not exist")
    void getProductById_NonExistingId_ReturnsEmpty() {
        Optional<Product> result = productService.getProductById("999");

        assertFalse(result.isPresent());
    }

    // ========== TESTS FOR getProductsByCategorySorted() ==========

    @Test
    @DisplayName("Should return products in category sorted by name")
    void getProductsByCategorySorted_MultipleProducts_ReturnsSortedByName() {

        Product zebra = new Product.Builder()
                .id("3")
                .name("Zebra Pen")
                .category(Category.TOYS)
                .rating(6)
                .asNewProduct()
                .build();

        Product apple = new Product.Builder()
                .id("4")
                .name("Apple Toy")
                .category(Category.TOYS)
                .rating(7)
                .asNewProduct()
                .build();

        Product book = new Product.Builder()
                .id("5")
                .name("Java Book")
                .category(Category.BOOKS)
                .rating(9)
                .asNewProduct()
                .build();

        productService.addProduct(zebra);
        productService.addProduct(sampleProduct); // ELECTRONICS
        productService.addProduct(apple);
        productService.addProduct(book);

        List<Product> toysProducts = productService.getProductsByCategorySorted(Category.TOYS);

        assertEquals(2, toysProducts.size());
        assertEquals("Apple Toy", toysProducts.get(0).name()); // Ska komma först (A före Z)
        assertEquals("Zebra Pen", toysProducts.get(1).name());
    }

    @Test
    @DisplayName("Should return empty list when no products in category")
    void getProductsByCategorySorted_NoProductsInCategory_ReturnsEmptyList() {
        productService.addProduct(sampleProduct); // ELECTRONICS

        List<Product> foodProducts = productService.getProductsByCategorySorted(Category.FOOD);

        assertTrue(foodProducts.isEmpty());
    }

    // ========== TESTS FOR getProductsCreatedAfter() ==========

    @Test
    @DisplayName("Should return products created after specified date")
    void getProductsCreatedAfter_ValidDate_ReturnsFilteredProducts() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        productService.addProduct(sampleProduct); // Created "today"

        List<Product> recentProducts = productService.getProductsCreatedAfter(yesterday);

        assertEquals(1, recentProducts.size());
        assertTrue(recentProducts.contains(sampleProduct));
    }

    @Test
    @DisplayName("Should return empty list when no products created after date")
    void getProductsCreatedAfter_NoProductsAfterDate_ReturnsEmptyList() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        productService.addProduct(sampleProduct);

        List<Product> futureProducts = productService.getProductsCreatedAfter(tomorrow);

        assertTrue(futureProducts.isEmpty());
    }

    // ========== TESTS FOR getModifiedProducts() ==========

    @Test
    @DisplayName("Should return only modified products")
    void getModifiedProducts_MixOfModifiedAndUnmodified_ReturnsOnlyModified() {
        Product unmodified = new Product.Builder()
                .id("2")
                .name("Unmodified Product")
                .category(Category.BOOKS)
                .rating(7)
                .asNewProduct()
                .build();

        productService.addProduct(sampleProduct);
        productService.addProduct(unmodified);

        // Modify a product
        productService.updateProduct("1", "Modified iPhone", Category.ELECTRONICS, 10);

        List<Product> modifiedProducts = productService.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertEquals("Modified iPhone", modifiedProducts.get(0).name());
        assertTrue(modifiedProducts.get(0).isModified());
    }

    @Test
    @DisplayName("Should return empty list when no products are modified")
    void getModifiedProducts_NoModifiedProducts_ReturnsEmptyList() {
        productService.addProduct(sampleProduct); // Inte modifierad

        List<Product> modifiedProducts = productService.getModifiedProducts();

        assertTrue(modifiedProducts.isEmpty());
    }

    // ========== TESTS FOR VG-METODER ==========

    @Test
    @DisplayName("Should return all categories that have products")
    void getCategoriesWithProducts_MultipleCategories_ReturnsAllRepresentedCategories() {
        Product electronics1 = new Product.Builder()
                .id("2").name("iPhone").category(Category.ELECTRONICS).rating(9).asNewProduct().build();
        Product electronics2 = new Product.Builder()
                .id("3").name("iPad").category(Category.ELECTRONICS).rating(8).asNewProduct().build();
        Product clothing = new Product.Builder()
                .id("4").name("Jeans").category(Category.CLOTHING).rating(7).asNewProduct().build();
        Product books = new Product.Builder()
                .id("5").name("Java Book").category(Category.BOOKS).rating(9).asNewProduct().build();

        productService.addProduct(electronics1);
        productService.addProduct(electronics2); // Same category as electronics1
        productService.addProduct(clothing);
        productService.addProduct(books);

        Set<Category> categories = productService.getCategoriesWithProducts();

        assertEquals(3, categories.size()); // Bara 3 unika kategorier
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.CLOTHING));
        assertTrue(categories.contains(Category.BOOKS));
        assertFalse(categories.contains(Category.FOOD)); // Ingen produkt i denna kategori
    }

    @Test
    @DisplayName("Should return empty set when no products exist")
    void getCategoriesWithProducts_NoProducts_ReturnsEmptySet() {
        Set<Category> categories = productService.getCategoriesWithProducts();

        assertTrue(categories.isEmpty());
    }

    @Test
    @DisplayName("Should count products in specific category correctly")
    void countProductsInCategory_MultipleProductsInCategory_ReturnsCorrectCount() {
        Product electronics1 = new Product.Builder()
                .id("2").name("iPhone").category(Category.ELECTRONICS).rating(9).asNewProduct().build();
        Product electronics2 = new Product.Builder()
                .id("3").name("iPad").category(Category.ELECTRONICS).rating(8).asNewProduct().build();
        Product electronics3 = new Product.Builder()
                .id("4").name("MacBook").category(Category.ELECTRONICS).rating(10).asNewProduct().build();
        Product clothing = new Product.Builder()
                .id("5").name("Jeans").category(Category.CLOTHING).rating(7).asNewProduct().build();

        productService.addProduct(electronics1);
        productService.addProduct(electronics2);
        productService.addProduct(electronics3);
        productService.addProduct(clothing);

        long electronicsCount = productService.countProductsInCategory(Category.ELECTRONICS);
        long clothingCount = productService.countProductsInCategory(Category.CLOTHING);
        long foodCount = productService.countProductsInCategory(Category.FOOD);

        assertEquals(3, electronicsCount);
        assertEquals(1, clothingCount);
        assertEquals(0, foodCount); // Inga produkter i FOOD-kategorin
    }

    @Test
    @DisplayName("Should throw exception when counting products with null category")
    void countProductsInCategory_NullCategory_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> productService.countProductsInCategory(null)
        );
        assertEquals("Category cannot be null", exception.getMessage());
    }

    @Test
    @DisplayName("Should create map of product name initials and their counts")
    void getProductInitialsMap_MultipleProducts_ReturnsCorrectInitialsCount() {
        Product iPhone = new Product.Builder()
                .id("2").name("iPhone").category(Category.ELECTRONICS).rating(9).asNewProduct().build();
        Product iPad = new Product.Builder()
                .id("3").name("iPad").category(Category.ELECTRONICS).rating(8).asNewProduct().build();
        Product android = new Product.Builder()
                .id("4").name("Android Phone").category(Category.ELECTRONICS).rating(7).asNewProduct().build();
        Product book = new Product.Builder()
                .id("5").name("Book").category(Category.BOOKS).rating(6).asNewProduct().build();
        Product apple = new Product.Builder()
                .id("6").name("Apple Watch").category(Category.ELECTRONICS).rating(8).asNewProduct().build();

        productService.addProduct(iPhone);     // I
        productService.addProduct(iPad);       // I
        productService.addProduct(android);    // A
        productService.addProduct(book);       // B
        productService.addProduct(apple);      // A

        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        assertEquals(3, initialsMap.size()); // 3 unika bokstäver: I, A, B
        assertEquals(Integer.valueOf(2), initialsMap.get('I')); // iPhone + iPad
        assertEquals(Integer.valueOf(2), initialsMap.get('A')); // Android + Apple Watch
        assertEquals(Integer.valueOf(1), initialsMap.get('B')); // Book
        assertNull(initialsMap.get('C')); // No product starts with C
    }

    @Test
    @DisplayName("Should return empty map when no products exist")
    void getProductInitialsMap_NoProducts_ReturnsEmptyMap() {
        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        assertTrue(initialsMap.isEmpty());
    }

    @Test
    @DisplayName("Should handle case insensitivity correctly")
    void getProductInitialsMap_MixedCase_GroupsCorrectly() {
        Product apple1 = new Product.Builder()
                .id("2").name("apple").category(Category.FOOD).rating(5).asNewProduct().build();
        Product apple2 = new Product.Builder()
                .id("3").name("Apple Watch").category(Category.ELECTRONICS).rating(8).asNewProduct().build();

        productService.addProduct(apple1);
        productService.addProduct(apple2);

        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        // Both should be grouped under 'A' (Case insensitive)
        assertEquals(1, initialsMap.size());
        assertEquals(Integer.valueOf(2), initialsMap.get('A')); // Båda produkter under 'A'
    }

    @Test
    @DisplayName("Should return top rated products from this month sorted by newest first")
    void getTopRatedProductsThisMonth_WithVariousRatings_ReturnsTopRatedNewestFirst() {
        // Create products with different dates and ratings this month using Builder
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1);
        LocalDateTime midMonth = now.withDayOfMonth(15);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth());

        // Products this month with different ratings
        Product product1 = new Product.Builder()
                .id("2").name("iPhone").category(Category.ELECTRONICS).rating(9)
                .createdDate(startOfMonth).modifiedDate(startOfMonth).build();
        Product product2 = new Product.Builder()
                .id("3").name("iPad").category(Category.ELECTRONICS).rating(10)
                .createdDate(midMonth).modifiedDate(midMonth).build();
        Product product3 = new Product.Builder()
                .id("4").name("MacBook").category(Category.ELECTRONICS).rating(8)
                .createdDate(midMonth.plusDays(1)).modifiedDate(midMonth.plusDays(1)).build();
        Product product4 = new Product.Builder()
                .id("5").name("AirPods").category(Category.ELECTRONICS).rating(10)
                .createdDate(endOfMonth).modifiedDate(endOfMonth).build();

        // Product from last month not to be included
        Product oldProduct = new Product.Builder()
                .id("6").name("Old Phone").category(Category.ELECTRONICS).rating(10)
                .createdDate(now.minusMonths(1)).modifiedDate(now.minusMonths(1)).build();

        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(product4);
        productService.addProduct(oldProduct);

        List<Product> topProducts = productService.getTopRatedProductsThisMonth();

        assertEquals(2, topProducts.size()); // Bara produkter med betyg 10 (max för denna månad)
        assertEquals("AirPods", topProducts.get(0).name());  // Nyaste först (slutet av månaden)
        assertEquals("iPad", topProducts.get(1).name());     // Äldre (mitten av månaden)

        // Verify all returned products have max rating
        assertTrue(topProducts.stream().allMatch(product -> product.rating() == 10));

        // Check sorting (newest first)
        for (int i = 0; i < topProducts.size() - 1; i++) {
            assertTrue(topProducts.get(i).createdDate().isAfter(topProducts.get(i + 1).createdDate()));
        }
    }

    @Test
    @DisplayName("Should return empty list when no products exist at all")
    void getTopRatedProductsThisMonth_NoProducts_ReturnsEmptyList() {
        List<Product> topProducts = productService.getTopRatedProductsThisMonth();

        assertTrue(topProducts.isEmpty());
    }

    // ========== ADDITIONAL TESTS FOR NEW METHODS ==========

    @Test
    @DisplayName("Should return high quality products above threshold")
    void getHighQualityProducts_WithThreshold_ReturnsFilteredProducts() {
        Product highQuality1 = new Product.Builder()
                .id("HQ1").name("Premium Product").category(Category.ELECTRONICS).rating(9).asNewProduct().build();
        Product highQuality2 = new Product.Builder()
                .id("HQ2").name("Excellent Product").category(Category.BOOKS).rating(10).asNewProduct().build();
        Product lowQuality = new Product.Builder()
                .id("LQ1").name("Basic Product").category(Category.TOYS).rating(6).asNewProduct().build();

        productService.addProduct(highQuality1);
        productService.addProduct(highQuality2);
        productService.addProduct(lowQuality);

        List<Product> highQualityProducts = productService.getHighQualityProducts(8);

        assertEquals(2, highQualityProducts.size());
        assertTrue(highQualityProducts.stream().allMatch(p -> p.rating() >= 8));
        // Should be sorted by rating descending
        assertEquals("Excellent Product", highQualityProducts.get(0).name()); // Rating 10 först
        assertEquals("Premium Product", highQualityProducts.get(1).name());   // Rating 9 sedan
    }

    @Test
    @DisplayName("Should check if products exist in category")
    void hasProductsInCategory_WithAndWithoutProducts_ReturnsCorrectBoolean() {
        Product electronics = new Product.Builder()
                .id("E1").name("Laptop").category(Category.ELECTRONICS).rating(8).asNewProduct().build();

        productService.addProduct(electronics);

        assertTrue(productService.hasProductsInCategory(Category.ELECTRONICS));
        assertFalse(productService.hasProductsInCategory(Category.FOOD));
    }

    // ========== DEPENDENCY INJECTION TEST ==========

    @Test
    @DisplayName("Should throw exception when ProductRepository is null")
    void constructor_NullRepository_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ProductService(null)
        );
        assertEquals("ProductRepository cannot be null", exception.getMessage());
    }
}