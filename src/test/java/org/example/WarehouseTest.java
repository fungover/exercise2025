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
    @DisplayName("Checks that AddProduct works as supposed to. Should return true because input in product is valid")
    public void testAddProductTrue() {
        Warehouse warehouse = new Warehouse();
        Product tv = new Product(UUID.randomUUID(), "Television", CategoryEnum.ELECTRIC, 3, LocalDate.now(), LocalDate.now());
        boolean added = warehouse.addProduct(tv);

        assertTrue(added);
    }

    @Test
    @DisplayName("Checks that AddProduct works as supposed to. Should call NullPointerException because name is null/invalid.")
    public void testAddProductFalse() {
        Warehouse warehouse = new Warehouse();

        assertThrows(NullPointerException.class, () -> {
            Product tv = new Product(UUID.randomUUID(), null, CategoryEnum.ELECTRIC, 3, LocalDate.now(), LocalDate.now());
        });
    }

    @Test
    @DisplayName("Checks that getProduct returns all products in our warehouse storage. Should print a list of all products.")
    public void getProductsTest() {
        Warehouse warehouse = setupWarehouse();

        System.setOut(new PrintStream(outContent));

        warehouse.getProducts();

        String printed = outContent.toString();
        assertTrue(printed.contains("Hammer"));
        assertTrue(printed.contains("Screwdriver"));
        assertTrue(printed.contains("Television"));
    }

    @Test
    @DisplayName("Checks that updateProduct works as intended. Should return true if product is updated")
    public void updateProductTrueTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        boolean updated = warehouse.updateProduct(hammer.id().toString(), "Updated hammer", CategoryEnum.UTILITY, 5);

        assertTrue(updated);
    }

    @Test
    @DisplayName("Checks that updateProduct works as intended. Should return false because product uuid is wrong")
    public void updateProductFalseTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        boolean updated = warehouse.updateProduct("h342dass", "Updated hammer", CategoryEnum.UTILITY, 5);

        assertFalse(updated);
    }

    @Test
    @DisplayName("Checks that getProductById works as intended, should return product")
    public void getProductByIdTrueTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        Optional<Product> sameProductFromId = warehouse.getProductById(hammer.id().toString());

        assertTrue(sameProductFromId.isPresent());
    }

    @Test
    @DisplayName("Checks that getProductById works as intended, should return product")
    public void getProductByIdFalseTest() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);

        Optional<Product> sameProductFromId = warehouse.getProductById("head21zsd");

        assertFalse(sameProductFromId.isPresent());
    }

    @Test
    @DisplayName("Checks that getProductsByCategorySorted works as intended. Should return all products in the same category sorted A to Z")
    public void getProductsByCategorySorted() {
        Warehouse warehouse = setupWarehouse();

        List<Product> products = warehouse.getProductsByCategorySorted(CategoryEnum.UTILITY);

        assertEquals(3, products.size());
        assertEquals("Drill", products.getFirst().name());
        assertEquals("Hammer", products.get(1).name());
        assertEquals("Screwdriver", products.get(2).name());
    }

    @Test
    @DisplayName("Checks that getProductsCreatedAfter works as intended. Should return products with later createdDate then inserted date")
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
    @DisplayName("Checks that getProductsCreatedAfter works as intended. Should return all products because all products are created after input date")
    public void getProductsCreatedAfterFalseTest() {
        Warehouse warehouse = new Warehouse();

        LocalDate nowDate = LocalDate.now();
        LocalDate tomorrowDate = nowDate.plusDays(1);
        LocalDate yesterdayDate = nowDate.minusDays(1);
        LocalDate yesterday2Date = nowDate.minusDays(2);

        Product hammer = new Product(UUID.randomUUID(), "Hammer",  CategoryEnum.UTILITY, 4, nowDate, nowDate);
        Product saw = new Product(UUID.randomUUID(), "Saw", CategoryEnum.UTILITY, 2, tomorrowDate, tomorrowDate);
        Product nailgun = new Product(UUID.randomUUID(), "Nailgun", CategoryEnum.UTILITY, 3, yesterdayDate, yesterdayDate);

        warehouse.addProduct(hammer);
        warehouse.addProduct(saw);
        warehouse.addProduct(nailgun);
        List<Product> products = warehouse.getProductsCreatedAfter(yesterday2Date);
        assertEquals(3, products.size());
        assertEquals("Hammer", products.get(0).name());
        assertEquals("Saw", products.get(1).name());
        assertEquals("Nailgun", products.get(2).name());
    }

    @Test
    @DisplayName("Checks that getModifedProducts works. Should return products that doesnt have the same createdDate as modifiedDate")
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
    @DisplayName("Checks that getModifedProducts works. Should return 0 products, because none is modified")
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