package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class WarehouseTest {

    private Warehouse warehouse;

    @BeforeEach void warehouseSetup() {
        warehouse = new Warehouse(); //before each test
    }

    @Test void productAddedToWareHouseInventory() {
        Product laptop = new Product("Laptop", Category.ELECTRONICS, 4);
        assertTrue(warehouse.addProduct(laptop));
    }

    @Test void productAddedWithEmptyName() {
        Warehouse warehouse = new Warehouse();
        Product laptop = new Product("", Category.ELECTRONICS, 4);
        assertFalse(warehouse.addProduct(laptop));

    }

    @Test void updateProduct_Valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        warehouse.addProduct(lamp);

        String idAsString = String.valueOf(lamp.id());

        warehouse.getAllProducts()
                 .forEach(System.out::println);
        warehouse.updateProduct(idAsString, "blueLaptop", Category.FOOD, 3);


        //get our updated product
        Product updated = warehouse.getProductById(lamp.id()
                                                       .toString());

        assertEquals(lamp.id(), updated.id());
        assertEquals("blueLaptop", updated.name());
        assertEquals(Category.FOOD, updated.category());
        assertEquals(3, updated.rating());
    }

    @Test void updateProduct_Invalid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        warehouse.addProduct(lamp);
        assertThrows(IllegalArgumentException.class,
          () -> warehouse.updateProduct("3", "blueLaptop", Category.FOOD, 3));
    }

    @Test void getAllProducts_Valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product cpu = new Product("cpu", Category.ELECTRONICS, 2);

        warehouse.addProduct(lamp);
        warehouse.addProduct(cpu);

        List<Product> products = warehouse.getAllProducts();
        assertEquals(2, products.size());

    }

    @Test void getAllProducts_ShouldUpdateAfterRemoval() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product cpu = new Product("cpu", Category.ELECTRONICS, 2);

        warehouse.addProduct(lamp);
        warehouse.addProduct(cpu);

        warehouse.removeProductById(cpu.id()
                                       .toString());

        List<Product> products = warehouse.getAllProducts();
        assertEquals(1, products.size());
        assertFalse(products.contains(cpu));

    }

    @Test void getAllProducts_WhenEmpty_ShouldReturnEmptyList() {
        List<Product> products = warehouse.getAllProducts();
        assertEquals(0, products.size());
        assertNotNull(products);
        assertTrue(products.isEmpty());

    }

    @Test void getProductsByCategorySorted_valid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);
        Product dogFood = new Product("DogFood", Category.FOOD, 2);
        Product catFood = new Product("CatFood", Category.FOOD, 7);
        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(dogFood);
        warehouse.addProduct(catFood);

        List<Product> sortedByCategory = warehouse.getProductsByCategorySorted(
          Category.FOOD);

        assertEquals(2, sortedByCategory.size());

        assertEquals("CatFood", sortedByCategory.get(0)
                                                .name());
        assertEquals("DogFood", sortedByCategory.get(1)
                                                .name());
    }

    @Test void getProductsByCategorySorted_invalid() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);

        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);

        List<Product> sortedByCategory = warehouse.getProductsByCategorySorted(
          Category.FOOD);

        assertEquals(0, sortedByCategory.size(),
          "Should be 0, we have no Food items in our inventory");
        assertNotNull(sortedByCategory);
        assertTrue(sortedByCategory.isEmpty());
    }

    @Test void getProductsCreatedAfter_valid() {
        LocalDate testDate = LocalDate.now(); //todays date
        LocalDate daysFromCheck5 = LocalDate.now() // 5 After today
                                            .plusDays(5);

        Product lamp = new Product("lamp", Category.ELECTRONICS, 10, daysFromCheck5);
        Product pc = new Product("PC", Category.ELECTRONICS, 4, daysFromCheck5);
        Product ps5 = new Product("Super Delux Playstaion", Category.ELECTRONICS, 4);

        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(ps5);
        List<Product> productsCreaetedAfterList = warehouse.getProductsCreatedAfter(
          testDate);
        assertEquals(2, productsCreaetedAfterList.size());
    }

    @Test void getProductsCreatedAfter_invalid() {
        LocalDate testDate = LocalDate.now(); // todays date

        Product lamp = new Product("lamp", Category.ELECTRONICS, 10,
          testDate.minusDays(1));
        Product pc = new Product("PC", Category.ELECTRONICS, 4,
          testDate.minusDays(2));
        Product ps5 = new Product("Super Delux Playstaion", Category.ELECTRONICS, 6,
          testDate.minusDays(3));

        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(ps5);
        List<Product> productsCreatedAfterList = warehouse.getProductsCreatedAfter(
          testDate);
        assertEquals(0, productsCreatedAfterList.size());

    }

    @Test void getModifiedProducts_valid() {
        LocalDate testDate = LocalDate.now()
                                      .minusDays(2);
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10, testDate);
        Product pc = new Product("PC", Category.ELECTRONICS, 4, testDate);
        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);

        String idAsString = String.valueOf(lamp.id());
        warehouse.updateProduct(idAsString, "bed", Category.FOOD, 2);
        List<Product> getModifiedProductsList = warehouse.getModifiedProducts();

        assertEquals(1, getModifiedProductsList.size());
    }

    @Test void getModifiedProducts_invalid() {
        //when we modify a Product its modified date is set to the day its modified.
        //this can be changed by using LocalDateTime instead of LocalDate
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);
        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);

        String idAsString = String.valueOf(lamp.id());
        warehouse.updateProduct(idAsString, "bed", Category.FOOD, 2);


        List<Product> getModifiedProductsList = warehouse.getModifiedProducts();
        assertEquals(2, getModifiedProductsList.size());
    }


    //vg
    @Test void getCategoriesWithProducts_valid() {
        // 2 electronics 2 food 1 game
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);
        Product hotdog = new Product("HotDog", Category.FOOD, 4);
        Product angryFish = new Product("AngryFish", Category.FOOD, 6);
        Product sillyBirds = new Product("SillyBirds", Category.GAMES, 9);
        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(hotdog);
        warehouse.addProduct(angryFish);
        warehouse.addProduct(sillyBirds);

        List<Category> getCategoriesWithProductsList =
          warehouse.getCategoriesWithProducts();

        assertEquals(3, getCategoriesWithProductsList.size());
        assertTrue(getCategoriesWithProductsList.contains(Category.FOOD));
        assertTrue(getCategoriesWithProductsList.contains(Category.ELECTRONICS));
        assertTrue(getCategoriesWithProductsList.contains(Category.GAMES));
    }

    @Test void getCategoriesWithProducts_isEmpty() {
        List<Category> categories_emptyList = warehouse.getCategoriesWithProducts();
        assertEquals(0, categories_emptyList.size());
        assertTrue(categories_emptyList.isEmpty());
    }

    @Test void countProductsInCategory_valid() {
        // 2 electronics 2 food 1 game
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product("PC", Category.ELECTRONICS, 4);
        Product hotdog = new Product("HotDog", Category.FOOD, 4);
        Product angryFish = new Product("AngryFish", Category.FOOD, 6);
        Product sillyBirds = new Product("SillyBirds", Category.GAMES, 9);
        warehouse.addProduct(lamp);
        warehouse.addProduct(pc);
        warehouse.addProduct(hotdog);
        warehouse.addProduct(angryFish);
        warehouse.addProduct(sillyBirds);


        //when we ask for a Category food, we should get back 2
        long amountOfProductsInFood = warehouse.countProductsInCategory(
          Category.FOOD);


        assertEquals(2, amountOfProductsInFood);
        assertEquals(2, warehouse.countProductsInCategory(Category.ELECTRONICS));
        assertEquals(1, warehouse.countProductsInCategory(Category.GAMES));

    }

    @Test void countProductsInCategory_invalid_nullCategory() {
        /*
        Test for null input and string-based parsing
         */

        assertThrows(IllegalArgumentException.class,
          () -> warehouse.countProductsInCategory(null));
        assertThrows(IllegalArgumentException.class,
          () -> warehouse.countProductsInCategory(Category.valueOf("Toys")));

    }

    @Test void getProductInitialsMap() {
        Product lamp = new Product("lamp", Category.ELECTRONICS, 10);
        Product LargePc = new Product("LargePC", Category.ELECTRONICS, 4);
        Product hotdog = new Product("HotDog", Category.FOOD, 4);
        Product angryFish = new Product("AngryFish", Category.FOOD, 6);
        Product sillyBirds = new Product("SillyBirds", Category.GAMES, 9);
        warehouse.addProduct(lamp);
        warehouse.addProduct(LargePc);
        warehouse.addProduct(hotdog);
        warehouse.addProduct(angryFish);
        warehouse.addProduct(sillyBirds);

        Map<Character, Integer> FirstLetterAndCountOfItem =
          warehouse.getProductInitialsMap();
        assertEquals(4, FirstLetterAndCountOfItem.size());

    }

    @Test void getProductInitialsMap_invalid_nonLetterInitials() {
        Product emptyNameProduct = new Product("1Lamp", Category.ELECTRONICS, 4);
        warehouse.addProduct(emptyNameProduct);
        Map<Character, Integer> initialsMap = warehouse.getProductInitialsMap();
        System.out.println(initialsMap);
        assertTrue(initialsMap.containsKey('1'));
    }

    @Test void getTopRatedProductsThisMonth_valid_CheckCorrectOrder() {
        LocalDate today = LocalDate.now();


        Product lamp = new Product("lamp", Category.ELECTRONICS, 10,
          today.minusMonths(1));
        Product LargePc = new Product("LargePC", Category.ELECTRONICS, 8);
        Product hotdog = new Product("HotDog", Category.FOOD, 10,
          today.plusMonths(1));
        //this should be the last one cuz we  create that 3 days before everyone else
        Product angryFish = new Product("AngryFish", Category.FOOD, 10,
          today.minusDays(3));
        //this should be the first one cuz we create it 4 days in the future
        Product sillyBirds = new Product("SillyBirds", Category.GAMES, 10,
          today.plusDays(4));
        Product sadBirds = new Product("SadBirds", Category.GAMES, 10, today);
        Product heavyBirds = new Product("HeavyBirds", Category.GAMES, 10, today);
        warehouse.addProduct(lamp);
        warehouse.addProduct(LargePc);
        warehouse.addProduct(hotdog);
        warehouse.addProduct(angryFish);
        warehouse.addProduct(sillyBirds);
        warehouse.addProduct(sadBirds);
        warehouse.addProduct(heavyBirds);

        List<Product> result = warehouse.getTopRatedProductsThisMonth();

        assertEquals(4, result.size());
        assertEquals("SillyBirds", result.get(0)
                                         .name());
        assertEquals("AngryFish", result.get(3)
                                        .name());
    }

    @Test void getTopRatedProductsThisMonth_invalid_nonMatching() {
        LocalDate lastMonth = LocalDate.now()
                                       .minusMonths(1);

        Product lamp = new Product("lamp", Category.ELECTRONICS, 10, lastMonth);
        Product LargePc = new Product("LargePC", Category.ELECTRONICS, 8, lastMonth);
        Product hotdog = new Product("HotDog", Category.FOOD, 10, lastMonth);
        warehouse.addProduct(lamp);
        warehouse.addProduct(LargePc);
        warehouse.addProduct(hotdog);

        List<Product> result = warehouse.getTopRatedProductsThisMonth();

        assertTrue(result.isEmpty());
    }

}