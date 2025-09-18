package entities;

/* Test Class where I declare Warehouses´ variable wh - this is private */

import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Warehouse;

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
        Product p = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

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
        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "NOPE",
                        "X",
                        Category.FOOD,
                        5),
                "Should throw if product id does not exist");
    }

    @Test
    void updateProduct_failsOnEmptyName() {
        Product p = new Product(
                "U-010",
                "Coffee",
                Category.FOOD,
                7,
                LocalDate.now(),
                LocalDate.now());

        wh.addProduct(p);

        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "U-010",
                        " ",
                        Category.FOOD,
                        5),
                "Name can not be empty");
    }

    @Test
    void updateProduct_failsOnBadRating() {
        Product p = new Product(
                "U-011",
                "Coffee",
                Category.FOOD,
                7,
                LocalDate.now(),
                LocalDate.now());

        wh.addProduct(p);

        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "U-011",
                        "Coffee",
                        Category.FOOD,
                        11),
                "Rating must be between 0-10");

        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "U-011",
                        "Coffee",
                        Category.FOOD,
                        -1),
                "Rating must be between 0-10");
    }

    @Test
    void getProductsByCategorySorted_ReturnsAZByName_caseInsensitive() {
        Product a = new Product("S1", "alpha", Category.TOYS, 7, LocalDate.now(), LocalDate.now());
        Product b = new Product("S2", "Beta", Category.TOYS, 6, LocalDate.now(), LocalDate.now());
        Product c = new Product("S3", "car", Category.TOYS, 5, LocalDate.now(), LocalDate.now());
        Product x = new Product("X1", "zest", Category.TOYS, 4, LocalDate.now(), LocalDate.now());

        wh.addProduct(b);
        wh.addProduct(c);
        wh.addProduct(a);
        wh.addProduct(x);

        java.util.List<Product> toys = wh.getProductsByCategorySorted(Category.TOYS);

        //Case-insensitive
        assertEquals(4, toys.size());
        assertEquals("alpha", toys.get(0).name());
        assertEquals("Beta", toys.get(1).name());
        assertEquals("car", toys.get(2).name());
    }

    @Test
    void getProductsByCategorySorted_nullCategory_throws() {
        assertThrows(NullPointerException.class, () -> wh.getProductsByCategorySorted(null),
                "Category can not be null");
    }

    @Test
    void getProductsCreatedAfter_FiltersByDate() {
        LocalDate d0 = LocalDate.now().minusDays(10);
        LocalDate d1 = LocalDate.now().minusDays(5);
        LocalDate d2 = LocalDate.now();

        Product p0 = new Product("C1", "Old", Category.FOOD, 5, d0, d0);
        Product p1 = new Product("C2", "Middle", Category.FOOD, 5, d1, d1);
        Product p2 = new Product("C3", "New", Category.FOOD, 5, d2, d2);

        wh.addProduct(p0);
        wh.addProduct(p1);
        wh.addProduct(p2);

        List<Product> out = wh.getProductsCreatedAfter(LocalDate.now().minusDays(7));

        assertEquals(2, out.size());
        assertTrue(out.stream().anyMatch(p -> p.id().equals("C2")));
        assertTrue(out.stream().anyMatch(p -> p.id().equals("C3")));
    }

    @Test
    void getProductsCreatedAfter_nullDate_throws() {
        assertThrows(NullPointerException.class, () -> wh.getProductsCreatedAfter(null),
                "date can not be null");
    }

}
