package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;
import Exercise3.Entities.ProductBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void canCreateNewProduct() {

        Product product = new ProductBuilder().setId("Ella").setName("Flared jeans").setCategory(Category.JEANS).setRating(8).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct();
        assertEquals("Ella", product.id());
        assertEquals("Flared jeans", product.name());
        assertEquals(Category.JEANS, product.category());
        assertEquals(8, product.rating());
        assertEquals(LocalDate.now(),product.createdDate());
        assertEquals(LocalDate.now(),product.modifiedDate());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfRatingIsOutOfRange() {

        assertThrows(IllegalArgumentException.class, ()-> new ProductBuilder().setId("Ella").setName("Flared jeans").setCategory(Category.JEANS).setRating(12).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfRatingIsNegative() {

        assertThrows(IllegalArgumentException.class, ()-> new ProductBuilder().setId("Ella").setName("Flared jeans").setCategory(Category.JEANS).setRating(-2).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfProductIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new ProductBuilder().setId(" ").setName("Flared jeans").setCategory(Category.JEANS).setRating(8).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
    }
}