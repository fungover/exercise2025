package org.example;

import org.example.entities.CategoryEnum;
import org.example.entities.Product;
import org.example.service.ProductService;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    public ProductService setupWarehouse() {
        ProductService productService = new ProductService();

        Product hammer = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Hammer")
            .category(CategoryEnum.UTILITY)
            .rating(4)
            .build();
        productService.addProduct(hammer);
        Product screwdriver = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Screwdriver")
            .category(CategoryEnum.UTILITY)
            .rating(2)
            .build();
        productService.addProduct(screwdriver);
        Product tv = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Television")
            .category(CategoryEnum.ELECTRIC)
            .rating(3)
            .build();
        productService.addProduct(tv);
        Product drill = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Drill")
            .category(CategoryEnum.UTILITY)
            .rating(5)
            .build();
        productService.addProduct(drill);

        return productService;
    }

    @Test
    @DisplayName("addProduct with valid product returns true")
    public void testAddProductTrue() {
        ProductService productService = new ProductService();
        Product tv = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Television")
            .category(CategoryEnum.ELECTRIC)
            .rating(3)
            .build();
        boolean added = productService.addProduct(tv);

        assertTrue(added);
    }

    @Test
    @DisplayName("constructing Product with null name throws NullPointerException")
    public void testAddProductFalse() {
        ProductService productService = new ProductService();

        assertThrows(NullPointerException.class, () -> {
            Product tv = new Product.Builder()
                .id(UUID.randomUUID())
                .name(null)
                .category(CategoryEnum.ELECTRIC)
                .rating(3)
                .build();
        });
    }

    @Test
    @DisplayName("getProducts returns all stored products and includes expected names")
    public void getProductsTest() {
        ProductService productService = setupWarehouse();

        List<Product> products = productService.getProducts();

        assertEquals(4, products.size());
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Hammer")));
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Screwdriver")));
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Television")));
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Drill")));
    }

    @Test
    @DisplayName("updateProduct with existing id updates product and returns true")
    public void updateProductTrueTest() {
        ProductService productService = new ProductService();

        Product hammer = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Hammer")
            .category(CategoryEnum.UTILITY)
            .rating(4)
            .build();
        productService.addProduct(hammer);

        boolean updated = productService.updateProduct(hammer.id().toString(), "Updated hammer", CategoryEnum.UTILITY, 5);

        assertTrue(updated);
    }

    @Test
    @DisplayName("updateProduct with invalid id returns false")
    public void updateProductFalseTest() {
        ProductService productService = new ProductService();

        Product hammer = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Hammer")
            .category(CategoryEnum.UTILITY)
            .rating(4)
            .build();

        productService.addProduct(hammer);

        boolean updated = productService.updateProduct("h342dass", "Updated hammer", CategoryEnum.UTILITY, 5);

        assertFalse(updated);
    }

    @Test
    @DisplayName("getProductById with existing id returns product")
    public void getProductByIdTrueTest() {
        ProductService productService = new ProductService();

        Product hammer = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Hammer")
            .category(CategoryEnum.UTILITY)
            .rating(4)
            .build();
        productService.addProduct(hammer);

        Optional<Product> sameProductFromId = productService.getProductById(hammer.id().toString());

        assertTrue(sameProductFromId.isPresent());
    }

    @Test
    @DisplayName("getProductById with non-existing id returns empty")
    public void getProductByIdFalseTest() {
        ProductService productService = new ProductService();

        Product hammer = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Hammer")
            .category(CategoryEnum.UTILITY)
            .rating(4)
            .build();
        productService.addProduct(hammer);

        Optional<Product> sameProductFromId = productService.getProductById("head21zsd");

        assertFalse(sameProductFromId.isPresent());
    }

    @Test
    @DisplayName("getProductsByCategorySorted filters by category and sorts names A to Z")
    public void getProductsByCategorySorted() {
        ProductService productService = setupWarehouse();

        List<Product> products = productService.getProductsByCategorySorted(CategoryEnum.UTILITY);

        assertEquals(3, products.size());
        assertEquals("Drill", products.getFirst().name());
        assertEquals("Hammer", products.get(1).name());
        assertEquals("Screwdriver", products.get(2).name());
    }

    @Test
    @DisplayName("getProductsCreatedAfter returns all products created after that date")
    public void getProductsCreatedAfterTrueTest() {
        ProductService productService = new ProductService();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);
        LocalDate yesterdayDate = nowDate.minusDays(1);
        LocalDate yesterday2Date = nowDate.minusDays(2);

        Product hammer = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Hammer")
            .category(CategoryEnum.UTILITY)
            .rating(4)
            .createdDate(nowDate)
            .modifiedDate(nowDate)
            .build();
        Product saw = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Saw")
            .category(CategoryEnum.UTILITY)
            .rating(2)
            .createdDate(tomorrowDate)
            .modifiedDate(tomorrowDate)
            .build();
        Product nailgun = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Nailgun")
            .category(CategoryEnum.UTILITY)
            .rating(3)
            .createdDate(yesterday2Date)
            .modifiedDate(yesterday2Date)
            .build();

        productService.addProduct(hammer);
        productService.addProduct(saw);
        productService.addProduct(nailgun);
        List<Product> products = productService.getProductsCreatedAfter(yesterdayDate);
        assertEquals(2, products.size());
        assertEquals("Hammer", products.get(0).name());
        assertEquals("Saw", products.get(1).name());
    }

    @Test
    @DisplayName("getProductsCreatedAfter returns no products because all products are created before date ")
    public void getProductsCreatedAfterFalseTest() {
        ProductService productService = new ProductService();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);

        Product hammer = new Product.Builder()
                .id(UUID.randomUUID())
                .name("Hammer")
                .category(CategoryEnum.UTILITY)
                .rating(4)
                .createdDate(nowDate)
                .modifiedDate(nowDate)
                .build();
        Product saw = new Product.Builder()
                .id(UUID.randomUUID())
                .name("Saw")
                .category(CategoryEnum.UTILITY)
                .rating(2)
                .createdDate(nowDate)
                .modifiedDate(nowDate)
                .build();
        Product nailgun = new Product.Builder()
                .id(UUID.randomUUID())
                .name("Nailgun")
                .category(CategoryEnum.UTILITY)
                .rating(3)
                .createdDate(nowDate)
                .modifiedDate(nowDate)
                .build();

        productService.addProduct(hammer);
        productService.addProduct(saw);
        productService.addProduct(nailgun);
        List<Product> products = productService.getProductsCreatedAfter(tomorrowDate);
        assertEquals(0, products.size());
    }

    @Test
    @DisplayName("getModifiedProducts returns products where modifiedDate is not createdDate")
    public void getModifiedProductsTrueTest() {
        ProductService productService = new ProductService();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);

        Product hammer = new Product.Builder()
                .id(UUID.randomUUID())
                .name("Hammer")
                .category(CategoryEnum.UTILITY)
                .rating(4)
                .createdDate(nowDate)
                .modifiedDate(tomorrowDate)
                .build();
        Product saw = new Product.Builder()
                .id(UUID.randomUUID())
                .name("Saw")
                .category(CategoryEnum.UTILITY)
                .rating(2)
                .createdDate(nowDate)
                .modifiedDate(nowDate)
                .build();
        Product nailgun = new Product.Builder()
                .id(UUID.randomUUID())
                .name("Nailgun")
                .category(CategoryEnum.UTILITY)
                .rating(3)
                .createdDate(nowDate)
                .modifiedDate(nowDate)
                .build();

        productService.addProduct(saw);
        productService.addProduct(nailgun);
        productService.addProduct(hammer);

        List <Product> products = productService.getModifiedProducts();
        assertEquals(1, products.size());
        assertEquals("Hammer", products.getFirst().name());
    }

    @Test
    @DisplayName("getModifiedProducts returns empty list when none is modified")
    public void getModifiedProductsFalseTest() {
        ProductService productService = new ProductService();

        LocalDate nowDate = LocalDate.now();

        Product saw = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Saw")
            .category(CategoryEnum.UTILITY)
            .rating(3)
            .createdDate(nowDate)
            .modifiedDate(nowDate)
            .build();
        Product nailgun = new Product.Builder()
            .id(UUID.randomUUID())
            .name("Nailgun")
            .category(CategoryEnum.UTILITY)
            .rating(3)
            .createdDate(nowDate)
            .modifiedDate(nowDate)
            .build();

        productService.addProduct(saw);
        productService.addProduct(nailgun);

        List <Product> products = productService.getModifiedProducts();
        assertEquals(0, products.size());
    }
}