package service;

import entities.Category;
import entities.Product;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.SaveProductRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {


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

        assertThrows(IllegalArgumentException.class, () -> {
            Product product = new Product.Builder()
                    .id("2")
                    .name("")
                    .category(Category.SUIT)
                    .rating(4)
                    .build();

            service.addProduct(product);

        });
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
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        assertThrows(IllegalArgumentException.class, () ->
                service.updateProduct("999", "Nonexistent", Category.SUIT, 5)
        );
    }

    @Test
    void testGetAllProductsReturnsProducts() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product product1 = new Product.Builder()
                .id("1")
                .name("Red Dress")
                .category(Category.DRESS)
                .rating(5)
                .build();


        Product product2 = new Product.Builder()
                .id("2")
                .name("Suit Jacket")
                .category(Category.SUIT)
                .rating(3)
                .build();


        service.addProduct(product1);
        service.addProduct(product2);

        List<Product> all = service.getAllProducts();

        assertEquals(2, all.size());
        assertTrue(all.contains(product1));
        assertTrue(all.contains(product2));
    }

    @Test
    void testGetProductByIdSuccess() { //Hittar produkt med hjälp av ID.
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product product = new Product.Builder()
                .id("1")
                .name("Red dress")
                .category(Category.DRESS)
                .rating(5)
                .build();

        service.addProduct(product);

        Product found = service.getProductId("1");

        assertEquals("Red dress", found.name());
        assertEquals(Category.DRESS, found.category());
    }

    @Test
    void testGetProductByIdFailsIfNotFound() {  //Detta ger fel om ID inte finns.
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        assertThrows(IllegalArgumentException.class, () ->
                service.getProductId("999")
        );
    }

    @Test
    void testGetProductsByCategorySortedSuccess() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product p1 = new Product.Builder()
                .id("1")
                .name("Blue Shirt")
                .category(Category.SHIRT)
                .rating(2)
                .build();

        Product p2 = new Product.Builder()
                .id("2")
                .name("Red Shirt")
                .category(Category.SHIRT)
                .rating(4)
                .build();

        Product p3 = new Product.Builder()
                .id("3")
                .name("Black dress")
                .category(Category.DRESS)
                .rating(3)
                .build();

        service.addProduct(p1);
        service.addProduct(p2);
        service.addProduct(p3);

        List<Product> shirts = service.getProductsByCategorySorted(Category.SHIRT);

        assertEquals(2, shirts.size());
        assertEquals("Blue Shirt", shirts.get(0).name());
        assertEquals("Red Shirt", shirts.get(1).name());
    }

    @Test
    void testGetProductsByCategorySortedEmpty() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        List<Product> dresses = service.getProductsByCategorySorted(Category.DRESS);
        assertTrue(dresses.isEmpty());
    }

    @Test
    void testGetProductsCreatedAfterSuccess() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product old = new Product.Builder()
                .id("1") .name("Old Suit")
                .category(Category.SUIT)
                .rating(5)
                .createdDate(LocalDate.of(2020, 1, 1))
                .modifiedDate(LocalDate.of(2020, 1, 1))
                .build();


        Product recent = new Product.Builder()
                .id("2")
                .name("New Dress")
                .category(Category.DRESS)
                .rating(1)
                .createdDate(LocalDate.of(2023, 1, 1))
                .modifiedDate(LocalDate.of(2023, 1, 1))
                .build();

        service.addProduct(old);
        service.addProduct(recent);

        List<Product> results = service.getProductsCreatedAfter(LocalDate.of(2021, 1, 1));


        assertEquals(1, results.size());
        assertEquals("New Dress", results.get(0).name());
    }

    @Test
    void testGetProductsCreatedAfterNoResults() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product old = new Product.Builder()
                .id("1")
                .name("Vintage Coat")
                .category(Category.SUIT)
                .rating(4)
                .createdDate(LocalDate.of(2019, 5, 10))
                .modifiedDate(LocalDate.of(2019, 5, 10))
                .build();

        service.addProduct(old);

        List<Product> results = service.getProductsCreatedAfter(LocalDate.of(2022, 1, 1));
        assertTrue(results.isEmpty());
    }

    @Test
    void testGetModifiedProductsSuccess() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product original = new Product.Builder()
                .id("1")
                .name("Green Dress")
                .category(Category.DRESS)
                .rating(2)
                .createdDate(LocalDate.of(2023, 1, 1))
                .modifiedDate(LocalDate.of(2023, 1, 1))
                .build();


        Product modified = new Product.Builder()
                .id("2")
                .name("Blue Suit")
                .category(Category.SUIT)
                .rating(5)
                .createdDate(LocalDate.of(2023, 1, 1))
                .modifiedDate(LocalDate.of(2023, 2, 1))
                .build();

        service.addProduct(original);
        service.addProduct(modified);

        List<Product> results = service.getModifiedProducts();

        assertEquals(1, results.size());
        assertEquals("Blue Suit", results.get(0).name());
    }

    @Test
    void testGetModifiedProductsEmpty() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        Product product = new Product.Builder()
                .id("1") .name("Yellow Shirt")
                .category(Category.SHIRT)
                .rating(5)
                .build();

        service.addProduct(product);

        List<Product> results = service.getModifiedProducts();
        assertTrue(results.isEmpty());
    }



    //Test som ska faila
    @Test
    void testGetAllProductsEmpty() {
        ProductRepository repo = new SaveProductRepository();
        ProductService service = new ProductService(repo);

        List<Product> all = service.getAllProducts();
        assertTrue(all.isEmpty());
    }


}


