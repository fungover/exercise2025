package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Product product;

    @BeforeEach
    void setUp() {
        ProductRepository productRepository = new InMemoryProductRepository();
        warehouse = new Warehouse(productRepository);
    }

    @Test
    void shouldCreateEmptyWarehouse() {
        ProductRepository productRepository = new InMemoryProductRepository();
        Warehouse warehouse = new Warehouse(productRepository);
        assertNotNull(warehouse);
    }

    @Test
    void shouldAddProduct() {
        Product product = Product.builder()
                .id("1")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(2)
                .createdDate(LocalDate.now())
                .build();

        warehouse.addProduct(product);
        assertDoesNotThrow(() -> warehouse.addProduct(product));
    }

    @Test
    void shouldRemoveProduct() {
        product = Product.builder()
                .id("123")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(2)
                .createdDate(LocalDate.now())
                .build();

        warehouse.addProduct(product);
        assertEquals(1, warehouse.getAllProducts().size());
        warehouse.removeProduct(product);
        assertEquals(0, warehouse.getAllProducts().size());
    }

    @Test
    void shouldStoreAddedProduct() {
        product = Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build();

        warehouse.addProduct(product);

        List<Product> products = warehouse.getAllProducts();
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
                .build();

        warehouse.addProduct(product);

        Optional<Product> foundProduct = warehouse.getProductById("213");

        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductNotFound() {
        Optional<Product> foundProduct = warehouse.getProductById("999");

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
                .build();

        Product electronics2 = Product.builder()
                .id("2")
                .name("Apple iMac G3")
                .category(Category.ELECTRONICS)
                .rating(2)
                .createdDate(LocalDate.now())
                .build();

        Product sports = Product.builder()
                .id("3")
                .name("Football - World Cup 1994")
                .category(Category.SPORTS)
                .rating(10)
                .createdDate(LocalDate.now())
                .build();

        warehouse.addProduct(electronics1);
        warehouse.addProduct(electronics2);
        warehouse.addProduct(sports);

        List<Product> electronicsProducts = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);

        assertEquals(2, electronicsProducts.size());
        assertEquals("Apple iMac G3", electronicsProducts.get(0).name());
        assertEquals("Nokia 3310", electronicsProducts.get(1).name());
    }

    @Test
    void shouldReturnEmptyListWhenNoCategoryMatches() {
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        List<Product> result = warehouse.getProductsByCategorySorted(Category.SPORTS);

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
                .build();

        Product newProduct1 = Product.builder()
                .id("2")
                .name("New Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.of(2025, 9, 10))
                .build();

        Product newProduct2 = Product.builder()
                .id("3")
                .name("New Product 2")
                .category(Category.SPORTS)
                .rating(5)
                .createdDate(LocalDate.of(2025, 9, 15))
                .build();

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(newProduct1);
        warehouse.addProduct(newProduct2);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(cutoffDate);

        assertEquals(2, recentProducts.size());
        assertTrue(recentProducts.contains(newProduct1));
        assertTrue(recentProducts.contains(newProduct2));
        assertFalse(recentProducts.contains(oldProduct));
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsFoundAfterDate() {
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.of(2025, 1, 1))
                .build());

        List<Product> result = warehouse.getProductsCreatedAfter(LocalDate.of(2099, 12, 10));

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
                .build();

        Product modifiedProduct = Product.builder()
                .id("2")
                .name("Modified")
                .category(Category.SPORTS)
                .rating(7)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .build();

        warehouse.addProduct(unmodifiedProduct);
        warehouse.addProduct(modifiedProduct);

        List<Product> modifiedProducts = warehouse.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertTrue(modifiedProducts.contains(modifiedProduct));
        assertFalse(modifiedProducts.contains(unmodifiedProduct));
    }

    @Test
    void shouldReturnEmptyListWhenNoModifiedProducts() {
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.of(2025, 1, 1))
                .build());

        List<Product> result = warehouse.getModifiedProducts();

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
                .build();

        warehouse.addProduct(originalProduct);

        warehouse.updateProduct("1", "New Product", Category.SPORTS, 5);

        Optional<Product> updated = warehouse.getProductById("1");

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
                warehouse.updateProduct("999", "Name", Category.ELECTRONICS, 5));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProductWithEmptyName() {
        Product product = Product.builder()
                .id("1")
                .name("Test product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build();

        warehouse.addProduct(product);

        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("1", "", Category.ELECTRONICS, 5));
    }

    @Test
    void shouldCountNumberOfProductsInCategory() {
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Test Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("2")
                .name("Test Product 2")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("3")
                .name("Test Product 3")
                .category(Category.SPORTS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        int electronicsCount = warehouse.countProductsInCategory(Category.ELECTRONICS);
        int sportsCount = warehouse.countProductsInCategory(Category.SPORTS);
        int clothingCount = warehouse.countProductsInCategory(Category.CLOTHING);

        assertEquals(2, electronicsCount);
        assertEquals(1, sportsCount);
        assertEquals(0, clothingCount);
    }

    @Test
    void shouldGetCategoriesWithProductsIn() {
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Test Product 1")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("2")
                .name("Test Product 2")
                .category(Category.ELECTRONICS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("3")
                .name("Test Product 3")
                .category(Category.SPORTS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        Set<Category> categories = warehouse.getCategoriesWithProducts();

        assertEquals(2, categories.size());
        assertTrue(categories.contains(Category.ELECTRONICS));
        assertTrue(categories.contains(Category.SPORTS));
        assertFalse(categories.contains(Category.CLOTHING));
    }

    @Test
    void shouldGetProductsInitialMap() {
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Mobile phone")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("2")
                .name("TV 49-inch")
                .category(Category.ELECTRONICS)
                .rating(6)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("3")
                .name("Basketball")
                .category(Category.SPORTS)
                .rating(8)
                .createdDate(LocalDate.now())
                .build());

        warehouse.addProduct(Product.builder()
                .id("4")
                .name("Blue T-shirt")
                .category(Category.CLOTHING)
                .rating(2)
                .createdDate(LocalDate.now())
                .build());

        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();

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
                .build();

        Product product2 = Product.builder()
                .id("2")
                .name("Test product 2")
                .category(Category.CLOTHING)
                .rating(5)
                .createdDate(thisMonth.minusDays(2))
                .build();

        Product product3 = Product.builder()
                .id("3")
                .name("Test product 3")
                .category(Category.SPORTS)
                .rating(10)
                .createdDate(thisMonth.minusDays(1))
                .build();

        Product oldProduct = Product.builder()
                .id("4")
                .name("Test product 4")
                .category(Category.ELECTRONICS)
                .rating(9)
                .createdDate(lastMonth)
                .build();

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(oldProduct);

        List<Product> topRated = warehouse.getTopRatedProductsThisMonth();

        assertEquals(2, topRated.size());
        assertEquals(product3, topRated.get(0));
        assertEquals(product1, topRated.get(1));
    }

    @Test
    void shouldReturnEmptyListWhenNoTopRatedProductsThisMonth() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        warehouse.addProduct(Product.builder()
                .id("1")
                .name("Old Product")
                .category(Category.ELECTRONICS)
                .rating(3)
                .createdDate(lastMonth)
                .build());

        List<Product> topRated = warehouse.getTopRatedProductsThisMonth();

        assertTrue(topRated.isEmpty());
    }
}
