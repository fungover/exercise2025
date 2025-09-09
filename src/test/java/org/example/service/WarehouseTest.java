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

    @Test
    @DisplayName( "Adds a product to the warehouse")
    void addProductToWarehouse(){
        ZonedDateTime now = ZonedDateTime.now();
        Product clicker = ( new Product(
                UUID.randomUUID().toString(),
                "Clicker",
                Category.TRAINING,
                5,
                now,
                now
        ));

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
                new Product(
                        UUID.randomUUID().toString(),
                        "",
                        Category.FOOD,
                        3,
                        ZonedDateTime.now(),
                        ZonedDateTime.now()
                )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product name cannot be empty or null.");

    }

    @Test
    @DisplayName("Returns all added products to  the warehouse")
    public void getAllProducts_returnsAllAddedProducts() {
        Warehouse warehouse = new Warehouse();

        Product chewBone = new Product(
                UUID.randomUUID().toString(),
                "Chew Bone",
                Category.FOOD,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        Product SqueakyBanana = new Product(
                UUID.randomUUID().toString(),
                "Squeaky Banana",
                Category.TOYS,
                3,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        warehouse.addProduct(chewBone);
        warehouse.addProduct(SqueakyBanana);

        List<Product> allProducts = warehouse.getAllProducts();

        assertThat(allProducts)
                .hasSize(2)
                .containsExactly(chewBone, SqueakyBanana);
    }

    @Test
    @DisplayName("Throws an exception when trying to modify returned list")
    public void getAllProducts_throwsException_whenTryingToModifyReturnedList() {
        Warehouse warehouse = new Warehouse();
        Product redBlanket = new Product(
                UUID.randomUUID().toString(),
                "Red Blanket",
                Category.BEDDING,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

        warehouse.addProduct(redBlanket);

        List<Product> allProducts = warehouse.getAllProducts();

        assertThatThrownBy(() -> allProducts.add(new Product(
                UUID.randomUUID().toString(),
                "Squeaky Banana",
                Category.TOYS,
                3,
                ZonedDateTime.now(),
                ZonedDateTime.now())))
                .isInstanceOf(UnsupportedOperationException.class);

        assertThat(allProducts)
                .hasSize(1)
                .containsExactly(redBlanket);

    }

    @Test
    @DisplayName("Returns product by valid ID")
    void getProductById_returnsProduct_whenIdExists() {
        Product chewBone = new Product(
                UUID.randomUUID().toString(),
                "Chew Bone",
                Category.FOOD,
                4,
                ZonedDateTime.now(),
                ZonedDateTime.now());

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

    @Test
    @DisplayName("Returns products of specified category sorted alphabetically by name")
    void getProductsByCategorySorted_returnsProductsSortedByName() {
        ZonedDateTime now = ZonedDateTime.now();

        Product premiumFood = new Product(
                UUID.randomUUID().toString(),
                "Zebra Premium Dog Food",
                Category.FOOD,
                5,
                now,
                now);

        Product alfaFood = new Product(
                UUID.randomUUID().toString(),
                "Alpha Dog Food",
                Category.FOOD,
                4,
                now,
                now);

        Product betaFood = new Product(
                UUID.randomUUID().toString(),
                "Beta Premium Food",
                Category.FOOD,
                5,
                now,
                now);

        Product toy = new Product(
                UUID.randomUUID().toString(),
                "Ball",
                Category.TOYS,
                3,
                now,
                now);

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

    @Test
    @DisplayName("Updates successfully existing product with new values")
    void updateProduct_updatesExistingProduct_whenValidInputProvided() {
        ZonedDateTime createdTime = ZonedDateTime.now().minusDays(1);

        Product originalProduct = new Product(
                UUID.randomUUID().toString(),
                "Old Dog Food",
                Category.FOOD,
                3,
                createdTime,
                createdTime);

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
        Product originalProduct = new Product(
                UUID.randomUUID().toString(),
                "Original Name",
                Category.FOOD,
                5,
                now,
                now);

        warehouse.addProduct(originalProduct);

        assertThatThrownBy(() -> warehouse.updateProduct(
                originalProduct.id(),
                "Product Name",
                Category.FOOD,
                -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Rating must be between 0 and 10.");
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

    @Test
    @DisplayName("Returns products created AFTER given ZonedDateTime")
    void getProductsCreatedAfter_returnsMatchingProducts() {
        Warehouse warehouse = new Warehouse();

        ZonedDateTime now = ZonedDateTime.now();
        Product oldProduct = new Product(
                UUID.randomUUID().toString(),
                "Old",
                Category.FOOD,
                5, now.minusDays(2),
                now.minusDays(2));

        Product newProduct = new Product(
                UUID.randomUUID().toString(),
                "New",
                Category.FOOD,
                8,
                now.plusHours(1),
                now.plusHours(1));

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

        Product oldProduct = new Product(
                UUID.randomUUID().toString(),
                "Old conditioner",
                Category.GROOMING,
                7,
                baseDateTime.minusDays(2), // Created 30 Aug 2025
                baseDateTime.minusDays(2)
        );

        warehouse.addProduct(oldProduct);

        List<Product> recentProducts = warehouse.getProductsCreatedAfter(baseDateTime);

        assertThat(recentProducts).isEmpty();
    }
}