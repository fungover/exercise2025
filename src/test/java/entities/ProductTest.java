package entities;

import org.junit.jupiter.api.Test;
import service.InMemoryProductRepository;
import service.ProductService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void addProduct_sizeIncreasesByOne() {

        ProductService productService = new ProductService(new InMemoryProductRepository());

        LocalDateTime dateTime = LocalDateTime.now();
        Product product = new Product.Builder()
                .id(1)
                .name("Book")
                .category(Category.BOOK)
                .rating(8)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        productService.addProduct(product);

        assertEquals(1, productService.getAllProducts().size());
        assertEquals(product, productService.getAllProducts().getFirst());
    }

    @Test
    public void addProduct_throwsExceptionWhenNameIsEmpty() {
        ProductService productService = new ProductService(new InMemoryProductRepository());
        LocalDateTime now = LocalDateTime.of(2023, 1, 1, 12, 0);
        Product product = new Product.Builder()
                .id(2).name("")
                .category(Category.BOOK)
                .rating(5).createdAt(now)
                .modifiedAt(now)
                .build();

        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
    }

    @Test
    void updateProduct_failsWhenProductNotFound() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        assertThrows(NoSuchElementException.class, () ->
                productService.updateProduct("99", "Name", Category.BOOK, 5)
        );
    }

    @Test
    void updateProduct_updatesWhenProductExists() {
        ProductService productService = new ProductService(new InMemoryProductRepository());
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product.Builder()
                .id(1)
                .name("Old Name")
                .category(Category.BOOK)
                .rating(3)
                .createdAt(now)
                .modifiedAt(now)
                .build();


        productService.addProduct(product);

        productService.updateProduct("1", "New Name", Category.BOOK, 5);

        Product updated = productService.getAllProducts().getFirst();

        assertEquals("New Name", updated.name());
        assertEquals(5, updated.rating());
        assertEquals(Category.BOOK, updated.category());
    }

    @Test
    void getAllProductsTest() {
        ProductService productService = new ProductService(new InMemoryProductRepository());
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product.Builder()
                .id(1)
                .name("Book")
                .category(Category.BOOK)
                .rating(5)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        Product p2 = new Product.Builder()
                .id(2)
                .name("Movie")
                .category(Category.MOVIE)
                .rating(4)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertTrue(products.contains(p1));
        assertTrue(products.contains(p2));
    }

    @Test
    void getAllProducts_emptyWarehouse_returnsEmptyList() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        List<Product> products = productService.getAllProducts();

        assertTrue(products.isEmpty());
    }

    @Test
    void getProductsByCategorySorted_noProductsInCategory_returnsEmptyList() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        List<Product> book1 = productService.getProductsByCategorySorted(Category.valueOf("BOOK"));

        assertTrue(book1.isEmpty());
    }

    @Test
    public void getProductsByIdTest() {
        ProductService productService = new ProductService(new InMemoryProductRepository());
        LocalDateTime now = LocalDateTime.now();

        Product product1 = new Product.Builder()
                .id(56)
                .name("Book")
                .category(Category.BOOK)
                .rating(5)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        Product product2 = new Product.Builder()
                .id(78)
                .name("Book")
                .category(Category.BOOK)
                .rating(5)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        productService.addProduct(product1);
        productService.addProduct(product2);

        assertEquals(56, product1.id());
        assertEquals(78, product2.id());

    }

    @Test
    public void SortProductCategory_alphabeticallyMovies() {
        ProductService productService = new ProductService(new InMemoryProductRepository());
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product.Builder()
                .id(5)
                .name("Clown Town")
                .category(Category.MOVIE)
                .rating(5)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        Product p2 = new Product.Builder()
                .id(6)
                .name("Bla bla bla")
                .category(Category.MOVIE)
                .rating(5)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> movies = productService.getProductsByCategorySorted(Category.MOVIE);

        assertEquals(2, movies.size());
        assertEquals("Bla bla bla", movies.get(0).name());
        assertEquals("Clown Town", movies.get(1).name());
    }

    @Test
    public void SortProductCategory_alphabeticallyBooks() {
        ProductService productService = new ProductService(new InMemoryProductRepository());
        LocalDateTime now = LocalDateTime.now();

        Product p1 = new Product.Builder()
                .id(1)
                .name("The Book of Lost Hours")
                .category(Category.BOOK)
                .rating(4)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        Product p2 = new Product.Builder()
                .id(2).name("Dominion")
                .category(Category.BOOK)
                .rating(1)
                .createdAt(now)
                .modifiedAt(now)
                .build();

        Product p3 = new Product.Builder()
                .id(3)
                .name("People Like Us")
                .category(Category.BOOK)
                .rating(3).createdAt(now)
                .modifiedAt(now)
                .build();

        Product p4 = new Product.Builder()
                .id(4)
                .name("Clown Town")
                .category(Category.BOOK)
                .rating(5).createdAt(now)
                .modifiedAt(now)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);
        productService.addProduct(p3);
        productService.addProduct(p4);

        List<Product> books = productService.getProductsByCategorySorted(Category.BOOK);

        assertEquals(4, books.size());
        assertEquals("Clown Town", books.get(0).name());
        assertEquals("Dominion", books.get(1).name());
        assertEquals("People Like Us", books.get(2).name());
    }

    @Test
    public void getProductsCreatedAfter_returnsOnlyNewerProducts() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        LocalDateTime d1 = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime d2 = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime d3 = LocalDateTime.of(2025, 1, 1, 12, 0);

        Product old = new Product.Builder()
                .id(1)
                .name("Old Book")
                .category(Category.BOOK)
                .rating(3).createdAt(d1)
                .modifiedAt(d1)
                .build();

        Product mid = new Product.Builder()
                .id(2)
                .name("Mid Book")
                .category(Category.BOOK)
                .rating(4)
                .createdAt(d2)
                .modifiedAt(d2)
                .build();

        Product recent = new Product.Builder()
                .id(3)
                .name("Recent Book")
                .category(Category.BOOK)
                .rating(5)
                .createdAt(d3)
                .modifiedAt(d3)
                .build();

        productService.addProduct(old);
        productService.addProduct(mid);
        productService.addProduct(recent);

        List<Product> products = productService.getProductsCreatedAfter(LocalDate.of(2023, 6, 1));

        assertEquals(2, products.size());
        assertTrue(products.contains(mid));
        assertTrue(products.contains(recent));
        assertFalse(products.contains(old));
    }

    @Test
    void getModifiedProducts_returnsOnlyWhenDatesDiffer() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        LocalDateTime created = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime modified = LocalDateTime.of(2023, 6, 1, 12, 0);

        Product p1 = new Product.Builder()
                .id(1)
                .name("Unchanged Book")
                .category(Category.BOOK)
                .rating(3)
                .createdAt(created)
                .modifiedAt(created)
                .build();

        Product p2 = new Product.Builder()
                .id(2)
                .name("Changed Book")
                .category(Category.BOOK)
                .rating(4)
                .createdAt(created)
                .modifiedAt(modified)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> modifiedProducts = productService.getModifiedProducts();

        assertEquals(1, modifiedProducts.size());
        assertTrue(modifiedProducts.contains(p2));
        assertFalse(modifiedProducts.contains(p1));
    }

    @Test
    void getProductsCreatedAfter_noProductsNewer_returnsEmptyList() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        LocalDateTime d1 = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime d2 = LocalDateTime.of(2023, 2, 1, 12, 0);

        Product p1 = new Product.Builder().id(1)
                .name("Old Book 1")
                .category(Category.BOOK)
                .rating(3).createdAt(d1)
                .modifiedAt(d1)
                .build();
        Product p2 = new Product.Builder()
                .id(2).name("Old Book 2")
                .category(Category.BOOK)
                .rating(4).createdAt(d2)
                .modifiedAt(d2).build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> products = productService.getProductsCreatedAfter(LocalDate.of(2023, 6, 1));

        assertTrue(products.isEmpty(), "List should be empty if no products are newer than given date");
    }

    @Test
    void getModifiedProducts_noModifiedProducts_returnsEmptyList() {
        ProductService productService = new ProductService(new InMemoryProductRepository());

        LocalDateTime created = LocalDateTime.of(2023, 1, 1, 12, 0);

        Product p1 = new Product.Builder()
                .id(1).name("Book 1")
                .category(Category.BOOK)
                .rating(3).createdAt(created)
                .modifiedAt(created)
                .build();

        Product p2 = new Product.Builder()
                .id(2).name("Book 2")
                .category(Category.BOOK)
                .rating(4).createdAt(created)
                .modifiedAt(created)
                .build();

        productService.addProduct(p1);
        productService.addProduct(p2);

        List<Product> modifiedProducts = productService.getModifiedProducts();

        assertTrue(modifiedProducts.isEmpty(), "List should be empty if no products are modified");
    }

}

