package entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    void withName_updatesNameAndModifiedDate() {
        Product p1 = new Product(
                "A-001",
                "Coffee", Category.FOOD,
                8,
                LocalDate.now(),
                LocalDate.now());

        LocalDate newModified = LocalDate.now().plusDays(1);
        Product p2 = p1.withName("Cookies", newModified);

        /* My assertEquals is my assertion = how I am expecting my code to behave.
        Both considering what is supposed to be changed and what is NOT
         * This is the foundation of JUnit testing */

        // name should be updated (name -> newName)
        assertEquals("Cookies", p2.name(), "Name should be updated");

        // modifiedDate should be updated (modifiedDate -> newModifiedDate)
        assertEquals(newModified, p2.modifiedDate(), "ModifiedDate should be updated");

        /* NOT SUPPOSED TO CHANGE */
        assertEquals(p1.id(), p2.id(), "ID should not change");
        assertEquals(p1.category(), p2.category(), "Category should not change");
        assertEquals(p1.rating(), p2.rating(), "Rating should not change");
        assertEquals(p1.createdDate(), p2.createdDate(), "CreatedDate should not change");
    }

    @Test
    void withRating_updatesRatingAndModifiedDate() {
        Product p1 = new Product(
                "B-001",
                "Bike Helmet", Category.SPORTS,
                7,
                LocalDate.now(),
                LocalDate.now());

        LocalDate newModified = LocalDate.now().plusDays(2);
        Product p2 = p1.withRating(10, newModified);

        // rating should be updated
        assertEquals(10, p2.rating(), "Rating should be updated");

        // modifiedDate should be updated
        assertEquals(newModified, p2.modifiedDate(), "ModifiedDate should be updated");

        /* NOT SUPPOSED TO CHANGE */
        assertEquals(p1.id(), p2.id(), "ID should not change");
        assertEquals(p1.name(), p2.name(), "Name should not change");
        assertEquals(p1.category(), p2.category(), "Category should not change");
        assertEquals(p1.createdDate(), p2.createdDate(), "CreatedDate should not change");
    }

}
