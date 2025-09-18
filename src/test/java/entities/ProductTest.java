package entities;

import org.junit.jupiter.api.Test;
import service.Warehouse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void addProduct_sizeIncreasesByOne() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime dateTime = LocalDateTime.now();
        Product product = new Product(1, "Book", Category.BOOK, 8, dateTime, dateTime);

        warehouse.addProduct(product);

        assertEquals(1, warehouse.getProducts().size());
        assertEquals(product, warehouse.getProducts().getFirst());
    }

    @Test
    public void addProduct_throwsExceptionWhenNameIsEmpty() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 12, 0);
        Product product = new Product(2, "", Category.BOOK, 5, now, now);

        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(product));
    }

    @Test
    void updateProduct_failsWhenProductNotFound() {
        Warehouse warehouse = new Warehouse();

        assertThrows(NoSuchElementException.class, () ->
                warehouse.updateProduct(99, "Name", Category.BOOK, 5)
        );
    }

    @Test
    void updateProduct_updatesWhenProductExists() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product(1, "Old Name", Category.BOOK, 3, now,
                now);

        warehouse.addProduct(product);

        warehouse.updateProduct(1, "New Name", Category.BOOK, 5);

        Product updated = warehouse.getProducts().getFirst();

        assertEquals("New Name", updated.getName());
        assertEquals(5, updated.getRating());
        assertEquals(Category.BOOK, updated.getCategory());
    }

    @Test
    void getAllProductsTest() {
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product(1, "Book", Category.BOOK, 5, now, now);
        Product p2 = new Product(2, "Movie", Category.MOVIE, 4, now, now);

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

        Product product1 = new Product(56, "Book", Category.BOOK, 5, now, now);
        Product product2 = new Product(78, "Book", Category.BOOK, 5, now, now);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        assertEquals(56, product1.getId());
        assertEquals(78, product2.getId());

    }

    @Test
    public void SortProductCategory_alphabetically(){
        Warehouse warehouse = new Warehouse();
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product(1, "The Book of Lost Hours", Category.BOOK, 4, now, now);
        Product p2 = new Product(2, "Dominion", Category.BOOK, 1, now, now);
        Product p3 = new Product(3, "People Like Us", Category.BOOK, 3, now, now);
        Product p4 = new Product(4, "Clown Town", Category.BOOK, 5, now, now);
        Product p5 = new Product(5, "Clown Town", Category.MOVIE, 5, now, now);
        Product p6 = new Product(6, "Bla bla bla", Category.MOVIE, 5, now, now);

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);
        warehouse.addProduct(p4);
        warehouse.addProduct(p5);
        warehouse.addProduct(p6);

        List<Product> books = warehouse.getProductsByCategorySorted(Category.BOOK);
        List<Product> movies = warehouse.getProductsByCategorySorted(Category.MOVIE);

        assertEquals(4, books.size());
        assertEquals("Clown Town", books.get(0).getName());
        assertEquals("Dominion", books.get(1).getName());
        assertEquals("People Like Us", books.get(2).getName());

        assertEquals(2, movies.size());
        assertEquals("Bla bla bla", movies.get(0).getName());
        assertEquals("Clown Town", movies.get(1).getName());
    }

    @Test
    public void getProductsCreatedAfter_returnsOnlyNewerProducts() {
        Warehouse warehouse = new Warehouse();

        LocalDateTime d1 = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime d2 = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime d3 = LocalDateTime.of(2025, 1, 1, 12, 0);

        Product old = new Product(1, "Old Book", Category.BOOK, 3, d1, d1);
        Product mid = new Product(2, "Mid Book", Category.BOOK, 4, d2, d2);
        Product recent = new Product(3, "Recent Book", Category.BOOK, 5, d3, d3);

        warehouse.addProduct(old);
        warehouse.addProduct(mid);
        warehouse.addProduct(recent);

        List<Product> products = warehouse.getProductsCreatedAfter(LocalDate.of(2023, 6, 1));

        assertEquals(2, products.size());
        assertTrue(products.contains(mid));
        assertTrue(products.contains(recent));
        assertFalse(products.contains(old));
    }


}

