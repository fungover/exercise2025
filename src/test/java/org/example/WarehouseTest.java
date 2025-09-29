package org.example;

import org.example.entities.CategoryEnum;
import org.example.entities.Product;
import org.example.service.Warehouse;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    public Warehouse setupWarehouse() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);
        Product screwdriver = new Product(UUID.randomUUID(), "Screwdriver", CategoryEnum.UTILITY, 2, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(screwdriver);
        Product tv = new Product(UUID.randomUUID(), "Television", CategoryEnum.ELECTRIC, 3, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(tv);
        Product drill = new Product(UUID.randomUUID(), "Drill", CategoryEnum.UTILITY, 5, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(drill);

        return warehouse;
    }

    @Test
    @DisplayName("addProduct with valid product returns true")
    public void testAddProductTrue() {
        Warehouse warehouse = new Warehouse();
        Product tv = new Product(UUID.randomUUID(), "Television", CategoryEnum.ELECTRIC, 3, LocalDate.now(), LocalDate.now());
        boolean added = warehouse.addProduct(tv);

        assertTrue(added);
    }

    @Test
    @DisplayName("constructing Product with null name throws NullPointerException")
    public void testAddProductFalse() {
        Warehouse warehouse = new Warehouse();

        assertThrows(NullPointerException.class, () -> {
            Product tv = new Product(UUID.randomUUID(), null, CategoryEnum.ELECTRIC, 3, LocalDate.now(), LocalDate.now());
        });
    }

    @Test
    @DisplayName("getProducts returns all stored products and includes expected names")
    public void getProductsTest() {
        Warehouse warehouse = setupWarehouse();

        List<Product> products = warehouse.getProducts();

        assertEquals(4, products.size());
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Hammer")));
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Screwdriver")));
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Television")));
        assertTrue(products.stream().anyMatch(product -> product.name().equals("Drill")));
    }

    @Test
    @DisplayName("updateProduct with existing id updates product and returns true")
    public void updateProductTrueTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        boolean updated = warehouse.updateProduct(hammer.id().toString(), "Updated hammer", CategoryEnum.UTILITY, 5);

        assertTrue(updated);
    }

    @Test
    @DisplayName("updateProduct with invalid id returns false")
    public void updateProductFalseTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        boolean updated = warehouse.updateProduct("h342dass", "Updated hammer", CategoryEnum.UTILITY, 5);

        assertFalse(updated);
    }

    @Test
    @DisplayName("getProductById with existing id returns product")
    public void getProductByIdTrueTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        Optional<Product> sameProductFromId = warehouse.getProductById(hammer.id().toString());

        assertTrue(sameProductFromId.isPresent());
    }

    @Test
    @DisplayName("getProductById with non-existing id returns empty")
    public void getProductByIdFalseTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        Optional<Product> sameProductFromId = warehouse.getProductById("head21zsd");

        assertFalse(sameProductFromId.isPresent());
    }

    @Test
    @DisplayName("getProductsByCategorySorted filters by category and sorts names A to Z")
    public void getProductsByCategorySorted() {
        Warehouse warehouse = setupWarehouse();

        List<Product> products = warehouse.getProductsByCategorySorted(CategoryEnum.UTILITY);

        assertEquals(3, products.size());
        assertEquals("Drill", products.getFirst().name());
        assertEquals("Hammer", products.get(1).name());
        assertEquals("Screwdriver", products.get(2).name());
    }

    @Test
    @DisplayName("getProductsCreatedAfter returns all products created after that date")
    public void getProductsCreatedAfterTrueTest() {
        Warehouse warehouse = new Warehouse();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);
        LocalDate yesterdayDate = nowDate.minusDays(1);
        LocalDate yesterday2Date = nowDate.minusDays(2);

        Product hammer = new Product(UUID.randomUUID(), "Hammer",  CategoryEnum.UTILITY, 4, nowDate, nowDate);
        Product saw = new Product(UUID.randomUUID(), "Saw", CategoryEnum.UTILITY, 2, tomorrowDate, tomorrowDate);
        Product nailgun = new Product(UUID.randomUUID(), "Nailgun", CategoryEnum.UTILITY, 3, yesterday2Date, yesterday2Date);

        warehouse.addProduct(hammer);
        warehouse.addProduct(saw);
        warehouse.addProduct(nailgun);
        List<Product> products = warehouse.getProductsCreatedAfter(yesterdayDate);
        assertEquals(2, products.size());
        assertEquals("Hammer", products.get(0).name());
        assertEquals("Saw", products.get(1).name());
    }

    @Test
    @DisplayName("getProductsCreatedAfter returns no products because all products are created before date ")
    public void getProductsCreatedAfterFalseTest() {
        Warehouse warehouse = new Warehouse();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);

        Product hammer = new Product(UUID.randomUUID(), "Hammer",  CategoryEnum.UTILITY, 4, nowDate, nowDate);
        Product saw = new Product(UUID.randomUUID(), "Saw", CategoryEnum.UTILITY, 2, nowDate, nowDate);
        Product nailgun = new Product(UUID.randomUUID(), "Nailgun", CategoryEnum.UTILITY, 3, nowDate, nowDate);

        warehouse.addProduct(hammer);
        warehouse.addProduct(saw);
        warehouse.addProduct(nailgun);
        List<Product> products = warehouse.getProductsCreatedAfter(tomorrowDate);
        assertEquals(0, products.size());
    }

    @Test
    @DisplayName("getModifiedProducts returns products where modifiedDate is not createdDate")
    public void getModifedProductsTrueTest() {
        Warehouse warehouse = new Warehouse();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);

        Product saw = new Product(UUID.randomUUID(), "Saw",  CategoryEnum.UTILITY, 4, nowDate, nowDate);
        Product nailgun = new Product(UUID.randomUUID(), "Nailgun",  CategoryEnum.UTILITY, 4, nowDate, nowDate);
        Product hammer = new Product(UUID.randomUUID(), "Hammer",  CategoryEnum.UTILITY, 4, nowDate, tomorrowDate);

        warehouse.addProduct(saw);
        warehouse.addProduct(nailgun);
        warehouse.addProduct(hammer);

        List <Product> products = warehouse.getModifiedProducts();
        assertEquals(1, products.size());
        assertEquals("Hammer", products.getFirst().name());
    }

    @Test
    @DisplayName("getModifiedProducts returns empty list when none is modified")
    public void getModifedProductsFalseTest() {
        Warehouse warehouse = new Warehouse();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);

        Product saw = new Product(UUID.randomUUID(), "Saw",  CategoryEnum.UTILITY, 4, nowDate, nowDate);
        Product nailgun = new Product(UUID.randomUUID(), "Nailgun",  CategoryEnum.UTILITY, 4, nowDate, nowDate);

        warehouse.addProduct(saw);
        warehouse.addProduct(nailgun);

        List <Product> products = warehouse.getModifiedProducts();
        assertEquals(0, products.size());
    }
}