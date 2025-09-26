package org.example;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;
import org.example.repository.InMemoryProductRepository;
import org.example.service.ProductService;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductService productServiceTest;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;

    @BeforeEach
    void setUp() {
        ProductRepository repository = new InMemoryProductRepository();
        productServiceTest = new ProductService(repository);

        LocalDateTime baseNow = LocalDateTime.now();
        LocalDateTime sixDaysAgo = baseNow.minusDays(6);
        LocalDateTime fourDaysAgo = baseNow.minusDays(4);
        LocalDateTime oneDayAgo = baseNow.minusDays(1);

        product1 = new Product.Builder()
                .id("1")
                .name("Super Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(4)
                .createdDate(sixDaysAgo)
                .build();

        product2 = new Product.Builder()
                .id("2")
                .name("Dune Awakening")
                .category(Category.GAMES)
                .rating(10)
                .createdDate(oneDayAgo)
                .build();

        product3 = new Product.Builder()
                .id("3")
                .name("Valheim")
                .category(Category.GAMES)
                .rating(10)
                .createdDate(fourDaysAgo)
                .build();

        product4 = new Product.Builder()
                .id("4")
                .name("XGaming Chair")
                .category(Category.FURNITURE)
                .rating(6)
                .createdDate(oneDayAgo)
                .build();

        product5 = new Product.Builder()
                .id("5")
                .name("Gaming Chair")
                .category(Category.FURNITURE)
                .rating(8)
                .createdDate(oneDayAgo)
                .build();

        productServiceTest.addProduct(product1);
        productServiceTest.addProduct(product2);
        productServiceTest.addProduct(product3);
        productServiceTest.addProduct(product4);
        productServiceTest.addProduct(product5);
    }

    @Test
     void testingAddProduct() {
        Product addedProduct = new Product.Builder()
                .id("6")
                .name("Table")
                .category(Category.FURNITURE)
                .rating(3)
                .createdDate(LocalDateTime.now())
                .build();

        productServiceTest.addProduct(addedProduct);
        assertEquals(6, productServiceTest.getAllProducts().size());
     }

    @Test
    void testingAddProductAddingDuplicateProductTestFailure() {
        Product duplicateProduct = new Product.Builder()
                .id("1")
                .name("Super Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(4)
                .createdDate(LocalDateTime.now().minusDays(1))
                .build();

        assertThrows(IllegalArgumentException.class, () -> productServiceTest.addProduct(duplicateProduct));
    }

     @Test
     void testingUpdateProduct() {
         productServiceTest.updateProduct("1", "Newer Gaming Laptop", Category.ELECTRONICS, 8);
         Product updatedProduct = productServiceTest.getProductById("1");

         assertEquals("1", updatedProduct.id());
         assertEquals("Newer Gaming Laptop", updatedProduct.name());
         assertEquals(Category.ELECTRONICS, updatedProduct.category());
         assertEquals(8, updatedProduct.rating());
     }

     @Test
     void testingUpdateProductNotFoundTestFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            productServiceTest.updateProduct("999", "Failing Product", Category.GAMES, 1);
        });
     }

     @Test
     void testingGetAllProducts() {
        List<Product> allProducts = productServiceTest.getAllProducts();
        assertEquals(5, allProducts.size());
     }

     @Test
    void testingAllProductsUnmodifiableTestFailure() {
        List<Product> allProducts = productServiceTest.getAllProducts();
        assertThrows(UnsupportedOperationException.class, () -> {
            allProducts.add(product1);
        });
    }

    @Test
    void getProductById() {
        Product product = productServiceTest.getProductById("1");
        assertEquals("1", product.id());
        assertEquals("Super Gaming Laptop", product.name());
        assertEquals(Category.ELECTRONICS, product.category());
        assertEquals(4, product.rating());
    }

    @Test
    void getProductByIdNotFoundTestFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            productServiceTest.getProductById("999");});
    }

    @Test
    void getProductByCategorySorted() {
        List<Product> sortedProducts = productServiceTest.getProductsByCategorySorted(Category.FURNITURE);
        assertEquals(2, sortedProducts.size());
        assertEquals("Gaming Chair",  sortedProducts.getFirst().name());
    }

    @Test
    void getProductByCategorySortedEmptyTestFailure() {
        List<Product> emptyList = productServiceTest.getProductsByCategorySorted(Category.MOVIES);
        assertTrue(emptyList.isEmpty(), "Should be empty");
    }

    @Test
    void getProductsCreatedAfter() {
        List<Product> recentProducts = productServiceTest.getProductsCreatedAfter(LocalDateTime.now().minusDays(3));
        assertEquals(3, recentProducts.size());
    }

    @Test
    void getProductsCreatedAfterEmptyTestFailure() {
        List<Product> emptyList = productServiceTest.getProductsCreatedAfter(LocalDateTime.now());
        assertTrue(emptyList.isEmpty(), "Should be empty");
    }

    @Test
    void getModifiedProducts() {
        productServiceTest.updateProduct("1", "Newer Gaming Laptop", Category.ELECTRONICS, 8);
        List<Product> modifiedProducts = productServiceTest.getModifiedProducts();
        assertEquals(1, modifiedProducts.size());
        assertEquals("Newer Gaming Laptop", modifiedProducts.getFirst().name());

    }

    @Test
    void getModifiedProductsEmptyTestFailure() {
        List<Product> emptyList = productServiceTest.getModifiedProducts();
        assertTrue(emptyList.isEmpty(), "Should be empty");
    }
}