package org.example;

import org.example.entities.CategoryEnum;
import org.example.entities.Product;
import org.example.service.Warehouse;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
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

    public void setupWarehouse() {
        Warehouse warehouse = new Warehouse();

        Product hammer = new Product(UUID.randomUUID(), "Hammer", CategoryEnum.UTILITY, 4, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(hammer);
        Product screwdriver = new Product(UUID.randomUUID(), "Screwdriver", CategoryEnum.UTILITY, 2, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(screwdriver);
        Product tv = new Product(UUID.randomUUID(), "Television", CategoryEnum.ELECTRIC, 3, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(tv);
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
}
