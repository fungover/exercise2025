package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;
    private ProductRepository productRepository;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
        testProduct = new Product.Builder()
            .id("1")
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();
    }

    @Test
    void addProduct_Success() {
        // When
        productService.addProduct(testProduct);

        // Then
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("iPhone 15", products.get(0).name());
    }

    @Test
    void addProduct_NullProduct_ThrowsException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(null);
        });
    }

    @Test
    void addProduct_EmptyProductId_ThrowsException() {
        // Given
        Product productWithEmptyId = new Product.Builder()
            .id("   ") // Whitespace-only ID
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(productWithEmptyId);
        });
        assertTrue(exception.getMessage().contains("Product ID cannot be empty"));
    }

    @Test
    void addProduct_NullProductId_ThrowsException() {
        // When & Then - Product Builder validation catches null ID
        assertThrows(NullPointerException.class, () -> {
            new Product.Builder()
                .id(null)
                .name("iPhone 15")
                .category(Category.ELECTRONICS)
                .rating(9)
                .price(new BigDecimal("999.99"))
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();
        });
    }

    @Test
    void addProduct_DuplicateId_ThrowsException() {
        // Given
        productService.addProduct(testProduct);
        Product identicalProduct = new Product.Builder()
            .id("1")
            .name("iPhone 15")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(testProduct.createdDate())
            .modifiedDate(testProduct.modifiedDate())
            .build();

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.addProduct(identicalProduct);
        });
        assertTrue(exception.getMessage().contains("already exists"));

        // Verify original product is unchanged
        assertEquals("iPhone 15", productService.getProductById("1").get().name());
    }

    @Test
    void getProductById_Success() {
        // Given
        productService.addProduct(testProduct);

        // When
        Optional<Product> found = productService.getProductById("1");

        // Then
        assertTrue(found.isPresent());
        assertEquals("iPhone 15", found.get().name());
    }

    @Test
    void getProductById_NotFound() {
        // When
        Optional<Product> found = productService.getProductById("999");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void getAllProducts_EmptyRepository() {
        // When
        List<Product> products = productService.getAllProducts();

        // Then
        assertTrue(products.isEmpty());
    }

    // VG Distinction Method Tests

    @Test
    void getCategoriesWithProducts_Success() {
        // Given
        Product electronics = new Product.Builder()
            .id("1")
            .name("iPhone")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product food = new Product.Builder()
            .id("2")
            .name("Apple")
            .category(Category.FOOD)
            .rating(8)
            .price(new BigDecimal("1.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product anotherElectronics = new Product.Builder()
            .id("3")
            .name("iPad")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("799.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        productService.addProduct(electronics);
        productService.addProduct(food);
        productService.addProduct(anotherElectronics);

        // When
        List<Category> categories = productService.getCategoriesWithProducts();

        // Then
        assertEquals(2, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.FOOD));
    }

    @Test
    void getCategoriesWithProducts_EmptyRepository() {
        // When
        List<Category> categories = productService.getCategoriesWithProducts();

        // Then
        assertTrue(categories.isEmpty());
    }

    @Test
    void countProductsInCategory_Success() {
        // Given
        Product electronics1 = new Product.Builder()
            .id("1")
            .name("iPhone")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product electronics2 = new Product.Builder()
            .id("2")
            .name("iPad")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("799.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product food = new Product.Builder()
            .id("3")
            .name("Apple")
            .category(Category.FOOD)
            .rating(8)
            .price(new BigDecimal("1.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        productService.addProduct(electronics1);
        productService.addProduct(electronics2);
        productService.addProduct(food);

        // When
        long electronicsCount = productService.countProductsInCategory(Category.ELECTRONICS);
        long foodCount = productService.countProductsInCategory(Category.FOOD);
        long carsCount = productService.countProductsInCategory(Category.CARS);

        // Then
        assertEquals(2, electronicsCount);
        assertEquals(1, foodCount);
        assertEquals(0, carsCount);
    }

    @Test
    void countProductsInCategory_NullCategory() {
        // When
        long count = productService.countProductsInCategory(null);

        // Then
        assertEquals(0, count);
    }

    @Test
    void getProductInitialsMap_Success() {
        // Given
        Product iPhone = new Product.Builder()
            .id("1")
            .name("iPhone")
            .category(Category.ELECTRONICS)
            .rating(9)
            .price(new BigDecimal("999.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product iPad = new Product.Builder()
            .id("2")
            .name("iPad")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("799.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product apple = new Product.Builder()
            .id("3")
            .name("Apple")
            .category(Category.FOOD)
            .rating(8)
            .price(new BigDecimal("1.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        Product banana = new Product.Builder()
            .id("4")
            .name("Banana")
            .category(Category.FOOD)
            .rating(7)
            .price(new BigDecimal("0.99"))
            .createdDate(LocalDate.now())
            .modifiedDate(LocalDate.now())
            .build();

        productService.addProduct(iPhone);
        productService.addProduct(iPad);
        productService.addProduct(apple);
        productService.addProduct(banana);

        // When
        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        // Then
        assertEquals(3, initialsMap.size());
        assertEquals(2, initialsMap.get('I'));
        assertEquals(1, initialsMap.get('A'));
        assertEquals(1, initialsMap.get('B'));
    }

    @Test
    void getProductInitialsMap_EmptyRepository() {
        // When
        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        // Then
        assertTrue(initialsMap.isEmpty());
    }

    @Test
    void getTopRatedProductsThisMonth_Success() {
        // Given
        LocalDate today = LocalDate.now();
        Product maxRated1 = new Product.Builder()
            .id("1")
            .name("iPhone")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("999.99"))
            .createdDate(today)
            .modifiedDate(today)
            .build();

        Product maxRated2 = new Product.Builder()
            .id("2")
            .name("iPad")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("799.99"))
            .createdDate(today.minusDays(1))
            .modifiedDate(today.minusDays(1))
            .build();

        Product lowerRated = new Product.Builder()
            .id("3")
            .name("Apple")
            .category(Category.FOOD)
            .rating(8)
            .price(new BigDecimal("1.99"))
            .createdDate(today)
            .modifiedDate(today)
            .build();

        Product oldProduct = new Product.Builder()
            .id("4")
            .name("Old Phone")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("499.99"))
            .createdDate(today.minusMonths(2))
            .modifiedDate(today.minusMonths(2))
            .build();

        productService.addProduct(maxRated1);
        productService.addProduct(maxRated2);
        productService.addProduct(lowerRated);
        productService.addProduct(oldProduct);

        // When
        List<Product> topRated = productService.getTopRatedProductsThisMonth();

        // Then
        assertEquals(2, topRated.size());
        assertEquals("iPhone", topRated.get(0).name());
        assertEquals("iPad", topRated.get(1).name());
        assertTrue(topRated.stream().allMatch(p -> p.rating() == 10));
    }

    @Test
    void getTopRatedProductsThisMonth_NoProductsThisMonth() {
        // Given
        Product oldProduct = new Product.Builder()
            .id("1")
            .name("Old Phone")
            .category(Category.ELECTRONICS)
            .rating(10)
            .price(new BigDecimal("499.99"))
            .createdDate(LocalDate.now().minusMonths(2))
            .modifiedDate(LocalDate.now().minusMonths(2))
            .build();

        productService.addProduct(oldProduct);

        // When
        List<Product> topRated = productService.getTopRatedProductsThisMonth();

        // Then
        assertTrue(topRated.isEmpty());
    }
}