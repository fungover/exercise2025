package entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class ProductTest {

    @Test
    void withName_updatesNameAndModifiedDate() {
        Product p1 = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        LocalDate newModified = LocalDate.now().plusDays(1);
        Product p2 = p1.withName("Cookies", newModified);

        /* My assertEquals is my assertion = how I am expecting my code to behave.
        Both considering what is supposed to be changed and what is NOT
         * This is the foundation of JUnit testing */

        // name should be updated (name -> newName)
        assertEquals("Cookies", p2.getName(), "Name should be updated");

        // modifiedDate should be updated (modifiedDate -> newModifiedDate)
        assertEquals(newModified, p2.getModifiedDate(), "ModifiedDate should be updated");

        /* NOT SUPPOSED TO CHANGE */
        assertEquals(p1.getId(), p2.getId(), "ID should not change");
        assertEquals(p1.getCategory(), p2.getCategory(), "Category should not change");
        assertEquals(p1.getRating(), p2.getRating(), "Rating should not change");
        assertEquals(p1.getCreatedDate(), p2.getCreatedDate(), "CreatedDate should not change");
    }

    @Test
    void withRating_updatesRatingAndModifiedDate() {
        Product p1 = new Product.Builder()
                .id("A-001")
                .name("Coffee")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        LocalDate newModified = LocalDate.now().plusDays(2);
        Product p2 = p1.withRating(10, newModified);

        // rating should be updated
        assertEquals(10, p2.getRating(), "Rating should be updated");

        // modifiedDate should be updated
        assertEquals(newModified, p2.getModifiedDate(), "ModifiedDate should be updated");

        /* NOT SUPPOSED TO CHANGE */
        assertEquals(p1.getId(), p2.getId(), "ID should not change");
        assertEquals(p1.getName(), p2.getName(), "Name should not change");
        assertEquals(p1.getCategory(), p2.getCategory(), "Category should not change");
        assertEquals(p1.getCreatedDate(), p2.getCreatedDate(), "CreatedDate should not change");

        assertNotSame(p1, p2, "withRating should return a new instance");
    }

    @Test
    void withCategory_updatesCategoryAndModifiedDate() {
        Product p1 = new Product.Builder()
                .id("C-001")
                .name("Lego")
                .category(Category.TOYS)
                .rating(6)
                .createdDate(LocalDate.now())
                .modifiedDate(LocalDate.now())
                .build();

        LocalDate newModified = LocalDate.now().plusDays(3);
        Product p2 = p1.withCategory(Category.BOOKS, newModified);

        // category should be updated
        assertEquals(Category.BOOKS, p2.getCategory(), "Category should be updated");

        // modifiedDate should be updated
        assertEquals(newModified, p2.getModifiedDate(), "ModifiedDate should be updated");

        /* NOT SUPPOSED TO CHANGE */
        assertEquals(p1.getId(), p2.getId(), "ID should not change");
        assertEquals(p1.getName(), p2.getName(), "Name should not change");
        assertEquals(p1.getRating(), p2.getRating(), "Rating should not change");
        assertEquals(p1.getCreatedDate(), p2.getCreatedDate(), "CreatedDate should not change");

        assertNotSame(p1, p2, "withCategory should return a new instance");
    }

}
