package exercise4.service;

import exercise4.entities.Category;
import exercise4.entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product.ProductBuilder builder = new Product.ProductBuilder();

    @Test
    void canCreateNewProduct() {
        Product product = builder.setId("Ella").setName("Flared jeans").setCategory(Category.JEANS).setRating(8).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct();
        assertEquals("Ella", product.getId());
        assertEquals("Flared jeans", product.getName());
        assertEquals(Category.JEANS, product.getCategory());
        assertEquals(8, product.getRating());
        assertEquals(LocalDate.now(),product.getCreatedDate());
        assertEquals(LocalDate.now(),product.getModifiedDate());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfRatingIsOutOfRange() {

        assertThrows(IllegalArgumentException.class, ()-> builder.setId("Ella").setName("Flared jeans").setCategory(Category.JEANS).setRating(12).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfRatingIsNegative() {

        assertThrows(IllegalArgumentException.class, ()-> builder.setId("Ella").setName("Flared jeans").setCategory(Category.JEANS).setRating(-2).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfProductIsNull() {
        assertThrows(IllegalArgumentException.class, () -> builder.setId(" ").setName("Flared jeans").setCategory(Category.JEANS).setRating(8).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
    }
}