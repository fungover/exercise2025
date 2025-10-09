package service;

import entities.Category;
import entities.Product;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.SaveProductRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {



    @Test
    void testAddProductSuccess() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product product = new Product.Builder()
                .id("1")
                .name("Red Dress")
                .category(Category.DRESS)
                .rating(5)
                .build();

        service.addProduct(product);

        assertEquals(1, service.getAllProducts().size());
        assertEquals("Red Dress", service.getAllProducts().get(0).name());
    }

    @Test
    void testAddProductFailsIfNameEmpty() { //Ser till att det inte går att lägga till produkt utan namn.
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product product = new Product.Builder()
                .id("2")
                .name("")
                .category(Category.SUIT)
                .rating(4)
                .build();

        assertThrows(IllegalArgumentException.class, () -> service.addProduct(product));
    }

    @Test
    void testUpdateProductSuccess() { //Testar att det går att uppdatera produkten med datum t.ex.
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product product = new Product.Builder()
                .id("1")
                .name("Red Dress")
                .category(Category.DRESS)
                .rating(5)
                .createdDate(LocalDate.now().minusDays(1))
                .modifiedDate(LocalDate.now().minusDays(1))
                .build();


        service.addProduct(product);
        service.updateProduct("1", "Blue Dress", Category.DRESS, 8);

        Product updated = service.getAllProducts().get(0);
        assertEquals("Blue Dress", updated.name());
        assertEquals(8, updated.rating());
        assertEquals(product.createdDate(), updated.createdDate());
        assertTrue(updated.modifiedDate().isAfter(product.modifiedDate()));
    }

    @Test
    void testUpdateProductFailsIfNotFound() {
        ProductService warehouse = new ProductService();

        assertThrows(IllegalArgumentException.class, () ->
                warehouse.updateProduct("999", "Nonexistent", Category.SUIT, 5)
        );
    }

    @Test
    void testGetAllProductsReturnsProducts() {
        ProductService warehouse = new ProductService();
        Product product1 = new Product("1", "Red Dress", Category.DRESS, 5,
                LocalDate.now(), LocalDate.now());
        Product product2 = new Product("2", "Suit Jacket", Category.SUIT, 3,
                LocalDate.now(), LocalDate.now());

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        List<Product> all = warehouse.getAllProducts();

        assertEquals(2, all.size());
        assertTrue(all.contains(product1));
        assertTrue(all.contains(product2));
    }

    @Test
    void testGetProductByIdSuccess() { //Hittar produkt med hjälp av ID.
        ProductService warehouse = new ProductService();
        Product product = new Product("1", "Red dress", Category.DRESS, 5,
                LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);

        Product found = warehouse.getProductById("1");

        assertEquals("Red dress", found.name());
        assertEquals(Category.DRESS, found.category());
    }

    @Test
    void testGetProductByIdFailsIfNotFound() {  //Detta ger fel om ID inte finns.
        ProductService warehouse = new ProductService();

        assertThrows(IllegalArgumentException.class, () ->
                warehouse.getProductById("999")
        );
    }

    @Test
    void testGetProductsByCategorySortedSuccess() {
        ProductService warehouse = new ProductService();
        Product p1 = new Product("1", "Blue Shirt", Category.SHIRT, 2,
                LocalDate.now(), LocalDate.now());
        Product p2 = new Product("2", "Red Shirt", Category.SHIRT, 4,
                LocalDate.now(), LocalDate.now());
        Product p3 = new Product("3", "Black dress", Category.DRESS, 3,
                LocalDate.now(), LocalDate.now());

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);

        List<Product> shirts = warehouse.getProductsByCategorySorted(Category.SHIRT);

        assertEquals(2, shirts.size());
        assertEquals("Blue Shirt", shirts.get(0).name());
        assertEquals("Red Shirt", shirts.get(1).name());
    }

    @Test
    void testGetProductsByCategorySortedEmpty() {
        ProductService warehouse = new ProductService();

        List<Product> dresses = warehouse.getProductsByCategorySorted(Category.DRESS);

        assertTrue(dresses.isEmpty());
    }

    @Test
    void testGetProductsCreatedAfterSuccess() {
        ProductService warehouse = new ProductService();
        Product old = new Product("1", "Old Suit", Category.SUIT, 5,
                LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 1));
        Product recent = new Product("2", "New Dress", Category.DRESS, 1,
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1));

        warehouse.addProduct(old);
        warehouse.addProduct(recent);

        List<Product> results = warehouse.getProductsCreatedAfter(LocalDate.of(2021, 1, 1));


        assertEquals(1, results.size());
        assertEquals("New Dress", results.get(0).name());
    }

    @Test
    void testGetProductsCreatedAfterNoResults() {
        ProductService warehouse = new ProductService();
        Product old = new Product("1", "Vintage Coat", Category.SUIT, 4,
                LocalDate.of(2019, 5, 10), LocalDate.of(2019, 5, 10));

        warehouse.addProduct(old);

        List<Product> results = warehouse.getProductsCreatedAfter(LocalDate.of(2022, 1, 1));

        assertTrue(results.isEmpty());
    }

    @Test
    void testGetModifiedProductsSuccess() {
        ProductService warehouse = new ProductService();
        Product original = new Product("1", "Green Dress", Category.DRESS, 2,
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 1));
        Product modified = new Product("2", "Blue Suit", Category.SUIT, 5,
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 2, 1));

        warehouse.addProduct(original);
        warehouse.addProduct(modified);

        List<Product> results = warehouse.getModifiedProducts();

        assertEquals(1, results.size());
        assertEquals("Blue Suit", results.get(0).name());
    }

    @Test
    void testGetModifiedProductsEmpty() {
        ProductService warehouse = new ProductService();
        Product product = new Product("1", "Yellow Shirt", Category.SHIRT, 5,
                LocalDate.now(), LocalDate.now());

        warehouse.addProduct(product);

        List<Product> results = warehouse.getModifiedProducts();

        assertTrue(results.isEmpty());
    }



    //Test som ska faila
    @Test
    void testGetAllProductsEmpty() {
        ProductService warehouse = new ProductService();

        List<Product> all = warehouse.getAllProducts();

        assertTrue(all.isEmpty());
    }


}


