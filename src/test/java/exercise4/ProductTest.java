package exercise4;

import exersice4.enteties.Category;
import exersice4.enteties.Product;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductTest {
    @Test
    void testGetIdSuccess() {
        Product product = new Product.Builder().setName("Coconut shampoo").build();
        assertNotNull(product.getId());
    }

    @Test
    void testGetNameSuccess() {
        Product product = new Product.Builder()
                .setName("Strawberry conditioner")
                .build();
        assertEquals("Strawberry conditioner", product.getName());
    }

    @Test
    void testGetCategorySuccess() {;
        Product product = new Product.Builder()
                .setName("Strawberry conditioner")
                .setCategory(Category.Conditioner)
                .setRating(5)
                .build();
        assertEquals(Category.Conditioner, product.getCategory());
    }

    @Test
    void testGetRatingSuccess() {
        Product product = new Product.Builder()
                .setName("Strawberry conditioner")
                .setCategory(Category.Conditioner)
                .setRating(5)
                .build();
        assertEquals(5, product.getRating());
    }

    @Test
    void testGetCreatedDateSuccess() {
        Product product = new Product.Builder()
                .setName("Strawberry conditioner")
                .setCategory(Category.Conditioner)
                .setRating(5)
                .build();
        assertEquals(LocalDateTime.now().getYear(), product.getCreatedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), product.getCreatedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), product.getCreatedDate().getDayOfMonth());
    }

    @Test
    void testGetModifiedDateSuccess() {
        Product product = new Product.Builder()
                .setName("Rose soap")
                .build();
        assertEquals(LocalDateTime.now().getYear(), product.getModifiedDate().getYear());
        assertEquals(LocalDateTime.now().getMonth(), product.getModifiedDate().getMonth());
        assertEquals(LocalDateTime.now().getDayOfMonth(), product.getModifiedDate().getDayOfMonth());
    }
}
