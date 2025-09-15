package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


class WareHouseTest {

    private Warehouse warehouse;

    @BeforeEach void setUpTest() {
        warehouse = new Warehouse();
    }

    @Test
    @DisplayName("Tests for addProduct method")
    void addProductTest() {
        LocalDate today = LocalDate.now();

        // Success case
        Product validProduct = new Product.Builder()
                .id("5")
                .name("Valid")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(today)
                .modifiedDate(today)
                .build();

        warehouse.addProduct(validProduct);
        assertTrue(warehouse.getAllProducts().contains(validProduct), "Valid product should be added");

        // Failure: empty name
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(new Product.Builder()
                        .id("1")
                        .name("") // tomt namn
                        .category(Category.FOOD)
                        .rating(2)
                        .createdDate(today)
                        .modifiedDate(today)
                        .price(5.0)
                        .build())
        );

                        //(new Product("1", "", Category.FOOD, 2, today, today)));
        assertEquals("Name cannot be null or empty", exception.getMessage());

        // Failure: null product
        exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(null));
        assertEquals("Product cannot be null", exception.getMessage());

        // Failure: duplicate ID
        Product duplicateIdProduct = new Product.Builder()
                .id(validProduct.id())
                .name("Duplicate")
                .category(Category.ELECTRONICS)
                .rating(5)
                .createdDate(today)
                .modifiedDate(today)
                .price(20.0)
                .build();

        exception = assertThrows(IllegalArgumentException.class, () ->
                warehouse.addProduct(duplicateIdProduct));
        assertEquals("Product with this ID already exists", exception.getMessage());
    }

    @Test
    @DisplayName("Tests for updateProduct method")
    void updateProductTest() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 7).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product beforeUpdate = new Product.Builder()
                .id("5")
                .name("VaLid")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();

                //("5", "Valid", Category.FOOD, 5, today, today);

        warehouse.addProduct(beforeUpdate);
        warehouse.updateProduct("5", "Updated", Category.ELECTRONICS, 9);

        Product afterUpdate = warehouse.getProductById("5");

        assertThat(afterUpdate).isNotNull();
        assertThat(afterUpdate.id()).isEqualTo("5"); // Same id
        assertThat(afterUpdate.name()).isEqualTo("Updated");
        assertThat(afterUpdate.category()).isEqualTo(Category.ELECTRONICS);
        assertThat(afterUpdate.rating()).isEqualTo(9);
        assertThat(afterUpdate.createdDate()).isEqualTo(today); // createdDate should be unchanged

        assertThat(afterUpdate.modifiedDate()).isAfter(beforeUpdate.modifiedDate());
    }

    @Test
    @DisplayName("Tests for getAllProducts method")
    void getAllProductsTest() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 8).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product product1 = new Product.Builder()
                .id("97")
                .name("Product97")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();
        warehouse.addProduct(product1);

        Product product2 = new Product.Builder()
                .id("98")
                .name("Product99")
                .category(Category.ELECTRONICS)
                .rating(10)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();
        warehouse.addProduct(product2);

        Product product3 = new Product.Builder()
                .id("99")
                .name("Product99")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();
        warehouse.addProduct(product3);

        List <Product> copyWarehouse = warehouse.getAllProducts();

        assertThat(copyWarehouse).isNotNull();
        assertThat(copyWarehouse.size()).isEqualTo(3);
        assertThat(copyWarehouse).containsExactlyInAnyOrder(product1, product2, product3);
        assertThat(copyWarehouse.stream().anyMatch(p -> p.id().equals("100"))).isFalse();
    }

    @Test
    @DisplayName("Test when list is empty in getAllProducts method, should return empty list")
    void getAllProductsEmptyListTest() {
        List <Product> productList = warehouse.getAllProducts();
        assertThat(productList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Tests for getProductById method")
    void getProductByIdTest() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 8).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product product1 = new Product.Builder()
                .id("97")
                .name("Product97")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();

        Product product2 = new Product.Builder()
                .id("98")
                .name("Product98")
                .category(Category.TOYS)
                .rating(8)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);

        Product found1 = warehouse.getProductById("97");
        Product found2 = warehouse.getProductById("98");

        assertThat(found1).isNotNull();
        assertThat(found2).isNotNull();
        assertThat(found1.id()).isEqualTo("97");
        assertThat(found2.id()).isEqualTo("98");

        assertThatThrownBy(() -> warehouse.getProductById("1000"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product with id 1000 does not exist");
    }

    @Test
    @DisplayName("Tests for getProductsByCategorySorted method")
    void getProductsByCategorySortedTest() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 8).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate today = LocalDate.now(fixedClock);

        Product product1 = new Product.Builder()
                .id("1")
                .name("USB-Cable")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(today)
                .modifiedDate(today)
                .price(1)
                .build();

        Product product2 = new Product.Builder()
                .id("2")
                .name("Socks")
                .category(Category.CLOTHING)
                .rating(2)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();

        Product product3 = new Product.Builder()
                .id("3")
                .name("Hamburger")
                .category(Category.FOOD)
                .rating(3)
                .createdDate(today)
                .modifiedDate(today)
                .price(3)
                .build();

        Product product4 = new Product.Builder()
                .id("4")
                .name("Hoodie")
                .category(Category.CLOTHING)
                .rating(4)
                .createdDate(today)
                .modifiedDate(today)
                .price(2)
                .build();

        Product product5 = new Product.Builder()
                .id("5")
                .name("Trousers")
                .category(Category.CLOTHING)
                .rating(4)
                .createdDate(today)
                .modifiedDate(today)
                .price(4)
                .build();

        Product product6 = new Product.Builder()
                .id("6")
                .name("Pants")
                .category(Category.CLOTHING)
                .rating(4)
                .createdDate(today)
                .modifiedDate(today)
                .price(6)
                .build();

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        warehouse.addProduct(product5);
        warehouse.addProduct(product6);

        List <Product> filteredList =  warehouse.getProductsByCategorySorted(Category.CLOTHING);
        List <Product> filteredListToys =  warehouse.getProductsByCategorySorted(Category.TOYS);

        assertThat(warehouse.getAllProducts().size()).isEqualTo(6);

        assertThat(filteredList.size()).isEqualTo(4);
        assertThat(filteredListToys.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Tests for getProductsCreatedAfter method")
    void getProductsCreatedAfterTest() {

        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 1).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock editedDate = Clock.fixed(
                LocalDate.of(2025, 9, 9).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock oneMoreEditedDate = Clock.fixed(
                LocalDate.of(2025, 9, 11).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate defaultDay = LocalDate.now(fixedClock);
        LocalDate dateSep9 = LocalDate.now(editedDate);
        LocalDate dateSep11 = LocalDate.now(oneMoreEditedDate);

        Product product1 = new Product.Builder()
                .id("1")
                .name("Product1")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(defaultDay)
                .price(1)
                .build();

        Product product2 = new Product.Builder()
                .id("97")
                .name("Product97")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(dateSep9)
                .modifiedDate(dateSep9)
                .price(2)
                .build();

        Product product3 = new Product.Builder()
                .id("3")
                .name("Product3")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(dateSep11)
                .modifiedDate(dateSep11)
                .price(3)
                .build();

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);

        List <Product> filteredList =  warehouse.getProductsCreatedAfter(defaultDay);
        List <Product> filteredList2 =  warehouse.getProductsCreatedAfter(dateSep9);
        List <Product> filteredList3 =  warehouse.getProductsCreatedAfter(LocalDate.of(2025, 12, 20));

        // tests
        assertThat(warehouse.getAllProducts().size()).isEqualTo(3);
        assertThat(filteredList.size()).isEqualTo(2);
        assertThat(filteredList2.size()).isEqualTo(1);
        assertThat(filteredList3).isEmpty();
    }

    @Test
    @DisplayName("Tests for getModifiedProducts method")
    void getModifiedProductsTest() {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 1).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock editedDate = Clock.fixed(
                LocalDate.of(2025, 9, 9).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock oneMoreEditedDate = Clock.fixed(
                LocalDate.of(2025, 9, 11).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        LocalDate defaultDay = LocalDate.now(fixedClock);
        LocalDate dateSep9 = LocalDate.now(editedDate);
        LocalDate dateSep11 = LocalDate.now(oneMoreEditedDate);

        Product normalProduct = new Product.Builder()
                .id("1")
                .name("Product1")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(defaultDay)
                .price(2)
                .build();

        Product modifiedProduct1 = new Product.Builder()
                .id("2")
                .name("Product2")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep9)
                .price(2)
                .build();

        Product modifiedProduct2 = new Product.Builder()
                .id("3")
                .name("Product3")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(2)
                .build();

        warehouse.addProduct(normalProduct);
        warehouse.addProduct(modifiedProduct1);
        warehouse.addProduct(modifiedProduct2);

        List <Product> result =  warehouse.getModifiedProducts();

        // tests
        assertThat(warehouse.getAllProducts().size()).isEqualTo(3);
        assertThat(result).doesNotContain(normalProduct);
        assertThat(warehouse.getModifiedProducts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Tests for getCategoriesWithProducts method")
    void getCategoriesWithProductsTest(){
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 1).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock editedDate = Clock.fixed(
                LocalDate.of(2025, 9, 9).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock oneMoreEditedDate = Clock.fixed(
                LocalDate.of(2025, 9, 11).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );

        LocalDate defaultDay = LocalDate.now(fixedClock);
        LocalDate dateSep9 = LocalDate.now(editedDate);
        LocalDate dateSep11 = LocalDate.now(oneMoreEditedDate);

        Product normalProduct = new Product.Builder()
                .id("1")
                .name("Product1")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(defaultDay)
                .price(1)
                .build();

        Product modifiedProduct1 = new Product.Builder()
                .id("2")
                .name("Product2")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep9)
                .price(1)
                .build();

        Product modifiedProduct2 = new Product.Builder()
                .id("3")
                .name("Product3")
                .category(Category.CLOTHING)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(1)
                .build();

        warehouse.addProduct(normalProduct);
        warehouse.addProduct(modifiedProduct1);
        warehouse.addProduct(modifiedProduct2);

        // run method
        Set<Category> categories = warehouse.getCategoriesWithProducts();

        // tests
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories).containsExactlyInAnyOrder(Category.ELECTRONICS, Category.CLOTHING);
        assertThat(categories).doesNotContain(Category.FOOD);
        assertThat(categories).doesNotContain(Category.TOYS);
    }

    @Test
    @DisplayName("Test for countProductsInCategory method")
    void countProductsInCategoryTest() {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 1).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock editedDate = Clock.fixed(
                LocalDate.of(2025, 9, 9).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock oneMoreEditedDate = Clock.fixed(
                LocalDate.of(2025, 9, 11).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );

        LocalDate defaultDay = LocalDate.now(fixedClock);
        LocalDate dateSep9 = LocalDate.now(editedDate);
        LocalDate dateSep11 = LocalDate.now(oneMoreEditedDate);

        Product normalProduct = new Product.Builder()
                .id("1")
                .name("Product1")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(defaultDay)
                .price(2)
                .build();

        Product modifiedProduct1 = new Product.Builder()
                .id("2")
                .name("Product2")
                .category(Category.ELECTRONICS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep9)
                .price(2)
                .build();

        Product modifiedProduct2 = new Product.Builder()
                .id("3")
                .name("Product3")
                .category(Category.CLOTHING)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(2)
                .build();

        warehouse.addProduct(normalProduct);
        warehouse.addProduct(modifiedProduct1);
        warehouse.addProduct(modifiedProduct2);

        // run method
        int countCategoryElectronics = warehouse.countProductsInCategory(Category.ELECTRONICS);
        int countCategoryClothing = warehouse.countProductsInCategory(Category.CLOTHING);
        int countCategoryToys = warehouse.countProductsInCategory(Category.TOYS);

        // tests
        assertThat(countCategoryElectronics).isEqualTo(2);
        assertThat(countCategoryClothing).isEqualTo(1);
        assertThat(countCategoryToys).isEqualTo(0);
    }

    @Test
    @DisplayName("Test for getProductInitialsMap")
    void getProductInitialsMapTest() {
        // setup
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 1).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock editedDate = Clock.fixed(
                LocalDate.of(2025, 9, 9).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );
        Clock oneMoreEditedDate = Clock.fixed(
                LocalDate.of(2025, 9, 11).atStartOfDay(ZoneOffset.UTC).toInstant(),
                ZoneOffset.UTC
        );

        LocalDate defaultDay = LocalDate.now(fixedClock);
        LocalDate dateSep9 = LocalDate.now(editedDate);
        LocalDate dateSep11 = LocalDate.now(oneMoreEditedDate);

        Product normalProduct = new Product.Builder()
                .id("1")
                .name("Apple")
                .category(Category.FOOD)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(defaultDay)
                .price(2)
                .build();

        Product modifiedProduct1 = new Product.Builder()
                .id("2")
                .name("Avocado")
                .category(Category.FOOD)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep9)
                .price(2)
                .build();

        Product modifiedProduct2 = new Product.Builder()
                .id("3")
                .name("Pants")
                .category(Category.CLOTHING)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(2)
                .build();

        Product modifiedProduct3 = new Product.Builder()
                .id("4")
                .name("Wobbler")
                .category(Category.TOYS)
                .rating(1)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(2)
                .build();

        warehouse.addProduct(normalProduct);
        warehouse.addProduct(modifiedProduct1);
        warehouse.addProduct(modifiedProduct2);
        warehouse.addProduct(modifiedProduct3);

        // run
        Map<Character, Integer> result = warehouse.getProductInitialsMap();

        // tests
        assertThat(result).containsEntry('A', 2);
        assertThat(result).containsEntry('P', 1);
        assertThat(result).containsEntry('W', 1);
        assertThat(result).doesNotContainKey('Z');
        assertThat(result).doesNotContainKey('X');
    }

    @Test
    @DisplayName("Test for getTopRatedProductThisMonth method")
    void getTopRatedProductsThisMonthTest() {
        // setup
        LocalDate defaultDay = LocalDate.of(2025, 8, 1);
        LocalDate dateSep9 = LocalDate.of(2025, 9, 9);
        LocalDate dateSep11 = LocalDate.of(2025, 9, 11);
        LocalDate dateAug20 = LocalDate.of(2025, 8, 20);
        LocalDate dateSep20LastYear = LocalDate.of(2024, 9, 20);

        Product product1 = new Product.Builder()
                .id("1")
                .name("Apple")
                .category(Category.FOOD)
                .rating(10)
                .createdDate(dateSep11)
                .modifiedDate(dateSep11)
                .price(15.0)
                .build();

        Product product2 = new Product.Builder()
                .id("2")
                .name("Avocado")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(dateSep9)
                .modifiedDate(dateSep9)
                .price(12.0)
                .build();

        Product product3 = new Product.Builder()
                .id("3")
                .name("Pants")
                .category(Category.CLOTHING)
                .rating(7)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(25.0)
                .build();

        Product product4 = new Product.Builder()
                .id("4")
                .name("Pants")
                .category(Category.CLOTHING)
                .rating(7)
                .createdDate(defaultDay)
                .modifiedDate(dateSep11)
                .price(30.0)
                .build();

        Product product5 = new Product.Builder()
                .id("5")
                .name("Wobbler")
                .category(Category.TOYS)
                .rating(10)
                .createdDate(dateSep11)
                .modifiedDate(dateSep11)
                .price(50.0)
                .build();

        Product product6 = new Product.Builder()
                .id("6")
                .name("Wobbler")
                .category(Category.TOYS)
                .rating(10)
                .createdDate(dateSep20LastYear)
                .modifiedDate(dateAug20)
                .price(55.0)
                .build();

        // test for empty list
        assertThat(warehouse.getTopRatedProductsThisMonth().size()).isEqualTo(0);

        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        warehouse.addProduct(product3);
        warehouse.addProduct(product4);
        warehouse.addProduct(product5);
        warehouse.addProduct(product6);

        // run
        List<Product> result = warehouse.getTopRatedProductsThisMonth();

        // tests
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0).rating()).isEqualTo(10);
        assertThat(result.get(1).rating()).isEqualTo(10);
        assertThat(result.get(2).rating()).isEqualTo(8);

        assertThat(result).doesNotContain(product4);
    }
}