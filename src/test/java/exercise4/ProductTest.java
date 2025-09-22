package exercise4;

import exersice4.enteties.Category;
import exersice4.enteties.Product;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductTest {
    @Test
    void testProductConstructorSetName() {
        Product product = new Product("Rose soap");
        LocalDateTime now = LocalDateTime.now();
        assertEquals("Rose soap", product.getName());
        assertEquals(LocalDateTime.now().getYear(), product.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), product.getCreatedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), product.getCreatedDate().getDayOfMonth());
        assertNotNull(product.getId());
    }

    @Test
    void testProductConstructorSetAll() {
        Product product = new Product("Coconut Shampoo", Category.Shampoo, 7);
        assertEquals("Coconut Shampoo", product.getName());
        assertEquals(Category.Shampoo, product.getCategory());
        assertEquals(7, product.getRating());
        assertEquals(LocalDateTime.now().getYear(), product.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), product.getCreatedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), product.getCreatedDate().getDayOfMonth());
        assertNotNull(product.getId());
    }

    @Test
    void testGetIdSuccess() {
        Product product = new Product("Coconut Shampoo");
        assertNotNull(product.getId());
    }

    @Test
    void testGetNameSuccess() {
        Product product = new Product("Strawberry conditioner");
        assertEquals("Strawberry conditioner", product.getName());
    }


    @Test
    void testGetCategorySuccess() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        assertEquals(Category.Conditioner, product.getCategory());
    }


    @Test
    void testGetRatingSuccess() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        assertEquals(5, product.getRating());
    }

    @Test
    void testGetCreatedDateSuccess() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        assertEquals(LocalDateTime.now().getYear(), product.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), product.getCreatedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), product.getCreatedDate().getDayOfMonth());
    }

    @Test
    void testGetModifiedDateSuccess() {
        Product product = new Product("Rose soap");
        assertEquals(LocalDateTime.now().getYear(), product.getModifiedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), product.getModifiedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), product.getModifiedDate().getDayOfMonth());
    }
}
