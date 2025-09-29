package decorators;

import entities.Category;
import entities.Product;
import entities.Sellable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorTest {

    private Product baseProduct;

    @BeforeEach
    void setUp() {
        baseProduct = new Product.Builder()
                .id("TEST001")
                .name("Test Product")
                .category(Category.ELECTRONICS)
                .rating(8)
                .price(1000.0)
                .asNewProduct()
                .build();
    }

    // ========== DISCOUNT DECORATOR TESTS ==========

    @Test
    @DisplayName("Should apply 20% discount correctly")
    void discountDecorator_20Percent_CalculatesCorrectly() {
        Sellable discounted = new DiscountDecorator(baseProduct, 20);

        assertEquals(800.0, discounted.getPrice(), 0.01);
        assertEquals("Test Product", discounted.getName());
        assertEquals("TEST001", discounted.getId());
    }

    @Test
    @DisplayName("Should apply 50% discount correctly")
    void discountDecorator_50Percent_CalculatesCorrectly() {
        Sellable discounted = new DiscountDecorator(baseProduct, 50);

        assertEquals(500.0, discounted.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should apply 0% discount (no change)")
    void discountDecorator_0Percent_NoChange() {
        Sellable discounted = new DiscountDecorator(baseProduct, 0);

        assertEquals(1000.0, discounted.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should apply 100% discount (free)")
    void discountDecorator_100Percent_ResultsInZero() {
        Sellable discounted = new DiscountDecorator(baseProduct, 100);

        assertEquals(0.0, discounted.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should throw exception for negative discount")
    void discountDecorator_NegativeDiscount_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DiscountDecorator(baseProduct, -10)
        );

        assertTrue(exception.getMessage().contains("between 0 and 100"));
    }

    @Test
    @DisplayName("Should throw exception for discount over 100%")
    void discountDecorator_Over100Percent_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DiscountDecorator(baseProduct, 150)
        );

        assertTrue(exception.getMessage().contains("between 0 and 100"));
    }

    @Test
    @DisplayName("Should throw exception for null product")
    void discountDecorator_NullProduct_ThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new DiscountDecorator(null, 20)
        );

        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Should include discount info in description")
    void discountDecorator_Description_IncludesDiscountInfo() {
        Sellable discounted = new DiscountDecorator(baseProduct, 20);
        String description = discounted.getDescription();

        assertTrue(description.contains("20%"));
        assertTrue(description.contains("rabatt"));
    }

    @Test
    @DisplayName("Should calculate savings correctly")
    void discountDecorator_GetSavings_CalculatesCorrectly() {
        DiscountDecorator discounted = new DiscountDecorator(baseProduct, 25);

        assertEquals(250.0, discounted.getSavings(), 0.01);
        assertEquals(1000.0, discounted.getOriginalPrice(), 0.01);
    }

    @Test
    @DisplayName("Should get discount percentage correctly")
    void discountDecorator_GetDiscountPercentage_ReturnsCorrectValue() {
        DiscountDecorator discounted = new DiscountDecorator(baseProduct, 15);

        assertEquals(15.0, discounted.getDiscountPercentage(), 0.01);
    }

    // ========== CHAINING DISCOUNTS TESTS ==========

    @Test
    @DisplayName("Should chain two discounts correctly")
    void chainDecorators_TwoDiscounts_CalculatesCorrectly() {
        // 1000 kr - 20% = 800 kr
        // 800 kr - 10% = 720 kr
        Sellable chained = new DiscountDecorator(
                new DiscountDecorator(baseProduct, 20),
                10
        );

        assertEquals(720.0, chained.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should chain three discounts correctly")
    void chainDecorators_ThreeDiscounts_CalculatesCorrectly() {
        // 1000 - 10% = 900
        // 900 - 5% = 855
        // 855 - 3% = 829.35
        Sellable chained = new DiscountDecorator(
                new DiscountDecorator(
                        new DiscountDecorator(baseProduct, 10),
                        5
                ),
                3
        );

        assertEquals(829.35, chained.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should apply same discount multiple times")
    void chainDecorators_SameDiscountMultipleTimes_Works() {
        // Dubbel 10% rabatt
        Sellable doubleDiscount = new DiscountDecorator(
                new DiscountDecorator(baseProduct, 10),
                10
        );

        // 1000 - 10% = 900, 900 - 10% = 810
        assertEquals(810.0, doubleDiscount.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should maintain product identity through decorators")
    void chainDecorators_MaintainsIdentity_Correctly() {
        Sellable decorated = new DiscountDecorator(
                new DiscountDecorator(baseProduct, 20),
                10
        );

        assertEquals("TEST001", decorated.getId());
        assertEquals("Test Product", decorated.getName());
    }

    // ========== EDGE CASES ==========

    @Test
    @DisplayName("Should handle zero price product")
    void decorator_ZeroPrice_HandlesCorrectly() {
        Product freeProduct = new Product.Builder()
                .id("FREE001")
                .name("Free Sample")
                .category(Category.TOYS)
                .rating(5)
                .price(0.0)
                .asNewProduct()
                .build();

        Sellable discounted = new DiscountDecorator(freeProduct, 20);
        assertEquals(0.0, discounted.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should handle very high prices")
    void decorator_HighPrice_CalculatesCorrectly() {
        Product expensive = new Product.Builder()
                .id("EXP001")
                .name("Luxury Item")
                .category(Category.ELECTRONICS)
                .rating(10)
                .price(1000000.0)
                .asNewProduct()
                .build();

        Sellable discounted = new DiscountDecorator(expensive, 15);
        assertEquals(850000.0, discounted.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should handle decimal discount percentages")
    void decorator_DecimalDiscount_CalculatesCorrectly() {
        Sellable discounted = new DiscountDecorator(baseProduct, 12.5);

        assertEquals(875.0, discounted.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Should handle very small discounts")
    void decorator_SmallDiscount_CalculatesCorrectly() {
        Sellable discounted = new DiscountDecorator(baseProduct, 0.5);

        assertEquals(995.0, discounted.getPrice(), 0.01);
    }

    // ========== SELLABLE INTERFACE TESTS ==========

    @Test
    @DisplayName("Decorated product should implement Sellable")
    void discountDecorator_ImplementsSellable_Correctly() {
        Sellable decorated = new DiscountDecorator(baseProduct, 15);

        assertNotNull(decorated.getId());
        assertNotNull(decorated.getName());
        assertTrue(decorated.getPrice() >= 0);
        assertNotNull(decorated.getDescription());
    }

    @Test
    @DisplayName("Should delegate getName to original product")
    void discountDecorator_GetName_DelegatesToOriginal() {
        Sellable decorated = new DiscountDecorator(baseProduct, 25);

        assertEquals(baseProduct.getName(), decorated.getName());
    }

    @Test
    @DisplayName("Should delegate getId to original product")
    void discountDecorator_GetId_DelegatesToOriginal() {
        Sellable decorated = new DiscountDecorator(baseProduct, 25);

        assertEquals(baseProduct.getId(), decorated.getId());
    }

    // ========== REAL WORLD SCENARIOS ==========

    @Test
    @DisplayName("Black Friday scenario: 30% discount")
    void realWorldScenario_BlackFriday_CalculatesCorrectly() {
        Sellable blackFriday = new DiscountDecorator(baseProduct, 30);

        assertEquals(700.0, blackFriday.getPrice(), 0.01);
        assertTrue(blackFriday.getDescription().toLowerCase().contains("rabatt"));
    }

    @Test
    @DisplayName("Student discount scenario: 15% off")
    void realWorldScenario_StudentDiscount_CalculatesCorrectly() {
        Sellable studentPrice = new DiscountDecorator(baseProduct, 15);

        assertEquals(850.0, studentPrice.getPrice(), 0.01);
    }

    @Test
    @DisplayName("VIP member scenario: 25% off")
    void realWorldScenario_VIPDiscount_CalculatesCorrectly() {
        Sellable vipPrice = new DiscountDecorator(baseProduct, 25);

        assertEquals(750.0, vipPrice.getPrice(), 0.01);
    }

    @Test
    @DisplayName("Clearance sale scenario: 50% off")
    void realWorldScenario_ClearanceSale_CalculatesCorrectly() {
        Sellable clearancePrice = new DiscountDecorator(baseProduct, 50);

        assertEquals(500.0, clearancePrice.getPrice(), 0.01);
    }
}
