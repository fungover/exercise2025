package org.example;

import org.fungover.entities.Category;
import org.fungover.entities.Product;
import org.fungover.warehouse.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    void addProduct_success() {
        Product p = new Product("Mouse", Category.ELECTRONICS, 6);
        warehouse.addProduct(p);

        List<Product> all = warehouse.getAllProducts();
        assertEquals(1, all.size());
        assertEquals("Mouse", all.get(0).name());
    }

    @Test
    void addProduct_afterUpdateListIsUnmodifiable_failure() {
        Product p = new Product("Table", Category.FURNITURE, 5);
        warehouse.addProduct(p);
        warehouse.updateProduct(p.identifier(), "Table", Category.FURNITURE, 5);

        Product another = new Product("Lamp", Category.FURNITURE, 6);
        assertThrows(UnsupportedOperationException.class, () -> warehouse.addProduct(another));
    }

    @Test
    void getProduct_success() {
        Product p = new Product("Chair", Category.FURNITURE, 7);
        warehouse.addProduct(p);

        Product found = warehouse.getProduct(p.identifier());
        assertEquals("Chair", found.name());
        assertEquals(p.identifier(), found.identifier());
    }

    @Test
    void getProduct_notFound_throws() {
        assertThrows(java.util.NoSuchElementException.class, () -> warehouse.getProduct("missingID"));
    }

    @Test
    void updateProduct_success() {
        Product p = new Product("Phone", Category.ELECTRONICS, 7);
        warehouse.addProduct(p);

        warehouse.updateProduct(p.identifier(), "Smartphone", Category.ELECTRONICS, 9);

        Product updated = warehouse.getProduct(p.identifier());
        assertEquals("Smartphone", updated.name());
        assertEquals(9, updated.rating());
        assertEquals(p.identifier(), updated.identifier());
        assertEquals(p.createdDate(), updated.createdDate());
        assertTrue(!updated.lastModifiedDate().isBefore(p.lastModifiedDate()));
    }

    @Test
    void updateProduct_idNotFound_noChange() {
        Product p1 = new Product("A", Category.OTHER, 1);
        Product p2 = new Product("B", Category.OTHER, 2);
        warehouse.addProduct(p1);
        warehouse.addProduct(p2);

        warehouse.updateProduct("missingID", "X", Category.ELECTRONICS, 3);

        List<Product> all = warehouse.getAllProducts();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> p.identifier().equals(p1.identifier()) && p.name().equals("A")));
        assertTrue(all.stream().anyMatch(p -> p.identifier().equals(p2.identifier()) && p.name().equals("B")));
    }

    @Test
    void getProductsByCategory_sortedByName_caseInsensitive_success() {
        warehouse.addProduct(new Product("camera", Category.ELECTRONICS, 8));
        warehouse.addProduct(new Product("Adapter", Category.ELECTRONICS, 5));
        warehouse.addProduct(new Product("Sofa", Category.FURNITURE, 7));

        var electronicsSorted = warehouse.getProductsByCategory(Category.ELECTRONICS);
        assertEquals(List.of("Adapter", "camera"), electronicsSorted.stream().map(Product::name).toList());
    }

    @Test
    void getProductsCreatedAfter_success() throws InterruptedException {
        Product early = new Product("Old", Category.OTHER, 4);
        warehouse.addProduct(early);

        Thread.sleep(2);
        Product later = new Product("New", Category.OTHER, 6);
        warehouse.addProduct(later);

        var afterYesterday = warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(1));
        assertTrue(afterYesterday.size() >= 2);

        var afterTodayStart = warehouse.getProductsCreatedAfter(LocalDate.now());
        assertTrue(afterTodayStart.size() >= 1);
    }

    @Test
    void getProductsCreatedAfter_nullDate_throwsNPE() {
        assertThrows(NullPointerException.class, () -> warehouse.getProductsCreatedAfter(null));
    }

    @Test
    void getModifiedProducts_unmodifiedEmpty_thenUpdatedShowsUp() throws InterruptedException {
        Product p = new Product("Lamp", Category.FURNITURE, 6);
        warehouse.addProduct(p);

        assertTrue(warehouse.getModifiedProducts().isEmpty());

        Thread.sleep(5);

        warehouse.updateProduct(p.identifier(), "Lamp v2", Category.FURNITURE, 7);
        var modified = warehouse.getModifiedProducts();

        assertEquals(1, modified.size());
        assertEquals("Lamp v2", modified.get(0).name());
    }
}