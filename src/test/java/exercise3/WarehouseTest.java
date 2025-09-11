package exercise3;

import exercise3.enteties.Product;
import exercise3.service.Category;
import exercise3.service.Warehouse;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static exercise3.service.Category.Shampoo;
import static exercise3.service.Category.Soap;
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
        warehouse.updateProduct(id, "Pink soap", Category.Soap, 8);
        assertEquals("Pink soap", warehouse.getAllProducts().getFirst().getName());
        assertEquals(Soap, warehouse.getAllProducts().getFirst().getCategory());
        assertEquals(8, warehouse.getAllProducts().getFirst().getRating());
        assertEquals(LocalDate.now(), warehouse.getAllProducts().getFirst().getModifiedDate());
    }

    @Test
    void testUpdateProductFail() {
        Product product = new Product("Rose soap");
        String id = "tre";
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.updateProduct(id, "Pink soap", Category.Soap, 8);
        assertEquals("Rose soap", warehouse.getAllProducts().getFirst().getName());
        //assertEquals(Soap, warehouse.getAllProducts().getFirst().getCategory());
        //assertEquals(8, warehouse.getAllProducts().getFirst().getRating());
        //assertEquals(LocalDate.now(), warehouse.getAllProducts().getFirst().getModifiedDate());
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
        assertNull(allProducts);
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
        Product product = new Product("Rose soap", Soap, 8);
        Product product2 = new Product("Lavender soap", Soap, 5);
        Product product3 = new Product("Vanilla shampoo", Shampoo, 4);
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        List<Product> productsInCategory = warehouse.getProductsByCategorySorted(Soap);
        assertEquals(2, productsInCategory.size());
        assertEquals("Lavender soap", productsInCategory.getFirst().getName());
    }
    @Test
    void testGetProductsByCategorySortFail(){
        Warehouse warehouse = new Warehouse();
        Product product = new Product("Vanilla shampoo", Shampoo, 4);
        warehouse.addProduct(product);
        List<Product> productsInCategory = warehouse.getProductsByCategorySorted(Soap);
        assertNull(productsInCategory);
    }
    @Test
    void testGetProductsCreatedAfterSuccess(){
        Product product = new Product("Rose soap");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        LocalDate date = LocalDate.of(2025,6,10);
        List<Product> productsCreatedAfter = warehouse.getProductsCreatedAfter(date);
        assertEquals(1, productsCreatedAfter.size());
    }
    @Test
    void testGetProductsCreatedAfterFail(){
        Warehouse warehouse = new Warehouse();
        LocalDate date = LocalDate.of(2025,6,10);
        List<Product> productsCreatedAfter = warehouse.getProductsCreatedAfter(date);
        assertNull(productsCreatedAfter);
    }
    @Test
    void testGetModifiedProductsSuccess(){
        Product product = new Product("Rose soap");
        Product product2 = new Product("Lavender soap");
        Warehouse warehouse = new Warehouse();
        warehouse.addProduct(product);
        warehouse.addProduct(product2);
        warehouse.updateProduct(product.getId(), "Pink soap", Soap, 8);
        LocalDate modifiedDate = LocalDate.of(2025,12,12);
        product.setModifiedDate(modifiedDate);
        List<Product> modifiedProducts = warehouse.getModifiedProducts();
        assertEquals(1, modifiedProducts.size());
    }
    @Test
    void testGetModifiedProductsFail(){
        Warehouse warehouse = new Warehouse();
        Product product = new Product("Rose soap");
        warehouse.addProduct(product);
        List<Product> modifiedProducts = warehouse.getModifiedProducts();
        assertNull(modifiedProducts);
    }
}