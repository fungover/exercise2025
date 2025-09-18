package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseTest {

    private Warehouse warehouse;
    private final LocalDate today = LocalDate.now();

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }


    @Test
    void addProduct_storesProductInWarehouse() {
        Product tv = Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        warehouse.addProduct(tv);

        // Kontrollera att produkten finns i lagret
        List<Product> allProducts = warehouse.getAllProducts();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts).contains(tv);
    }

    @Test
    void addProduct_nullProduct_throwsException() {
        assertThatThrownBy(() -> warehouse.addProduct(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("product");
    }

    @Test
    void addProduct_duplicatedId_throwsException() {
        Product first = Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product duplicateId = Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        warehouse.addProduct(first);

        assertThatThrownBy(() -> warehouse.addProduct(duplicateId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");

    }

    @Test
    void getProductById_returnsMatchingProduct() {
        Product tv = Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        Product laptop = Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        warehouse.addProduct(tv);
        warehouse.addProduct(laptop);

        Product found = warehouse.getProductById("p-001");
        assertThat(found).isEqualTo(tv);
    }

    @Test
    void getProductById_unknownId_throwsException() {
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        assertThatThrownBy(() -> warehouse.getProductById("p-000"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id");
    }

    @Test
    void getProductsByCategorySorted_returnsAtoZByName() {
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-003")
                .name("Pro Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-004")
                .name("Another Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        var computers = warehouse.getProductsByCategorySorted(Category.COMPUTER);

        assertThat(computers)
                .extracting(Product::name)
                .containsExactly("Another Laptop", "Budget Laptop", "Pro Laptop");
    }

    @Test
    void getProductsCreatedAfter_returnsOnlyNewer() {
        var d1 = LocalDate.of(2025, 1, 1);
        var d2 = LocalDate.of(2025, 1, 2);
        var d3 = LocalDate.of(2025, 1, 3);
        var d4 = LocalDate.of(2025, 1, 4);
        var d5 = LocalDate.of(2025, 1, 5);
        var d6 = LocalDate.of(2025, 1, 6);

        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(d1)
                .modifiedDate(d1)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(d2)
                .modifiedDate(d2)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-003")
                .name("Pro Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(d3)
                .modifiedDate(d3)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-004")
                .name("Another Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(d4)
                .modifiedDate(d4)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-005")
                .name("Iphone")
                .category(Category.PHONE)
                .rating(7)
                .createdDate(d5)
                .modifiedDate(d5)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-006")
                .name("Refrigerator")
                .category(Category.APPLIANCES)
                .rating(7)
                .createdDate(d6)
                .modifiedDate(d6)
                .build());

        var afterJan3 = warehouse.getProductsCreatedAfter(LocalDate.of(2025, 1, 3));
        assertThat(afterJan3)
                .extracting(Product::id)
                .containsExactly("p-004", "p-005", "p-006");
    }

    @Test
    void getModifiedProducts_returnsWhereCreatedNotEqualModified() {
        var createdDate = LocalDate.of(2025, 1, 1);
        // CreatedDate är samma som ModifiedDate
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(createdDate)
                .modifiedDate(createdDate)
                .build());

        // CreatedDate är inte samma som ModifiedDate
        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(createdDate)
                .modifiedDate(createdDate.plusDays(1))
                .build());

        var modifiedDate = warehouse.getModifiedProducts();

        assertThat(modifiedDate)
                .extracting(Product::id)
                .containsExactly("p-002");
    }

    @Test
    void getCategoriesWithProducts_returnsOnlyCategoriesWithProducts() {
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        var categories = warehouse.getCategoriesWithProducts();

        assertThat(categories)
                .containsExactly(Category.TV, Category.COMPUTER)
                .doesNotContain(Category.APPLIANCES, Category.PHONE);
    }

    @Test
    void countProductsInCategory_returnsCorrectCount() {
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-003")
                .name("Pro Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-006")
                .name("Refrigerator")
                .category(Category.APPLIANCES)
                .rating(7)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        int tvCount = warehouse.countProductsInCategory(Category.TV);
        int computerCount = warehouse.countProductsInCategory(Category.COMPUTER);
        int appliancesCount = warehouse.countProductsInCategory(Category.APPLIANCES);

        assertThat(tvCount).isEqualTo(1);
        assertThat(computerCount).isEqualTo(2);
        assertThat(appliancesCount).isEqualTo(1);
    }

    @Test
    void countProductsInCategory_whenNoneExists_returnsZero() {
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        int phoneCount = warehouse.countProductsInCategory(Category.PHONE);

        assertThat(phoneCount).isEqualTo(0);
    }

    @Test
    void getProductInitialsMap_CountsByFirstLetter_caseInsensitive() {
        warehouse.addProduct(Product.builder()
                .id("p-001")
                .name("Samsung 55\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Budget Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-003")
                .name("Pro Laptop")
                .category(Category.COMPUTER)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-007")
                .name("Samsung 65\" 4K")
                .category(Category.TV)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .build());


        var map = warehouse.getProductInitialsMap();

        assertThat(map.get('S')).isEqualTo(2);
        assertThat(map.get('B')).isEqualTo(1);
        assertThat(map.get('P')).isEqualTo(1);

        assertThat(map.keySet()).containsExactlyInAnyOrder('S', 'B', 'P');
    }

    @Test
    void getTopRatedProductsThisMonth_returnsTopRatedCreatedThisMonth_sortedNewestFirst() {
        LocalDate fixedNow = LocalDate.of(2025, 9, 15);

        Product p1 = Product.builder()
                .id("p-001")
                .name("Best TV")
                .category(Category.TV)
                .rating(10)
                .createdDate(fixedNow.minusDays(10))
                .modifiedDate(fixedNow.minusDays(10))
                .build();

        Product p2 = Product.builder()
                .id("p-002")
                .name("Best Laptop")
                .category(Category.COMPUTER)
                .rating(10)
                .createdDate(fixedNow.minusDays(5))
                .modifiedDate(fixedNow.minusDays(5))
                .build();

        Product p3 = Product.builder()
                .id("p-003")
                .name("Mid Phone")
                .category(Category.PHONE)
                .rating(7)
                .createdDate(fixedNow.minusDays(13))
                .modifiedDate(fixedNow.minusDays(13))
                .build();

        Product p4 = Product.builder()
                .id("p-004")
                .name("Best Refrigerator")
                .category(Category.APPLIANCES)
                .rating(10)
                .createdDate(fixedNow.minusMonths(1))
                .modifiedDate(fixedNow.minusMonths(1))
                .build();

        warehouse.addProduct(p1);
        warehouse.addProduct(p2);
        warehouse.addProduct(p3);
        warehouse.addProduct(p4);

        var result = warehouse.getTopRatedProductsThisMonth(fixedNow);

        assertThat(result).containsExactly(p2, p1);
    }

    @Test
    void getTopRatedProductsThisMonth_whenNoMatchingProducts_returnsEmptyList() {
        warehouse.addProduct(Product.builder()
                .id("p-003")
                .name("Mid Phone")
                .category(Category.PHONE)
                .rating(7)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        warehouse.addProduct(Product.builder()
                .id("p-002")
                .name("Mid Laptop")
                .category(Category.COMPUTER)
                .rating(7)
                .createdDate(today)
                .modifiedDate(today)
                .build());

        var result = warehouse.getTopRatedProductsThisMonth(today);

        assertThat(result).isEmpty();
    }
}