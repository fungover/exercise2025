package exercise4;


import exersice4.enteties.Category;
import exersice4.enteties.Product;
import exersice4.service.Warehouse;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WarehouseTest {
    @Test
    void testAddProductSuccess() {
        Product product = new Product("Rose soap");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        assertEquals(1, warehouse.getAllProducts().size());
        assertEquals(product, warehouse.getAllProducts().getFirst());
    }

    @Test
    void testAddProductFail() {
        Product product = new Product(null);
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        assertEquals(0, warehouse.getAllProducts().size());
    }

    @Test
    void testUpdateProductSuccess() {
        Product product = new Product("Rose soap");
        String id = product.getId();
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.updateProduct(id, "Pink soap", Category.Soap, 8, product.getCreatedDate());
        assertEquals("Pink soap", warehouse.getAllProducts().getFirst().getName());
        assertEquals(Category.Soap, warehouse.getAllProducts().getFirst().getCategory());
        assertEquals(8, warehouse.getAllProducts().getFirst().getRating());
        assertEquals(LocalDateTime.now().getYear(), warehouse.getAllProducts().getFirst().getModifiedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), warehouse.getAllProducts().getFirst().getModifiedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), warehouse.getAllProducts().getFirst().getModifiedDate().getDayOfMonth());
    }

    @Test
    void testUpdateProductFail() {
        Product product = new Product("Rose soap");
        String id = "tre";
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.updateProduct(id, "Pink soap", Category.Soap, 8, product.getCreatedDate());
        assertEquals("Rose soap", warehouse.getAllProducts().getFirst().getName());
        assertEquals(product.getCreatedDate(), product.getModifiedDate());
    }

    @Test
    void testGetAllProductsSuccess() {
        Product product = new Product("Rose soap");
        Product product2 = new Product("Lavender soap");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.addProduct(product2);
        List<Product> allProducts = warehouse.getAllProducts();
        assertEquals(2, allProducts.size());
    }

    @Test
    void testGetAllProductsFail() {
        Warehouse warehouse = new Warehouse();
        List<Product> allProducts = warehouse.getAllProducts();
        assertEquals(0, allProducts.size());
    }

    @Test
    void testGetProductByIdSuccess() {
        Product product = new Product("Rose soap");
        String id = product.getId();
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        Product product2 = warehouse.getProductById(id);
        assertEquals("Rose soap", product2.getName());
    }
    @Test
    void testGetProductByIdFail() {
        Warehouse warehouse = new Warehouse();
        Product product = warehouse.getProductById("tre");
        assertNull(product);
    }
    @Test
    void testGetProductsByCategorySortedSuccess(){
        Product product = new Product("Rose soap", Category.Soap, 8);
        Product product2 = new Product("Lavender soap", Category.Soap, 5);
        Product product3 = new Product("Vanilla shampoo", Category.Shampoo, 4);
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        List<Product> productsInCategory = warehouse.getProductsByCategorySorted(Category.Soap);
        assertEquals(2, productsInCategory.size());
        assertEquals("Lavender soap", productsInCategory.getFirst().getName());
    }
    @Test
    void testGetProductsByCategorySortFail(){
        Warehouse warehouse = new Warehouse();
        Product product = new Product("Vanilla shampoo", Category.Shampoo, 4);
        warehouse.addProduct(product);
        List<Product> productsInCategory = warehouse.getProductsByCategorySorted(Category.Soap);
        assertEquals(0, productsInCategory.size());
    }
    @Test
    void testGetProductsCreatedAfterSuccess(){
        Product product = new Product("Rose soap");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        LocalDateTime date = LocalDateTime.of(2025,6,10, 12, 0, 0);
        List<Product> productsCreatedAfter = warehouse.getProductsCreatedAfter(date);
        assertEquals(1, productsCreatedAfter.size());
    }
    @Test
    void testGetProductsCreatedAfterFail(){
        Warehouse warehouse = new Warehouse();
        LocalDateTime date = LocalDateTime.of(2025,6,10, 12, 0, 0);
        List<Product> productsCreatedAfter = warehouse.getProductsCreatedAfter(date);
        assertEquals(0, productsCreatedAfter.size());
    }
    @Test
    void testGetModifiedProductsSuccess(){
        Product product = new Product("Rose soap", Category.Shampoo, 7, LocalDateTime.of(2025,6,10, 12, 0, 0));
        Product product2 = new Product("Lavender soap");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.addProduct(product2);
        warehouse.updateProduct(product.getId(), "Pink soap", Category.Soap, 8, product.getCreatedDate());
        /*LocalDateTime modifiedDate = LocalDateTime.of(2025,12,12, 12,0,0);
        product.setModifiedDate(modifiedDate);*/
        List<Product> modifiedProducts = warehouse.getModifiedProducts();
        assertEquals(1, modifiedProducts.size());
    }
    @Test
    void testGetModifiedProductsFail(){
        Warehouse warehouse = new Warehouse();
        Product product = new Product("Rose soap");
        warehouse.addProduct(product);
        List<Product> modifiedProducts = warehouse.getModifiedProducts();
        assertEquals(0, modifiedProducts.size());
    }
}
