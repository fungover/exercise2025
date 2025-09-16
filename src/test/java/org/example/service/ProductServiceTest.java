package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;
import org.example.repository.inMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class ProductServiceTest {

    private ProductService productService;

    @BeforeEach void warehouseSetup() {
        // create repository
        ProductRepository repository = new inMemoryProductRepository();

        this.productService = new ProductService(repository);//before each test

    }

    @Test void productAddedToWareHouseInventory() {
        Product laptop = new Product.Builder().name("Laptop")
                                              .category(Category.ELECTRONICS)
                                              .rating(4)
                                              .build();


        assertTrue(productService.addProduct(laptop));
    }

    @Test void productAddedWithEmptyName() {
//        ProductService productService = new ProductService();
//        Product laptop = new Product.Builder().name("")
//                                              .category(Category.ELECTRONICS)
//                                              .rating(4)
//                                              .build();
        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(
          new Product.Builder().name("")
                               .category(Category.ELECTRONICS)
                               .rating(4)
                               .build()));
//        assertFalse(productService.addProduct(laptop));

    }

    @Test void updateProduct_Valid() {
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();

        productService.addProduct(lamp);

        String idAsString = String.valueOf(lamp.id());

        productService.getAllProducts()
                      .forEach(System.out::println);
        productService.updateProduct(idAsString, "blueLaptop", Category.FOOD, 3);


        //get our updated product
        Product updated = productService.getProductById(lamp.id()
                                                            .toString());

        assertEquals(lamp.id(), updated.id());
        assertEquals("blueLaptop", updated.name());
        assertEquals(Category.FOOD, updated.category());
        assertEquals(3, updated.rating());
    }

    @Test void updateProduct_Invalid() {
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();


        productService.addProduct(lamp);
        assertThrows(IllegalArgumentException.class,
          () -> productService.updateProduct("3", "blueLaptop", Category.FOOD, 3));
    }

    @Test void getAllProducts_Valid() {
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();
        Product cpu = new Product.Builder().name("cpu")
                                           .category(Category.ELECTRONICS)
                                           .rating(2)
                                           .build();


        productService.addProduct(lamp);
        productService.addProduct(cpu);

        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());

    }

    @Test void getAllProducts_ShouldUpdateAfterRemoval() {
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();
        Product cpu = new Product.Builder().name("cpu")
                                           .category(Category.ELECTRONICS)
                                           .rating(2)
                                           .build();

        productService.addProduct(lamp);
        productService.addProduct(cpu);

        productService.removeProductById(cpu.id()
                                            .toString());

        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        assertFalse(products.contains(cpu));

    }

    @Test void getAllProducts_WhenEmpty_ShouldReturnEmptyList() {
        List<Product> products = productService.getAllProducts();
        assertEquals(0, products.size());
        assertNotNull(products);
        assertTrue(products.isEmpty());

    }

    @Test void getProductsByCategorySorted_valid() {
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();

        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .build();


        Product dogFood = new Product.Builder().name("DogFood")
                                               .category(Category.FOOD)
                                               .rating(2)
                                               .build();

        Product catFood = new Product.Builder().name("CatFood")
                                               .category(Category.FOOD)
                                               .rating(7)
                                               .build();


        productService.addProduct(lamp);
        productService.addProduct(pc);
        productService.addProduct(dogFood);
        productService.addProduct(catFood);

        List<Product> sortedByCategory = productService.getProductsByCategorySorted(
          Category.FOOD);

        assertEquals(2, sortedByCategory.size());

        assertEquals("CatFood", sortedByCategory.get(0)
                                                .name());
        assertEquals("DogFood", sortedByCategory.get(1)
                                                .name());
    }

    @Test void getProductsByCategorySorted_invalid() {
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();

        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .build();

        productService.addProduct(lamp);
        productService.addProduct(pc);

        List<Product> sortedByCategory = productService.getProductsByCategorySorted(
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

        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .createdDate(daysFromCheck5)
                                            .build();
//          ("lamp", Category.ELECTRONICS, 10, daysFromCheck5);
        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .createdDate(daysFromCheck5)
                                          .build();
//          ("PC", Category.ELECTRONICS, 4, daysFromCheck5);

        Product ps5 = new Product.Builder().name("Super Delux Playstaion")
                                           .category(Category.ELECTRONICS)
                                           .rating(4)
                                           .build();
//          ("Super Delux Playstaion", Category.ELECTRONICS, 4);

        productService.addProduct(lamp);
        productService.addProduct(pc);
        productService.addProduct(ps5);
        List<Product> productsCreaetedAfterList =
          productService.getProductsCreatedAfter(
          testDate);
        assertEquals(2, productsCreaetedAfterList.size());
    }

    @Test void getProductsCreatedAfter_invalid() {
        LocalDate testDate = LocalDate.now(); // todays date

        Product lamp = new Product.Builder().name("lmap")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .createdDate(testDate.minusDays(1))
                                            .build();

//          ("lamp", Category.ELECTRONICS, 10,
//          testDate.minusDays(1));

        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .createdDate(testDate.minusDays(2))
                                          .build();
//          ("PC", Category.ELECTRONICS, 4,
//          testDate.minusDays(2));
        Product ps5 = new Product.Builder().name("Super Delux Playstaion")
                                           .category(Category.ELECTRONICS)
                                           .rating(6)
                                           .createdDate(testDate.minusDays(3))
                                           .build();
//          ("Super Delux Playstaion", Category.ELECTRONICS, 6,
//          testDate.minusDays(3));

        productService.addProduct(lamp);
        productService.addProduct(pc);
        productService.addProduct(ps5);
        List<Product> productsCreatedAfterList =
          productService.getProductsCreatedAfter(
          testDate);
        assertEquals(0, productsCreatedAfterList.size());

    }

    @Test void getModifiedProducts_valid() {
        LocalDate testDate = LocalDate.now()
                                      .minusDays(2);
        Product lamp = new Product.Builder().name("lmap")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .createdDate(testDate)
                                            .modifiedDate(testDate)

                                            .build();
//          ("lamp", Category.ELECTRONICS, 10, testDate);
        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .createdDate(testDate)
                                          .modifiedDate(testDate)
                                          .build();
//          ("PC", Category.ELECTRONICS, 4, testDate);

        productService.addProduct(lamp);
        productService.addProduct(pc);

        String idAsString = String.valueOf(lamp.id());
        productService.updateProduct(idAsString, "bed", Category.FOOD, 2);
        List<Product> getModifiedProductsList = productService.getModifiedProducts();

        assertEquals(1, getModifiedProductsList.size());
    }

    @Test void getModifiedProducts_invalid() {
        //when we modify a Product its modified date is set to the day its modified.
        //this can be changed by using LocalDateTime instead of LocalDate
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();
//          ("lamp", Category.ELECTRONICS, 10);
        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .build();
//          ("PC", Category.ELECTRONICS, 4);
        productService.addProduct(lamp);
        productService.addProduct(pc);

        String idAsString = String.valueOf(lamp.id());
        productService.updateProduct(idAsString, "bed", Category.FOOD, 2);


        List<Product> getModifiedProductsList = productService.getModifiedProducts();
        assertEquals(0, getModifiedProductsList.size());
    }


    //vg
    @Test void getCategoriesWithProducts_valid() {
        // 2 electronics 2 food 1 game
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();
        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .build();
        Product hotdog = new Product.Builder().name("HotDog")
                                              .category(Category.FOOD)
                                              .rating(4)
                                              .build();
//          ("HotDog", Category.FOOD, 4);
        Product angryFish = new Product.Builder().name("AngryFish")
                                                 .category(Category.FOOD)
                                                 .rating(6)
                                                 .build();
//          ("AngryFish", Category.FOOD, 6);
        Product sillyBirds = new Product.Builder().name("SillyBirds")
                                                  .category(Category.GAMES)
                                                  .rating(9)
                                                  .build();
//          ("SillyBirds", Category.GAMES, 9);
        productService.addProduct(lamp);
        productService.addProduct(pc);
        productService.addProduct(hotdog);
        productService.addProduct(angryFish);
        productService.addProduct(sillyBirds);

        List<Category> getCategoriesWithProductsList =
          productService.getCategoriesWithProducts();

        assertEquals(3, getCategoriesWithProductsList.size());
        assertTrue(getCategoriesWithProductsList.contains(Category.FOOD));
        assertTrue(getCategoriesWithProductsList.contains(Category.ELECTRONICS));
        assertTrue(getCategoriesWithProductsList.contains(Category.GAMES));
    }

    @Test void getCategoriesWithProducts_isEmpty() {
        List<Category> categories_emptyList =
          productService.getCategoriesWithProducts();
        assertEquals(0, categories_emptyList.size());
        assertTrue(categories_emptyList.isEmpty());
    }

    @Test void countProductsInCategory_valid() {
        // 2 electronics 2 food 1 game
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();
        Product pc = new Product.Builder().name("PC")
                                          .category(Category.ELECTRONICS)
                                          .rating(4)
                                          .build();
        Product hotdog = new Product.Builder().name("HotDog")
                                              .category(Category.FOOD)
                                              .rating(4)
                                              .build();
//          ("HotDog", Category.FOOD, 4);
        Product angryFish = new Product.Builder().name("AngryFish")
                                                 .category(Category.FOOD)
                                                 .rating(6)
                                                 .build();
//          ("AngryFish", Category.FOOD, 6);
        Product sillyBirds = new Product.Builder().name("SillyBirds")
                                                  .category(Category.GAMES)
                                                  .rating(9)
                                                  .build();
        productService.addProduct(lamp);
        productService.addProduct(pc);
        productService.addProduct(hotdog);
        productService.addProduct(angryFish);
        productService.addProduct(sillyBirds);


        //when we ask for a Category food, we should get back 2
        long amountOfProductsInFood = productService.countProductsInCategory(
          Category.FOOD);


        assertEquals(2, amountOfProductsInFood);
        assertEquals(2,
          productService.countProductsInCategory(Category.ELECTRONICS));
        assertEquals(1, productService.countProductsInCategory(Category.GAMES));

    }

    @Test void countProductsInCategory_invalid_nullCategory() {
        /*
        Test for null input and string-based parsing
         */

        assertThrows(IllegalArgumentException.class,
          () -> productService.countProductsInCategory(null));
        assertThrows(IllegalArgumentException.class,
          () -> productService.countProductsInCategory(Category.valueOf("Toys")));

    }

    @Test void getProductInitialsMap() {


        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .build();
        Product LargePc = new Product.Builder().name("LargePC")
                                               .category(Category.ELECTRONICS)
                                               .rating(4)
                                               .build();
        Product hotdog = new Product.Builder().name("HotDog")
                                              .category(Category.FOOD)
                                              .rating(4)
                                              .build();
//          ("HotDog", Category.FOOD, 4);
        Product angryFish = new Product.Builder().name("AngryFish")
                                                 .category(Category.FOOD)
                                                 .rating(6)
                                                 .build();
//          ("AngryFish", Category.FOOD, 6);
        Product sillyBirds = new Product.Builder().name("SillyBirds")
                                                  .category(Category.GAMES)
                                                  .rating(9)
                                                  .build();
        productService.addProduct(lamp);
        productService.addProduct(LargePc);
        productService.addProduct(hotdog);
        productService.addProduct(angryFish);
        productService.addProduct(sillyBirds);

        Map<Character, Integer> FirstLetterAndCountOfItem =
          productService.getProductInitialsMap();
        assertEquals(4, FirstLetterAndCountOfItem.size());

    }

    @Test void getProductInitialsMap_invalid_nonLetterInitials() {
        Product emptyNameProduct = new Product.Builder().name("1Lamp")
                                                        .category(
                                                          Category.ELECTRONICS)
                                                        .rating(4)
                                                        .build();
//          ("1Lamp", Category.ELECTRONICS, 4);
        productService.addProduct(emptyNameProduct);
        Map<Character, Integer> initialsMap = productService.getProductInitialsMap();
        System.out.println(initialsMap);
        assertTrue(initialsMap.containsKey('1'));
    }

    @Test void getTopRatedProductsThisMonth_valid_CheckCorrectOrder() {
        LocalDate today = LocalDate.now();
        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .createdDate(today.minusMonths(1))
                                            .build();
        Product LargePc = new Product.Builder().name("LargePC")
                                               .category(Category.ELECTRONICS)
                                               .rating(8)
                                               .build();
        Product hotdog = new Product.Builder().name("HotDog")
                                              .category(Category.FOOD)
                                              .rating(10)
                                              .createdDate(today.plusMonths(1))
                                              .build();
//          ("HotDog", Category.FOOD, 4);
        //this should be the last one cuz we  create that 3 days before everyone else
        Product angryFish = new Product.Builder().name("AngryFish")
                                                 .category(Category.FOOD)
                                                 .rating(10)
                                                 .createdDate(today.minusDays(3))
                                                 .build();
//this should be the first one cuz we create it 4 days in the future
        Product sillyBirds = new Product.Builder().name("SillyBirds")
                                                  .category(Category.GAMES)
                                                  .rating(10)
                                                  .createdDate(today.plusDays(4))
                                                  .build();


        Product sadBirds = new Product.Builder().name("SadBirds")
                                                .category(Category.GAMES)
                                                .rating(10)
                                                .createdDate(today)
                                                .build();

        Product heavyBirds = new Product.Builder().name("HeavyBirds")
                                                  .category(Category.GAMES)
                                                  .rating(10)
                                                  .createdDate(today)
                                                  .build();

        productService.addProduct(lamp);
        productService.addProduct(LargePc);
        productService.addProduct(hotdog);
        productService.addProduct(angryFish);
        productService.addProduct(sillyBirds);
        productService.addProduct(sadBirds);
        productService.addProduct(heavyBirds);

        List<Product> result = productService.getTopRatedProductsThisMonth();

        assertEquals(4, result.size());
        assertEquals("SillyBirds", result.get(0)
                                         .name());
        assertEquals("AngryFish", result.get(3)
                                        .name());
    }

    @Test void getTopRatedProductsThisMonth_invalid_nonMatching() {
        LocalDate lastMonth = LocalDate.now()
                                       .minusMonths(1);

        Product lamp = new Product.Builder().name("lamp")
                                            .category(Category.ELECTRONICS)
                                            .rating(10)
                                            .createdDate(lastMonth)
                                            .build();

        Product LargePc = new Product.Builder().name("largePC")
                                               .category(Category.ELECTRONICS)
                                               .rating(8)
                                               .createdDate(lastMonth)
                                               .build();

        Product hotdog = new Product.Builder().name("HotDog")
                                              .category(Category.FOOD)
                                              .rating(10)
                                              .createdDate(lastMonth)
                                              .build();

        productService.addProduct(lamp);
        productService.addProduct(LargePc);
        productService.addProduct(hotdog);

        List<Product> result = productService.getTopRatedProductsThisMonth();

        assertTrue(result.isEmpty());
    }

}