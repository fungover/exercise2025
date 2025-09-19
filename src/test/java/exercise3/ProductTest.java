package exercise3;

import exercise3.enteties.Product;
import exercise3.service.Category;
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
    public void testSetNameSuccess(){
        Product product = new Product("Strawberry conditioner");
        product.setName("Honey conditioner");
        assertEquals("Honey conditioner", product.getName());
    }
    @Test
    public void testSetNameFail(){
        Product product = new Product("Strawberry conditioner");
        product.setName(null);
        assertEquals("Strawberry conditioner", product.getName());

    }
    @Test
    void testGetCategorySuccess() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        assertEquals(Category.Conditioner, product.getCategory());
    }

    @Test
    void testSetCategorySuccess() {
        Product product = new Product("Strawberry conditioner", Category.Soap, 5);
        product.setCategory(Category.Conditioner);
        assertEquals(Category.Conditioner, product.getCategory());
    }
    @Test
    void testSetCategoryFail(){
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        product.setCategory(null);
        assertEquals(Category.Conditioner, product.getCategory());
    }

    @Test
    void testGetRatingSuccess() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        assertEquals(5, product.getRating());
    }

    @Test
    void testSetRatingSuccess() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        product.setRating(3);
        assertEquals(3, product.getRating());
    }
    @Test
    void testSetRatingFail() {
        Product product = new Product("Strawberry conditioner", Category.Conditioner, 5);
        product.setRating(15);
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


    @Test
    void testSetModifiedDateSuccess() {
        Product product = new Product("Rose soap");
        product.setModifiedDate(LocalDateTime.of(2025, 10, 10, 12, 0, 0));
        assertEquals(LocalDateTime.of(2025, 10, 10, 12, 0, 0), product.getModifiedDate());
    }
}
