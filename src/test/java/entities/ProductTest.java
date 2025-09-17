package entities;

import org.junit.jupiter.api.Test;
import service.Warehouse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void addProduct_sizeIncreasesByOne() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime dateTime = LocalDateTime.now();
        Product product = new Product(1, "Book", Category.BOOKS, 8, dateTime, dateTime);

        warehouse.addProduct(product);

        assertEquals(1, warehouse.getProducts().size());
        assertEquals(product, warehouse.getProducts().getFirst());
    }

    @Test
    public void addProduct_throwsExceptionWhenNameIsEmpty() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 12, 0);
        Product product = new Product(2, "", Category.BOOKS, 5, now, now);

        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
    }

    @Test
    void updateProduct_failsWhenProductNotFound() {
        Warehouse warehouse = new Warehouse();

        assertThrows(NoSuchElementException.class, () ->
                warehouse.updateProduct(99, "Name", Category.BOOKS, 5)
        );
    }

    @Test
    void updateProduct_updatesWhenProductExists() {
        Warehouse warehouse = new Warehouse();

        Product product = new Product(1, "Old Name", Category.BOOKS, 3, LocalDateTime.now(),
                LocalDateTime.now());

        warehouse.addProduct(product);

        warehouse.updateProduct(1, "New Name", Category.BOOKS, 5);

        Product updated = warehouse.getProducts().getFirst();

        assertEquals("New Name", updated.getName());
        assertEquals(5, updated.getRating());
        assertEquals(Category.BOOKS, updated.getCategory());
    }

    @Test
    void getAllProductsTest() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product(1, "Book", Category.BOOKS, 5, now, now);
        Product p2 = new Product(2, "Movie", Category.MOVIES, 4, now, now);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);

        List<Product> products = warehouse.getAllProducts();

        assertEquals(2, products.size());
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
    }

    @Test
    public void getProductsByIdTest(){
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.now();
        Product product = new Product(56, "Book", Category.BOOKS, 5, now, now);
        Product product2 = new Product(78, "Book", Category.BOOKS, 5, now, now);

        assertEquals(56, product.getId());
        assertEquals(78, product2.getId());

    }





}

