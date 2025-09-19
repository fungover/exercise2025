package org.example;
import org.example.entities.Category;
import org.example.entities.Product;
import org.example.service.Warehouse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;
    private Product product1;
    private Product product2;
    private Product product3;
    private Product product4;
    private Product product5;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();

        LocalDateTime baseNow = LocalDateTime.now();
        LocalDateTime sixDaysAgo = baseNow.minusDays(6);
        LocalDateTime fourDaysAgo = baseNow.minusDays(4);
        LocalDateTime oneDayAgo = baseNow.minusDays(1);

        product1 = new Product(
                "1",
                "Super Gaming Laptop",
                Category.ELECTRONICS,
                4,
                sixDaysAgo,
                null
        );

        product2 = new Product(
                "2",
                "Dune Awakening",
                Category.GAMES,
                10,
                oneDayAgo,
                null
        );

        product3 = new Product(
                "3",
                "Valheim",
                Category.GAMES,
                10,
                fourDaysAgo,
                null
        );

        product4 = new Product(
                "4",
                "XGaming Chair",
                Category.FURNITURE,
                6,
                oneDayAgo,
                null
        );

        product5 = new Product(
                "5",
                "Gaming Chair",
                Category.FURNITURE,
                8,
                oneDayAgo,
                null
        );

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        warehouse.addProduct(product5);
    }

    @Test
     void testingAddProduct() {
        Product addedProduct = new Product(
                "6",
                "Table",
                Category.FURNITURE,
                3,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        warehouse.addProduct(addedProduct);
        assertEquals(6, warehouse.getAllProducts().size());
     }

     @Test
     void testingAddProductAddingDuplicateProductTestFailure() {
        Product duplicateProduct = new Product(
                "1",
                "Super Gaming Laptop",
                Category.ELECTRONICS,
                4,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().minusDays(1)
        );
        assertThrows(IllegalArgumentException.class, () -> warehouse.addProduct(duplicateProduct));
     }

     @Test
     void testingUpdateProduct() {
         warehouse.updateProduct("1", "Newer Gaming Laptop", Category.ELECTRONICS, 8);
         Product updatedProduct = warehouse.getProductById("1");

         assertEquals("1", updatedProduct.id());
         assertEquals("Newer Gaming Laptop", updatedProduct.name());
         assertEquals(Category.ELECTRONICS, updatedProduct.category());
         assertEquals(8, updatedProduct.rating());
     }

     @Test
     void testingUpdateProductNotFoundTestFailure() {
        assertThrows(IllegalArgumentException.class, () -> {
            warehouse.updateProduct("999", "Failing Product", Category.GAMES, 1);
        });
     }

     @Test
     void testingGetAllProducts() {
        List<Product> allProducts = warehouse.getAllProducts();
        assertEquals(5, allProducts.size());
     }

     @Test
    void testingAllProductsUnmodifiableTestFailure() {
        List<Product> allProducts = warehouse.getAllProducts();
        assertThrows(UnsupportedOperationException.class, () -> {
            allProducts.add(product1);
        });
    }

    @Test
    void getProductById() {
        Product product = warehouse.getProductById("1");
        assertEquals("1", product.id());
        assertEquals("Super Gaming Laptop", product.name());
        assertEquals(Category.ELECTRONICS, product.category());
        assertEquals(4, product.rating());
    }

    @Test
    void getProductByIdNotFoundTestFailure() {
        assertThrows(IllegalArgumentException.class, () -> {warehouse.getProductById("999");});
    }

    @Test
    void getProductByCategorySorted() {
        List<Product> sortedProducts = warehouse.getProductsByCategorySorted(Category.FURNITURE);
        assertEquals(2, sortedProducts.size());
        assertEquals("Gaming Chair",  sortedProducts.getFirst().name());
    }

    @Test
    void getProductByCategorySortedEmptyTestFailure() {
        List<Product> emptyList = warehouse.getProductsByCategorySorted(Category.MOVIES);
        assertTrue(emptyList.isEmpty(), "Should be empty");
    }

    @Test
    void getProductsCreatedAfter() {
        List<Product> recentProducts = warehouse.getProductsCreatedAfter(LocalDateTime.now().minusDays(3));
        assertEquals(3, recentProducts.size());
    }

    @Test
    void getProductsCreatedAfterEmptyTestFailure() {
        List<Product> emptyList = warehouse.getProductsCreatedAfter(LocalDateTime.now());
        assertTrue(emptyList.isEmpty(), "Should be empty");
    }

    @Test
    void getModifiedProducts() {
        warehouse.updateProduct("1", "Newer Gaming Laptop", Category.ELECTRONICS, 8);
        List<Product> modifiedProducts = warehouse.getModifiedProducts();
        assertEquals(1, modifiedProducts.size());
        assertEquals("Newer Gaming Laptop", modifiedProducts.getFirst().name());

    }

    @Test
    void getModifiedProductsEmptyTestFailure() {
        List<Product> emptyList = warehouse.getModifiedProducts();
        assertTrue(emptyList.isEmpty(), "Should be empty");
    }
}