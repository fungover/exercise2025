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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldCreateEmptyWarehouse() {
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);
        assertNotNull(productService);
    }

    @Test
    void shouldAddProduct() {
        Product product = Product.builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(2)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build();

        productService.addProduct(product);
        assertDoesNotThrow(() -> productService.addProduct(product));
    }

    @Test
    void shouldRemoveProduct() {
        product = Product.builder()
                .id("123")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(2)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build();

        productService.addProduct(product);
        assertEquals(1, productService.getAllProducts().size());
        productService.removeProduct(product);
        assertEquals(0, productService.getAllProducts().size());
    }

    @Test
    void shouldStoreAddedProduct() {
        product = Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(49.99))
                .build();

        productService.addProduct(product);

        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void shouldFindProductWithId() {
        Product product = Product.builder()
                .id("213")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(29.95))
                .build();

        productService.addProduct(product);

        Optional<Product> foundProduct = productService.getProductById("213");

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFound() {
        Optional<Product> foundProduct = productService.getProductById("999");

        assertTrue(foundProduct.isEmpty());
    }

    @Test
    void shouldGetProductsByCategorySortedByName() {
        Product electronics1 = Product.builder()
                .id("1")
                .name("Nokia 3310")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(200.00))
                .build();

        Product electronics2 = Product.builder()
                .id("2")
                .name("Apple iMac G3")
                .category(Category.ELECTRONICS)
                .rating(2)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(2400.99))
                .build();

        Product sports = Product.builder()
                .id("3")
                .name("Football - World Cup 1994")
                .category(Category.SPORTS)
                .rating(10)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(49.99))
                .build();

        productService.addProduct(electronics1);
        productService.addProduct(electronics2);
        productService.addProduct(sports);

        List<Product> electronicsProducts = productService.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(2, electronicsProducts.size());
        assertEquals("Apple iMac G3", electronicsProducts.get(0).name());
        assertEquals("Nokia 3310", electronicsProducts.get(1).name());
    }

    @Test
    void shouldReturnEmptyListWhenNoCategoryMatches() {
        productService.addProduct(Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build());

        List<Product> result = productService.getProductsByCategorySorted(Category.SPORTS);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetProductsCreatedAfterGivenDate() {
        LocalDate cutoffDate = LocalDate.of(2025, 9, 8);
        Product oldProduct = Product.builder()
                .id("1")
                .name("Old product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.of(2025, 9, 1))
                .price(BigDecimal.valueOf(99.99))
                .build();

        Product newProduct1 = Product.builder()
                .id("2")
                .name("New Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.of(2025, 9, 10))
                .price(BigDecimal.valueOf(49.95))
                .build();

        Product newProduct2 = Product.builder()
                .id("3")
                .name("New Product 2")
                .category(Category.SPORTS)
                .rating(5)
                .createdDate(LocalDate.of(2025, 9, 15))
                .price(BigDecimal.valueOf(100))
                .build();

        productService.addProduct(oldProduct);
        productService.addProduct(newProduct1);
        productService.addProduct(newProduct2);

        List<Product> recentProducts = productService.getProductsCreatedAfter(cutoffDate);

        assertEquals(2, recentProducts.size());
        assertTrue(recentProducts.contains(newProduct1));
        assertTrue(recentProducts.contains(newProduct2));
        assertFalse(recentProducts.contains(oldProduct));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsFoundAfterDate() {
        productService.addProduct(Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.of(2025, 1, 1))
                .price(BigDecimal.valueOf(99.99))
                .build());

        List<Product> result = productService.getProductsCreatedAfter(LocalDate.of(2099, 12, 10));

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldGetModifiedProducts() {
        LocalDate createdDate = LocalDate.of(2025, 9, 8);
        LocalDate modifiedDate = LocalDate.of(2025, 9, 15);

        Product unmodifiedProduct = Product.builder()
                .id("1")
                .name("Unmodified")
                .category(Category.ELECTRONICS)
                .rating(6)
                .createdDate(createdDate)
                .price(BigDecimal.valueOf(100.00))
                .build();

        Product modifiedProduct = Product.builder()
                .id("2")
                .name("Modified")
                .category(Category.SPORTS)
                .rating(7)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .price(BigDecimal.valueOf(99.99))
                .build();

        productService.addProduct(unmodifiedProduct);
        productService.addProduct(modifiedProduct);

        List<Product> modifiedProducts = productService.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertTrue(modifiedProducts.contains(modifiedProduct));
        assertFalse(modifiedProducts.contains(unmodifiedProduct));
    }

    @Test
    void shouldReturnEmptyListWhenNoModifiedProducts() {
        productService.addProduct(Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.of(2025, 1, 1))
                .price(BigDecimal.valueOf(99.99))
                .build());

        List<Product> result = productService.getModifiedProducts();

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateExistingProduct() {
        Product originalProduct = Product.builder()
                .id("1")
                .name("Old Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.of(2025, 9, 1))
                .price(BigDecimal.valueOf(100.00))
                .build();

        productService.addProduct(originalProduct);

        productService.updateProduct("1", "New Product", Category.SPORTS, 5);

        Optional<Product> updated = productService.getProductById("1");

        assertTrue(updated.isPresent());
        assertEquals("New Product", updated.get().name());
        assertEquals(Category.SPORTS, updated.get().category());
        assertEquals(5, updated.get().rating());
        assertEquals(LocalDate.of(2025, 9, 1), updated.get().createdDate());
        assertNotEquals(originalProduct.createdDate(), updated.get().modifiedDate());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        assertThrows(IllegalArgumentException.class, () ->
                productService.updateProduct("999", "Name", Category.ELECTRONICS, 5));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProductWithEmptyName() {
        Product product = Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(999.99))
                .build();

        productService.addProduct(product);

        assertThrows(IllegalArgumentException.class, () ->
                productService.updateProduct("1", "", Category.ELECTRONICS, 5));
    }

    @Test
    void shouldCountNumberOfProductsInCategory() {
        productService.addProduct(Product.builder()
                .id("1")
                .name("Test Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(999.99))
                .build());

        productService.addProduct(Product.builder()
                .id("2")
                .name("Test Product 2")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(99.99))
                .build());

        productService.addProduct(Product.builder()
                .id("3")
                .name("Test Product 3")
                .category(Category.SPORTS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(999.99))
                .build());

        int electronicsCount = productService.countProductsInCategory(Category.ELECTRONICS);
        int sportsCount = productService.countProductsInCategory(Category.SPORTS);
        int clothingCount = productService.countProductsInCategory(Category.CLOTHING);

        assertEquals(2, electronicsCount);
        assertEquals(1, sportsCount);
        assertEquals(0, clothingCount);
    }

    @Test
    void shouldGetCategoriesWithProductsIn() {
        productService.addProduct(Product.builder()
                .id("1")
                .name("Test Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(999.99))
                .build());

        productService.addProduct(Product.builder()
                .id("2")
                .name("Test Product 2")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(49.99))
                .build());

        productService.addProduct(Product.builder()
                .id("3")
                .name("Test Product 3")
                .category(Category.SPORTS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(9999.99))
                .build());

        Set<Category> categories = productService.getCategoriesWithProducts();

        assertEquals(2, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.SPORTS));
        assertFalse(categories.contains(Category.CLOTHING));
    }

    @Test
    void shouldGetProductsInitialMap() {
        productService.addProduct(Product.builder()
                .id("1")
                .name("Mobile phone")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(450.00))
                .build());

        productService.addProduct(Product.builder()
                .id("2")
                .name("TV 49-inch")
                .category(Category.ELECTRONICS)
                .rating(6)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(699.99))
                .build());

        productService.addProduct(Product.builder()
                .id("3")
                .name("Basketball")
                .category(Category.SPORTS)
                .rating(8)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(30))
                .build());

        productService.addProduct(Product.builder()
                .id("4")
                .name("Blue T-shirt")
                .category(Category.CLOTHING)
                .rating(2)
                .createdDate(LocalDate.now())
                .price(BigDecimal.valueOf(95.99))
                .build());

        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();

        assertEquals(3, initialsMap.size());
        assertEquals(1, initialsMap.get('M'));
        assertEquals(1, initialsMap.get('T'));
        assertEquals(2, initialsMap.get('B'));
        assertNull(initialsMap.get('Z'));
    }

    @Test
    void shouldGetTopRatedProductsThisMonth() {
        LocalDate thisMonth = LocalDate.now();
        LocalDate lastMonth = thisMonth.minusMonths(1);

        Product product1 = Product.builder()
                .id("1")
                .name("Test product 1")
                .category(Category.ELECTRONICS)
                .rating(10)
                .createdDate(thisMonth.minusDays(5))
                .price(BigDecimal.valueOf(99.99))
                .build();

        Product product2 = Product.builder()
                .id("2")
                .name("Test product 2")
                .category(Category.CLOTHING)
                .rating(5)
                .createdDate(thisMonth.minusDays(2))
                .price(BigDecimal.valueOf(49.99))
                .build();

        Product product3 = Product.builder()
                .id("3")
                .name("Test product 3")
                .category(Category.SPORTS)
                .rating(10)
                .createdDate(thisMonth.minusDays(1))
                .price(BigDecimal.valueOf(29.99))
                .build();

        Product oldProduct = Product.builder()
                .id("4")
                .name("Test product 4")
                .category(Category.ELECTRONICS)
                .rating(9)
                .createdDate(lastMonth)
                .price(BigDecimal.valueOf(49.99))
                .build();

        productService.addProduct(product1);
        productService.addProduct(product2);
        productService.addProduct(product3);
        productService.addProduct(oldProduct);

        List<Product> topRated = productService.getTopRatedProductsThisMonth();

        assertEquals(2, topRated.size());
        assertEquals(product3, topRated.get(0));
        assertEquals(product1, topRated.get(1));
    }

    @Test
    void shouldReturnEmptyListWhenNoTopRatedProductsThisMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        productService.addProduct(Product.builder()
                .id("1")
                .name("Old Product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(lastMonth)
                .price(BigDecimal.valueOf(29.99))
                .build());

        List<Product> topRated = productService.getTopRatedProductsThisMonth();

        assertTrue(topRated.isEmpty());
    }
}
