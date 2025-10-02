package app.entities;

import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    void canCreateProduct() {
    Product product = new Product.Builder().build();
    assertNotNull(product);
        System.out.println(product);
    }

    @Test
    public void canCreateProductID() {
        Product product = new Product.Builder().id(5).build();
        assertEquals(5,product.ID());
        System.out.println(product.ID());
    }

    @Test
    public void cantCreateProductID() {
        Product product = new Product.Builder().id(0).build();
        assertEquals(0,product.ID());
        System.out.println(product.ID());
    }

    @Test
    public void canCreateProductName() {
        Product product = new Product.Builder().name("Name").build();
        assertEquals("Name",product.name());
        System.out.println(product.name());
    }

    @Test
    public void cantCreateProductName() {
        Product product = new Product.Builder().name("").build();
        assertEquals("",product.name());
        System.out.println(product.name());
    }

    @Test
    public void canCreateProductCategory() {
        Product product = new Product.Builder().category(Category.FOOD).build();
        assertEquals(Category.FOOD,product.category());
        System.out.println(product.category());
    }

    @Test
    public void cantCreateProductCategory() {
        Product product = new Product.Builder().category(null).build();
        assertNull(product.category());
        System.out.println(product.category());
    }

    @Test
    public void canCreateProductRating() {
        Product product = new Product.Builder().rating(5).build();
        assertEquals(5,product.rating());
        System.out.println(product.rating());
    }

    @Test
    public void cantCreateProductRating() {
        IllegalArgumentException errorMessage = assertThrows(IllegalArgumentException.class,
                () -> new Product.Builder().rating(11).build());

        assertEquals("Rating must be between 0 and 10", errorMessage.getMessage());
        System.out.println(errorMessage.getMessage());
    }

    @Test
    public void canCreateZonedDateTime() {

        ZoneId stockholm = ZoneId.of("Europe/Stockholm");
        ZonedDateTime time = ZonedDateTime.of(2025,1,1,1,1,1,1,stockholm);

        Product product = new Product.Builder().createdDate(time).build();

        assertEquals(stockholm,product.createdDate().getZone());
        System.out.println(product.createdDate().getZone());
    }

    @Test
    public void cantCreateZonedDateTime() {
        Product product = new Product.Builder().createdDate(null).build();
        assertEquals(ZoneId.of("Europe/Stockholm"),product.createdDate().getZone());
        System.out.println(product.createdDate().getZone());
    }

    @Test
    public void canCreateProductDate() {
        ZonedDateTime createdDate = ZonedDateTime.of(
                2025, 9, 1, 12, 0, 0, 0,
                ZoneId.of("Europe/Stockholm"));
        Product product = new Product.Builder().createdDate(createdDate).build();
        assertEquals(createdDate,product.createdDate());
        System.out.println("Created Date: "+product.createdDate());
    }

    @Test
    public void canCreateProductModifiedDate() {

        ZonedDateTime createdDate = ZonedDateTime.of(
                2025, 9, 1, 12, 0, 0, 0,
                ZoneId.of("Europe/Stockholm"));
        Product product = new Product.Builder().createdDate(createdDate).build();
        assertEquals(createdDate,product.modifiedDate());
        System.out.println("Modified Date: "+product.modifiedDate());
    }







}