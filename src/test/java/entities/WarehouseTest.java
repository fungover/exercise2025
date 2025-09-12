package entities;

/* Test Class where I declare Warehouses´ variable wh - this is private */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private Warehouse wh;

    // Every test gets its own new Warehouse, this makes every test fresh and clean for every new incoming test-product
    @BeforeEach
    void setup() {
        wh = new Warehouse();
    }

    @Test
    void addProduct_Success() {
        Product p = new Product(
                "A-001",
                "Coffee",
                Category.FOOD,
                8, LocalDate.now(),
                LocalDate.now());

        Product saved = wh.addProduct(p);
        assertEquals(p, saved, "Should return the same product instance");
        assertEquals(p, wh.getProductById("A-001"));
    }

    @Test
    void addProduct_failsOnDuplicatedId() {
        Product p1 = new Product(
                "A-001",
                "Coffee",
                Category.FOOD,
                8,
                LocalDate.now(),
                LocalDate.now());

        Product p2 = new Product(
                "A-001",
                "Cookies",
                Category.FOOD, 4,
                LocalDate.now(),
                LocalDate.now());

        wh.addProduct(p1);
        assertThrows(IllegalArgumentException.class, () -> wh.addProduct(p2), "Should not allow duplicated ID´s");
    }


    @Test
    void addProduct_failsOnNullProduct() {
        assertThrows(IllegalArgumentException.class,
                () -> wh.addProduct(null),
                "Should reject null product");
    }

    @Test
    void getProductById_returnProduct() {
        Product p = new Product(
                "C-001",
                "LEGO",
                Category.TOYS,
                9,
                LocalDate.now(),
                LocalDate.now());

        wh.addProduct(p);

        Product found = wh.getProductById("C-001");
        assertEquals(p, found, "Should return the product with matching Id");
    }

    @Test
    void getProductById_failOnMissingId() {
        assertThrows(IllegalArgumentException.class, () -> wh.getProductById("NO"), "Should throw if product does not exist");
    }

    @Test
    void getAllProducts_returnsAllProducts() {
        Product p1 = new Product(
                "D-001",
                "Book",
                Category.BOOKS,
                7,
                LocalDate.now(),
                LocalDate.now());

        Product p2 = new Product(
                "E-002",
                "Ball",
                Category.TOYS,
                6,
                LocalDate.now(),
                LocalDate.now());

        Product p3 = new Product(
                "E-003",
                "Spinner",
                Category.TOYS,
                7,
                LocalDate.now(),
                LocalDate.now());

        wh.addProduct(p1);
        wh.addProduct(p2);
        wh.addProduct(p3);

        List<Product> all = wh.getAllProducts();

        assertEquals(3, all.size(), "Should return a list of all products");
        assertTrue(all.contains(p1));
        assertTrue(all.contains(p2));
        assertTrue(all.contains(p3));
    }

    @Test
    void getAllProducts_ReturnsEmptyListWhenNoProducts() {
        List<Product> all = wh.getAllProducts();
        assertTrue(all.isEmpty(), "Should return an empty List if no products present");
    }

    @Test
    void updateProduct_success() {
        Product p = new Product(
                "U-001",
                "coffee",
                Category.FOOD,
                7,
                LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(3));
        wh.addProduct(p);

        Product updated = wh.updateProduct("U-001", "Mascara", Category.BEAUTY, 10);

        assertEquals("Mascara", updated.name());
        assertEquals(Category.BEAUTY, updated.category());
        assertEquals(10, updated.rating());
        assertEquals(p.id(), updated.id(), "Id should not change");
        assertEquals(p.createdDate(), updated.createdDate(), "Should not change");
        assertTrue(updated.modifiedDate().isAfter(p.modifiedDate()), "modifiedDate should be updated now");

        assertEquals(updated, wh.getProductById("U-001"));
    }
    @Test
    void updateProduct_failsOnMissingId() {
        /** soon **/
    }
}
