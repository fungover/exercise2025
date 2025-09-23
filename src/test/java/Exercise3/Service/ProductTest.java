package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void canCreateNewProduct() {

        Product product = new Product("Ella", "Flared jeans", Category.JEANS,8, LocalDate.now(), LocalDate.now());
        assertEquals("Ella", product.id());
        assertEquals("Flared jeans", product.name());
        assertEquals(Category.JEANS, product.category());
        assertEquals(8, product.rating());
        assertEquals(LocalDate.now(),product.createdDate());
        assertEquals(LocalDate.now(),product.modifiedDate());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfRatingIsOutOfRange() {

        assertThrows(IllegalArgumentException.class, ()->  new Product("Ella", "Flared jeans", Category.JEANS,12,LocalDate.now(), LocalDate.now()));
    }

    @Test
    public void throwsIllegalArgumentExceptionIfRatingIsNegative() {

        assertThrows(IllegalArgumentException.class, ()->  new Product("Ella", "Flared jeans", Category.JEANS,-2,LocalDate.now(), LocalDate.now()));
    }

    @Test
    public void throwsIllegalArgumentExceptionIfProductIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Product(" ", "Flared jeans", Category.JEANS,8,LocalDate.now(), LocalDate.now()));
    }
}