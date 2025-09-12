package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse();
    }

    //TEST ADD PRODUCT

    @Test
    @DisplayName( "Adds a product to the warehouse")
    void addProductToWarehouse(){
        ZonedDateTime now = ZonedDateTime.now();
        Product clicker = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Clicker")
                .category(Category.TRAINING)
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        warehouse.addProduct(clicker);

        List<Product> products = warehouse.getAllProducts();

        assertThat(products)
                .hasSize(1)
                .containsExactly(clicker);
    }

    @Test
    @DisplayName("Throws an exception when name is empty")
    void addProductToWarehouseWithEmptyName_throwsException(){
        assertThatThrownBy(() -> warehouse.addProduct(
                Product.builder()
                        .id(UUID.randomUUID().toString())
                        .name("")
                        .category(Category.FOOD)
                        .rating(3)
                        .createdDate(ZonedDateTime.now())
                        .modifiedDate(ZonedDateTime.now())
                        .build()
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("name required");

    }

    //TEST GET ALL PRODUCTS

    @Test
    @DisplayName("Returns all added products to  the warehouse")
    public void getAllProducts_returnsAllAddedProducts() {

        Product chewBone = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Chew Bone")
                .category(Category.FOOD)
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        Product squeakyBanana = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Squeaky Banana")
                .category(Category.TOYS)
                .rating(3)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        warehouse.addProduct(chewBone);
        warehouse.addProduct(squeakyBanana);

        List<Product> allProducts = warehouse.getAllProducts();

        assertThat(allProducts)
                .hasSize(2)
                .containsExactly(chewBone, squeakyBanana);
    }

    @Test
    @DisplayName("Throws an exception when trying to modify returned list")
    public void getAllProducts_throwsException_whenTryingToModifyReturnedList() {

        Product redBlanket = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Red Blanket")
                .category(Category.BEDDING)
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        warehouse.addProduct(redBlanket);

        List<Product> allProducts = warehouse.getAllProducts();

        assertThatThrownBy(() -> allProducts.add(
                Product.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Squeaky Banana")
                        .category(Category.TOYS)
                        .rating(3)
                        .createdDate(ZonedDateTime.now())
                        .modifiedDate(ZonedDateTime.now())
                        .build()
        ))
                .isInstanceOf(UnsupportedOperationException.class);

        assertThat(allProducts)
                .hasSize(1)
                .containsExactly(redBlanket);

    }

    //TEST GET PRODUCT BY ID

    @Test
    @DisplayName("Returns product by valid ID")
    void getProductById_returnsProduct_whenIdExists() {
        Product chewBone = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Chew Bone")
                .category(Category.FOOD)
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        warehouse.addProduct(chewBone);

        Optional<Product> result = warehouse.getProductById(chewBone.id());

        assertThat(result)
                .isPresent()
                .contains(chewBone);
    }

    @Test
    @DisplayName("Returns empty when product ID does not exist")
    void getProductById_returnsEmpty_whenIdDoesNotExist() {
        Optional<Product> result = warehouse.getProductById(UUID.randomUUID().toString());

        assertThat(result)
                .isEmpty();
    }

    @Test
    @DisplayName("Returns empty when ID is not a valid UUID")
    void getProductById_returnsEmpty_whenIdIsInvalidFormat() {
        Optional<Product> result = warehouse.getProductById("not-a-uuid");

        assertThat(result)
                .isEmpty();
    }

    //TEST GET PRODUCTS BY CATEGORY SORTED

    @Test
    @DisplayName("Returns products of specified category sorted alphabetically by name")
    void getProductsByCategorySorted_returnsProductsSortedByName() {
        ZonedDateTime now = ZonedDateTime.now();

        Product premiumFood = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Zebra Premium Dog Food")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Product alfaFood = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Alpha Dog Food")
                .category(Category.FOOD)
                .rating(4)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Product betaFood = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Beta Premium Food")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Product toy = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Ball")
                .category(Category.TOYS)
                .rating(3)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        warehouse.addProduct(premiumFood);
        warehouse.addProduct(alfaFood );
        warehouse.addProduct(betaFood);
        warehouse.addProduct(toy);

        List<Product> foodProducts = warehouse.getProductsByCategorySorted(Category.FOOD);

        assertThat(foodProducts)
                .hasSize(3)
                .containsExactly(alfaFood, betaFood, premiumFood); // Alpha, Beta, Zebra
    }

    @Test
    @DisplayName("Sorting category returns empty list when warehouse is empty")
    void getProductsByCategorySorted_returnsEmptyList_whenWarehouseIsEmpty() {
        List<Product> products = warehouse.getProductsByCategorySorted(Category.FOOD);

        assertThat(products)
                .isEmpty();
    }

    @Test
    @DisplayName("Sorting category throws exception when category is null")
    void getProductsByCategorySorted_throwsException_whenCategoryIsNull() {
        assertThatThrownBy(() -> warehouse.getProductsByCategorySorted(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category cannot be null");
    }

    //TEST UPDATE PRODUCT

    @Test
    @DisplayName("Updates successfully existing product with new values")
    void updateProduct_updatesExistingProduct_whenValidInputProvided() {
        ZonedDateTime createdTime = ZonedDateTime.now().minusDays(1);

        Product originalProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Old Dog Food")
                .category(Category.FOOD)
                .rating(3)
                .createdDate(createdTime)
                .modifiedDate(createdTime)
                .build();

        warehouse.addProduct(originalProduct);

        Optional<Product> updatedProduct = warehouse.updateProduct(
                originalProduct.id(),
                "Premium Dog Food",
                Category.FOOD,
                5);

        assertThat(updatedProduct)
                .isPresent();

        Product updated = updatedProduct.get();
        assertThat(updated.id()).isEqualTo(originalProduct.id());
        assertThat(updated.name()).isEqualTo("Premium Dog Food");
        assertThat(updated.category()).isEqualTo(Category.FOOD);
        assertThat(updated.rating()).isEqualTo(5);
        assertThat(updated.createdDate()).isEqualTo(originalProduct.createdDate());
        assertThat(updated.modifiedDate()).isAfter(originalProduct.modifiedDate());

        // Verify that the product is updated in the warehouse
        Optional<Product> retrievedProduct = warehouse.getProductById(originalProduct.id());
        assertThat(retrievedProduct)
                .isPresent()
                .contains(updated);
    }

    @Test
    @DisplayName("Update throws exception when rating is below valid range")
    void updateProduct_throwsException_whenRatingIsBelowZero() {
        ZonedDateTime now = ZonedDateTime.now();
        Product originalProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Original Name")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        warehouse.addProduct(originalProduct);

        assertThatThrownBy(() -> warehouse.updateProduct(
                originalProduct.id(),
                "Product Name",
                Category.FOOD,
                -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rating must be between 0 and 10");
    }
    @Test
    @DisplayName("Update returns empty optional when product ID does not exist")
    void updateProduct_returnsEmpty_whenProductIdDoesNotExist() {
        String nonExistentId = UUID.randomUUID().toString();

        Optional<Product> result = warehouse.updateProduct(
                nonExistentId,
                "New Name",
                Category.FOOD,
                5);

        assertThat(result).isEmpty();
    }

    //TEST GET PRODUCTS CREATED AFTER

    @Test
    @DisplayName("Returns products created AFTER given ZonedDateTime")
    void getProductsCreatedAfter_returnsMatchingProducts() {

        ZonedDateTime now = ZonedDateTime.now();
        Product oldProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Old")
                .category(Category.FOOD)
                .rating(5)
                .createdDate(now.minusDays(2))
                .modifiedDate(now.minusDays(2))
                .build();

        Product newProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("New")
                .category(Category.FOOD)
                .rating(8)
                .createdDate(now.plusHours(1))
                .modifiedDate(now.plusHours(1))
                .build();

        warehouse.addProduct(oldProduct);
        warehouse.addProduct(newProduct);

        List<Product> result = warehouse.getProductsCreatedAfter(now);

        assertEquals(1, result.size());
        assertEquals("New", result.get(0).name());
    }

    @Test
    @DisplayName("Should return empty list when no products created after the specified ZonedDateTime")
    void getProductsCreatedAfterZonedDateTimeWhenThereAreNoMatchingProducts() {
        ZonedDateTime baseDateTime = ZonedDateTime.of(
                2025,
                9,
                1,
                12,
                0,
                0,
                0,
                ZoneId.systemDefault()); // 1 Sep 2025 12:00

        Product oldProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Old conditioner")
                .category(Category.GROOMING)
                .rating(7)
                .createdDate(baseDateTime.minusDays(2))
                .modifiedDate(baseDateTime.minusDays(2))
                .build();

        warehouse.addProduct(oldProduct);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(baseDateTime);

        assertThat(recentProducts)
                .isEmpty();
    }

    //TEST GET MODIFIED PRODUCTS

    @Test
    @DisplayName("Returns products where createdDate is NOT the same as modifiedDate")
    void getModifiedProducts_returnsModifiedProducts() {
        ZonedDateTime now = ZonedDateTime.now();
        Product modified = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Modified Toy")
                .category(Category.TOYS)
                .rating(7)
                .createdDate(now)
                .modifiedDate(now.plusDays(1))
                .build();

        Product unmodified = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Unmodified Toy")
                .category(Category.TOYS)
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)//same date as created date
                .build();

        warehouse.addProduct(modified);
        warehouse.addProduct(unmodified);

        List<Product> result = warehouse.getModifiedProducts();

        assertThat(result)
                .hasSize(1)
                .containsExactly(modified);
    }

    @Test
    @DisplayName("Returns empty list when no products are modified")
    void getModifiedProducts_returnsEmpty_whenNoProductsModified() {
        ZonedDateTime now = ZonedDateTime.now();
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Chew Bone")
                .category(Category.FOOD)
                .rating(4)
                .createdDate(now)
                .modifiedDate(now)//not modified
                .build();

        warehouse.addProduct(product);

        List<Product> result = warehouse.getModifiedProducts();

        assertThat(result)
                .isEmpty();
    }
}