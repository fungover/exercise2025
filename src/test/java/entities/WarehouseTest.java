package entities;

/* Test Class where I declare Warehouses´ variable wh - this is private */

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
        Product p1 = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Product p2 = new Product.Builder()
                .id("A-001")
                .name("Cookies")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        wh.addProduct(p1);
        assertThrows(IllegalArgumentException.class, () -> wh.addProduct(p2),
                "Should not allow duplicated ID´s");
    }


    @Test
    void addProduct_failsOnNullProduct() {
        assertThrows(IllegalArgumentException.class,
                () -> wh.addProduct(null),
                "Should reject null product");
    }

    @Test
    void getProductById_returnProduct() {
        Product p = new Product.Builder()
                .id("C-001")
                .name("Lego")
                .category(Category.TOYS)
                .rating(9)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

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
        Product p1 = new Product.Builder()
                .id("D-001")
                .name("Book")
                .category(Category.BOOKS)
                .rating(7)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Product p2 = new Product.Builder()
                .id("E-001")
                .name("Ball")
                .category(Category.TOYS)
                .rating(6)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        Product p3 = new Product.Builder()
                .id("E-003")
                .name("Spinner")
                .category(Category.TOYS)
                .rating(7)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

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
        Product p = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        wh.addProduct(p);

        Product updated = wh.updateProduct("A-001", "Mascara", Category.BEAUTY, 10);

        assertEquals("Mascara", updated.getName());
        assertEquals(Category.BEAUTY, updated.getCategory());
        assertEquals(10, updated.getRating());
        assertEquals(p.getId(), updated.getId(), "Id should not change");
        assertEquals(p.getCreatedDate(), updated.getCreatedDate(), "Should not change");
        assertTrue(!updated.getModifiedDate().isBefore(p.getModifiedDate()), "modifiedDate should be same day or the day after");

        assertEquals(updated, wh.getProductById("A-001"));
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
        Product p = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        wh.addProduct(p);

        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "A-001",
                        " ",
                        Category.FOOD,
                        5),
                "Name can not be empty");
    }

    @Test
    void updateProduct_failsOnBadRating() {
        Product p = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        wh.addProduct(p);

        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "A-011",
                        "Coffee",
                        Category.FOOD,
                        11),
                "Rating must be between 0-10");

        assertThrows(IllegalArgumentException.class,
                () -> wh.updateProduct(
                        "A-011",
                        "Coffee",
                        Category.FOOD,
                        -1),
                "Rating must be between 0-10");
    }

    @Test
    void getProductsByCategorySorted_ReturnsAZByName_caseInsensitive() {
        Product a = new Product.Builder().id("S1").name("Alpha").category(Category.TOYS).rating(7).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product b = new Product.Builder().id("S2").name("Beta").category(Category.TOYS).rating(6).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product c = new Product.Builder().id("S3").name("Cars").category(Category.TOYS).rating(5).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();
        Product x = new Product.Builder().id("X1").name("Zest").category(Category.TOYS).rating(4).createdDate(LocalDate.now()).modifiedDate(LocalDate.now()).build();

        wh.addProduct(b);
        wh.addProduct(c);
        wh.addProduct(a);
        wh.addProduct(x);

        java.util.List<Product> toys = wh.getProductsByCategorySorted(Category.TOYS);

        //Case-insensitive
        assertEquals(4, toys.size());
        assertEquals("Alpha", toys.get(0).getName());
        assertEquals("Beta", toys.get(1).getName());
        assertEquals("Cars", toys.get(2).getName());
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

        Product p0 = new Product.Builder().id("C1").name("Old").category(Category.FOOD).rating(5)
                .createdDate(d0).modifiedDate(d0).build();
        Product p1 = new Product.Builder().id("C2").name("Middle").category(Category.FOOD).rating(5)
                .createdDate(d1).modifiedDate(d1).build();
        Product p2 = new Product.Builder().id("C3").name("New").category(Category.FOOD).rating(5)
                .createdDate(d2).modifiedDate(d2).build();

        wh.addProduct(p0);
        wh.addProduct(p1);
        wh.addProduct(p2);

        List<Product> out = wh.getProductsCreatedAfter(LocalDate.now().minusDays(7));

        assertEquals(2, out.size());
        assertTrue(out.stream().anyMatch(p -> p.getId().equals("C2")));
        assertTrue(out.stream().anyMatch(p -> p.getId().equals("C3")));
    }

    @Test
    void getProductsCreatedAfter_nullDate_throws() {
        assertThrows(NullPointerException.class, () -> wh.getProductsCreatedAfter(null),
                "date can not be null");
    }

}
