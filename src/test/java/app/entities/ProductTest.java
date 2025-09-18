package app.entities;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {

    @Test
    void canCreateProduct() {
    Product product = new Product(0,"", Category.FOOD, 5);
    }

    @Test
    public void canCreateProductID() {
        Product product = new Product(0,"", Category.FOOD, 5);
        assertEquals(0,product.ID());
    }

    @Test
    public void cantCreateProductID() {
        Product product = new Product(0,"", Category.FOOD, 5);
        assertEquals(0,product.ID());
    }

    @Test
    public void canCreateProductName() {
        Product product = new Product(0,"Name", Category.FOOD, 5);
        assertEquals("Name",product.name());
    }

    @Test
    public void cantCreateProductName() {
        Product product = new Product(0,"", Category.FOOD, 5);
        assertEquals("",product.name());
    }

    @Test
    public void canCreateProductCategory() {
        Product product = new Product(0,"Name",Category.FOOD, 5);
        assertEquals(Category.FOOD,product.category());
    }

    @Test
    public void cantCreateProductCategory() {
        Product product = new Product(0,"Name",null, 5);
        assertEquals(null,product.category());
    }

    @Test
    public void canCreateProductRating() {
        Product product = new Product(0,"Name",Category.FOOD, 5);
        assertEquals(5,product.rating());
    }
    @Test
    public void cantCreateProductRating() {
        IllegalArgumentException errorMessage = assertThrows(IllegalArgumentException.class,
                () -> new Product(0, "Name", Category.FOOD, 11));

        assertEquals("Rating must be between 0 and 10", errorMessage.getMessage());
    }

    @Test
    public void canCreateZonedDateTime() {
        Product product = new Product(0,"Name",Category.FOOD, 5);
        assertEquals(ZonedDateTime.now(ZoneId.of("Europe/Stockholm")),product.createdDate());
        System.out.println(product.createdDate().getZone());
    }

    @Test
    public void cantCreateZonedDateTime() {
        Product product = new Product(0,"Name",Category.FOOD, 5);
        assertEquals(ZoneId.of("Europe/Stockholm"),product.createdDate().getZone());

    }

    @Test
    public void canCreateProductDate() {
        ZonedDateTime createdDate = ZonedDateTime.of(
                2025, 9, 1, 12, 0, 0, 0,
                ZoneId.of("Europe/Stockholm"));
        Product product = new Product(0,"Name",Category.FOOD, 5, createdDate, null);
        assertEquals(createdDate,product.createdDate());
        System.out.println("Created Date: "+product.createdDate());
    }

    @Test
    public void canCreateProductModifiedDate() {

        ZonedDateTime createdDate = ZonedDateTime.of(
                2025, 9, 1, 12, 0, 0, 0,
                ZoneId.of("Europe/Stockholm"));
        Product product = new Product(0,"Name",Category.FOOD, 5, createdDate, null);
        assertEquals(createdDate,product.modifiedDate());
        System.out.println("Modified Date: "+product.modifiedDate());
    }







}