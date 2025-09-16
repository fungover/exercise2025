package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        ProductRepository repo = new InMemoryProductRepository();
        productService = new ProductService(repo);
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
                .price(BigDecimal.valueOf(9.99))
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        productService.addProduct(clicker);

        List<Product> products = productService.getAllProducts();

        assertThat(products)
                .hasSize(1)
                .containsExactly(clicker);
    }

    @Test
    @DisplayName("Throws an exception when getName is empty")
    void addProductToWarehouseWithEmptyGetName_throwsException(){
        assertThatThrownBy(() -> productService.addProduct(
                Product.builder()
                        .id(UUID.randomUUID().toString())
                        .name("")
                        .category(Category.FOOD)
                        .price(BigDecimal.valueOf(1.00))
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
                .price(BigDecimal.valueOf(4.50))
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        Product squeakyBanana = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Squeaky Banana")
                .category(Category.TOYS)
                .price(BigDecimal.valueOf(7.25))
                .rating(3)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        productService.addProduct(chewBone);
        productService.addProduct(squeakyBanana);

        List<Product> allProducts = productService.getAllProducts();

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
                .price(BigDecimal.valueOf(19.99))
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        productService.addProduct(redBlanket);

        List<Product> allProducts = productService.getAllProducts();

        assertThatThrownBy(() -> allProducts.add(
                Product.builder()
                        .id(UUID.randomUUID().toString())
                        .name("Squeaky Banana")
                        .category(Category.TOYS)
                        .price(BigDecimal.valueOf(7.25))
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
    void getProductById_returnsProduct_whenGetIdExists() {
        Product chewBone = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Chew Bone")
                .category(Category.FOOD)
                .price(BigDecimal.valueOf(3.99))
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        productService.addProduct(chewBone);

        Optional<Product> result = productService.getProductById(chewBone.getId());

        assertThat(result)
                .isPresent()
                .contains(chewBone);
    }

    @Test
    @DisplayName("Returns empty when product ID does not exist")
    void getProductById_returnsEmpty_whenGetIdDoesNotExist() {
        Optional<Product> result = productService.getProductById(UUID.randomUUID().toString());

        assertThat(result)
                .isEmpty();
    }

    @Test
    @DisplayName("Returns empty when ID is not a valid UUID")
    void getProductById_returnsEmpty_whenGetIdIsInvalidFormat() {
        Optional<Product> result = productService.getProductById("not-a-uuid");

        assertThat(result)
                .isEmpty();
    }

    //TEST GET PRODUCTS BY CATEGORY SORTED

    @Test
    @DisplayName("Returns products of specified getCategory sorted alphabetically by getName")
    void getProductsByGetCategorySorted_returnsProductsSortedByGetName() {
        ZonedDateTime now = ZonedDateTime.now();

        Product premiumFood = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Zebra Premium Dog Food")
                .category(Category.FOOD)
                .price(BigDecimal.valueOf(29.99))
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Product alfaFood = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Alpha Dog Food")
                .category(Category.FOOD)
                .price(BigDecimal.valueOf(9.49))
                .rating(4)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Product betaFood = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Beta Premium Food")
                .category(Category.FOOD)
                .price(BigDecimal.valueOf(14.75))
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        Product toy = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Ball")
                .category(Category.TOYS)
                .price(BigDecimal.valueOf(2.50))
                .rating(3)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        productService.addProduct(premiumFood);
        productService.addProduct(alfaFood );
        productService.addProduct(betaFood);
        productService.addProduct(toy);

        List<Product> foodProducts = productService.getProductsByCategorySorted(Category.FOOD);

        assertThat(foodProducts)
                .hasSize(3)
                .containsExactly(alfaFood, betaFood, premiumFood); // Alpha, Beta, Zebra
    }

    @Test
    @DisplayName("Sorting getCategory returns empty list when warehouse is empty")
    void getProductsByGetCategorySorted_returnsEmptyList_whenWarehouseIsEmpty() {
        List<Product> products = productService.getProductsByCategorySorted(Category.FOOD);

        assertThat(products)
                .isEmpty();
    }

    @Test
    @DisplayName("Sorting getCategory throws exception when getCategory is null")
    void getProductsByCategorySorted_throwsException_whenGetCategoryIsNull() {
        assertThatThrownBy(() -> productService.getProductsByCategorySorted(null))
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
                .price(BigDecimal.valueOf(10.00))
                .rating(3)
                .createdDate(createdTime)
                .modifiedDate(createdTime)
                .build();

        productService.addProduct(originalProduct);

        productService.updateProduct(
                originalProduct.getId(),
                "Premium Dog Food",
                Category.FOOD,
                5);

        Optional<Product> updatedProduct = productService.getProductById(originalProduct.getId());

        assertThat(updatedProduct)
                .isPresent();

        Product updated = updatedProduct.get();
        assertThat(updated.getId()).isEqualTo(originalProduct.getId());
        assertThat(updated.getName()).isEqualTo("Premium Dog Food");
        assertThat(updated.getCategory()).isEqualTo(Category.FOOD);
        assertThat(updated.getRating()).isEqualTo(5);
        assertThat(updated.getCreatedDate()).isEqualTo(originalProduct.getCreatedDate());
        assertThat(updated.getModifiedDate()).isAfter(originalProduct.getModifiedDate());

        // Verify that the product was successfully updated via ProductService
        Optional<Product> retrievedProduct = productService.getProductById(originalProduct.getId());
        assertThat(retrievedProduct)
                .isPresent()
                .contains(updated);
    }

    @Test
    @DisplayName("Update throws exception when getRating is below valid range")
    void updateProduct_throwsException_whenGetRatingIsBelowZero() {
        ZonedDateTime now = ZonedDateTime.now();
        Product originalProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Original Name")
                .category(Category.FOOD)
                .price(BigDecimal.valueOf(5.00))
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build();

        productService.addProduct(originalProduct);

        assertThatThrownBy(() -> productService.updateProduct(
                originalProduct.getId(),
                "Product Name",
                Category.FOOD,
                -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("rating must be between 0 and 10");
    }
    @Test
    @DisplayName("Throws exception when trying to update non-existent product")
    void updateProduct_throwsException_whenProductGetIdDoesNotExist() {
        String nonExistentId = UUID.randomUUID().toString();

        assertThatThrownBy(() -> productService.updateProduct(
                nonExistentId,
                "New Name",
                Category.FOOD,
                5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("does not exist");
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
                .price(BigDecimal.valueOf(1.10))
                .rating(5)
                .createdDate(now.minusDays(2))
                .modifiedDate(now.minusDays(2))
                .build();

        Product newProduct = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("New")
                .category(Category.FOOD)
                .price(BigDecimal.valueOf(2.20))
                .rating(8)
                .createdDate(now.plusHours(1))
                .modifiedDate(now.plusHours(1))
                .build();

        productService.addProduct(oldProduct);
        productService.addProduct(newProduct);

        List<Product> result = productService.getProductsCreatedAfter(now);

        assertEquals(1, result.size());
        assertEquals("New", result.get(0).getName());
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
                .price(BigDecimal.valueOf(12.34))
                .rating(7)
                .createdDate(baseDateTime.minusDays(2))
                .modifiedDate(baseDateTime.minusDays(2))
                .build();

        productService.addProduct(oldProduct);

        List<Product> recentProducts = productService.getProductsCreatedAfter(baseDateTime);

        assertThat(recentProducts)
                .isEmpty();
    }

    //TEST GET MODIFIED PRODUCTS

    @Test
    @DisplayName("Returns products where getCreatedDate is NOT the same as getModifiedDate")
    void getModifiedProducts_returnsModifiedProducts() {
        ZonedDateTime now = ZonedDateTime.now();
        Product modified = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Modified Toy")
                .category(Category.TOYS)
                .price(BigDecimal.valueOf(6.66))
                .rating(7)
                .createdDate(now)
                .modifiedDate(now.plusDays(1))
                .build();

        Product unmodified = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Unmodified Toy")
                .category(Category.TOYS)
                .price(BigDecimal.valueOf(5.55))
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)//same date as created date
                .build();

        productService.addProduct(modified);
        productService.addProduct(unmodified);

        List<Product> result = productService.getModifiedProducts();

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
                .price(BigDecimal.valueOf(4.44))
                .rating(4)
                .createdDate(now)
                .modifiedDate(now)//not modified
                .build();

        productService.addProduct(product);

        List<Product> result = productService.getModifiedProducts();

        assertThat(result)
                .isEmpty();
    }

    //TESTS GET PRICE

    @Test
    @DisplayName("Returns correct price from getPrice")
    void getPrice_returnsCorrectValue() {
        Product toy = Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Toy")
                .category(Category.TOYS)
                .price(BigDecimal.valueOf(15.75))
                .rating(4)
                .createdDate(ZonedDateTime.now())
                .modifiedDate(ZonedDateTime.now())
                .build();

        assertEquals(
                BigDecimal.valueOf(15.75),
                toy.getPrice()
        );
    }

    @Test
    @DisplayName("Throws exception when price is zero")
    void buildProduct_throwsException_whenPriceIsZero() {
        ZonedDateTime now = ZonedDateTime.now();

        assertThatThrownBy(() -> Product.builder()
                .id(UUID.randomUUID().toString())
                .name("Free Sample")
                .category(Category.FOOD)
                .price(BigDecimal.ZERO)//Price is 0
                .rating(5)
                .createdDate(now)
                .modifiedDate(now)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("price must be greater than zero");
    }
}