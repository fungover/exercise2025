package entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void withName_updatesNameAndModifiesDate() {
        Product p1 = new Product(
                "A-001",
                "Coffee", Category.FOOD,
                8,
                LocalDate.now(),
                LocalDate.now());

        LocalDate newModified = LocalDate.now().plusDays(1);
        Product p2 = p1.withName("Cookies", newModified);

        /* My assertEquals is my assertion = how I am expecting my code to behave.
         * This is the foundation of JUnit testing */

        // name should be updated
        assertEquals("Cookies", p2.name());

        // modifiedDate should be updated
        assertEquals(newModified, p2.modifiedDate());

        // ID should not change. p1 is my first product created with the constructor. withName gives it id p2- since p1 is immutable.
        assertEquals(p1.id(), p2.id());
    }

}
