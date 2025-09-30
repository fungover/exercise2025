package org.example;

import org.fungover.entities.Category;
import org.fungover.entities.Product;
import org.fungover.repository.InMemoryProductRepository;
import org.fungover.repository.ProductRepository;
import org.fungover.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    private ProductService service;

    @BeforeEach
    void setUp() {
        ProductRepository repo = new InMemoryProductRepository();
        service = new ProductService(repo);
    }

    private static Product product(String name, Category category, int rating) {
        return new Product.Builder()
                .name(name)
                .category(category)
                .rating(rating)
                .build();
    }

    @Test
    void addProduct_success() {
        Product p = product("Mouse", Category.ELECTRONICS, 6);
        service.addProduct(p);

        List<Product> all = service.getAllProducts();
        assertEquals(1, all.size());
        assertEquals("Mouse", all.get(0).name());
    }

    @Test
    void addProduct_afterUpdateListIsUnmodifiable_failure() {
        Product p = product("Table", Category.FURNITURE, 5);
        service.addProduct(p);
        service.updateProduct(p.identifier(), "Table", Category.FURNITURE, 5);

        Product another = product("Lamp", Category.FURNITURE, 6);
        assertThrows(UnsupportedOperationException.class, () -> service.addProduct(another));
    }

    @Test
    void getProduct_success() {
        Product p = product("Chair", Category.FURNITURE, 7);
        service.addProduct(p);

        Product found = service.getProduct(p.identifier()).orElseThrow();
        assertEquals("Chair", found.name());
        assertEquals(p.identifier(), found.identifier());
    }

    @Test
    void getProduct_notFound_throws() {
        assertTrue(service.getProduct("missingID").isEmpty());
    }

    @Test
    void updateProduct_success() throws InterruptedException {
        Product p = product("Phone", Category.ELECTRONICS, 7);
        service.addProduct(p);

        Product before = service.getProduct(p.identifier()).orElseThrow();
        var createdBefore = before.createdDate();
        var lastModifiedBefore = before.lastModifiedDate();

        Thread.sleep(2);

        service.updateProduct(p.identifier(), "Smartphone", Category.ELECTRONICS, 9);

        Product updated = service.getProduct(p.identifier()).orElseThrow();
        assertEquals("Smartphone", updated.name());
        assertEquals(9, updated.rating());
        assertEquals(p.identifier(), updated.identifier());
        assertEquals(createdBefore, updated.createdDate());
        assertTrue(!updated.lastModifiedDate().isBefore(lastModifiedBefore));
    }

    @Test
    void updateProduct_idNotFound_noChange() {
        Product p1 = product("A", Category.OTHER, 1);
        Product p2 = product("B", Category.OTHER, 2);
        service.addProduct(p1);
        service.addProduct(p2);

        service.updateProduct("missingID", "X", Category.ELECTRONICS, 3);

        List<Product> all = service.getAllProducts();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> p.identifier().equals(p1.identifier()) && p.name().equals("A")));
        assertTrue(all.stream().anyMatch(p -> p.identifier().equals(p2.identifier()) && p.name().equals("B")));
    }

    @Test
    void getProductsByCategory_sortedByName_caseInsensitive_success() {
        service.addProduct(product("camera", Category.ELECTRONICS, 8));
        service.addProduct(product("Adapter", Category.ELECTRONICS, 5));
        service.addProduct(product("Sofa", Category.FURNITURE, 7));

        var electronicsSorted = service.getProductsByCategory(Category.ELECTRONICS);
        assertEquals(List.of("Adapter", "camera"), electronicsSorted.stream().map(Product::name).toList());
    }

    @Test
    void getProductsCreatedAfter_success() throws InterruptedException {
        Product early = product("Old", Category.OTHER, 4);
        service.addProduct(early);

        Thread.sleep(2);

        Product later = product("New", Category.OTHER, 6);
        service.addProduct(later);

        var afterYesterday = service.getProductsCreatedAfter(LocalDate.now().minusDays(1));
        assertTrue(afterYesterday.size() >= 2);

        var afterTodayStart = service.getProductsCreatedAfter(LocalDate.now());
        assertTrue(afterTodayStart.size() >= 1);
    }

    @Test
    void getProductsCreatedAfter_nullDate_throwsNPE() {
        assertThrows(NullPointerException.class, () -> service.getProductsCreatedAfter(null));
    }

    @Test
    void getModifiedProducts_unmodifiedEmpty_thenUpdatedShowsUp() throws InterruptedException {
        Product p = product("Lamp", Category.FURNITURE, 6);
        service.addProduct(p);

        assertTrue(service.getModifiedProducts().isEmpty());

        Thread.sleep(5);

        service.updateProduct(p.identifier(), "Lamp v2", Category.FURNITURE, 7);
        var modified = service.getModifiedProducts();

        assertEquals(1, modified.size());
        assertEquals("Lamp v2", modified.get(0).name());
    }
}