package exercise3.service;

import exercise3.entities.Category;
import exercise3.entities.Product;
import exercise3.repository.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManagingProductTest {

    Product.ProductBuilder builder = new Product.ProductBuilder();
    InMemoryProductRepository managingProduct  = new InMemoryProductRepository();
    ProductService productService  = new ProductService(managingProduct);

    @Test
    public void productIsAddedToListWhenCreated() {

        managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(1,managingProduct.getAllProducts().size());
        assertEquals("Knitted shirt",managingProduct.getAllProducts().getFirst().getName());
        assertEquals(Category.SHIRT, managingProduct.getAllProducts().getFirst().getCategory());
        assertEquals(LocalDate.now(), managingProduct.getAllProducts().getFirst().getCreatedDate());
        assertEquals(LocalDate.now(), managingProduct.getAllProducts().getFirst().getModifiedDate());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfProductWithUniqIdAlreadyExist() {
        managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(1).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertThrows(IllegalArgumentException.class, () -> managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(1).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct()));
    }

    @Test
    public void productIsAddedToListWhenUpdated() {
        managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.of(2025, 9, 1)).setModifiedDate(LocalDate.of(2025, 9, 1)).createProduct());
        Product itemToUpdate = managingProduct.getProductById("Bella");
        managingProduct.updateProduct(builder.from(itemToUpdate).setRating(3).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(2,managingProduct.getAllProducts().size());
        assertEquals(1, productService.getModifiedProducts().size());

    }

    @Test
    public void throwsIllegalArgumentExceptionIfNothingIsUpdated() {
        managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        Product itemToUpdate = managingProduct.getProductById("Bella");

        assertThrows(IllegalArgumentException.class, () -> managingProduct.updateProduct(builder.from(itemToUpdate).createProduct()));
    }

    @Test
    public void allProductsAreAddedToList(){
        managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Tina").setName("Flared jeans").setCategory(Category.JEANS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed pants").setCategory(Category.PANTS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(3,managingProduct.getAllProducts().size());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenListIsEmpty() {

        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getAllProducts());
    }

    @Test
    public void returnsCorrectIdWhenSearchingProductById(){
        managingProduct.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Tina").setName("Flared jeans").setCategory(Category.JEANS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals("Bella", managingProduct.getProductById("Bella").getId());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenSearchingProductById(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductById("Tina"));
    }

    @Test
    public void returnsListSortedByCategoryInAlphabeticOrder(){
        managingProduct.addProduct(builder.setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List <Product> sortedProducts = productService.getProductsByCategorySorted(Category.DRESS);

        assertEquals(4,sortedProducts.size());
        assertEquals("Cocktail dress", sortedProducts.getFirst().getName());
        assertEquals("Dressed dress",sortedProducts.get(1).getName());
        assertEquals("Evening dress",sortedProducts.get(2).getName());
        assertEquals("Knitted dress",sortedProducts.get(3).getName());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToSortIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> productService.getProductsByCategorySorted(Category.DRESS));
    }

    @Test
    public void returnProductsCreatedAfterAGivenDate(){

        managingProduct.addProduct(builder.setId("Tina").setName("Flared jeans").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.of(2025, 9, 1)).setModifiedDate(LocalDate.of(2025, 9, 1)).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.of(2025, 9, 2)).setModifiedDate(LocalDate.of(2025, 9, 2)).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.of(2025, 9, 5)).setModifiedDate(LocalDate.of(2025, 9, 5)).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 7)).setModifiedDate(LocalDate.of(2025, 9, 7)).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 8)).setModifiedDate(LocalDate.of(2025, 9, 8)).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Product>  sortedProducts = productService.getProductsCreatedAfter(LocalDate.of(2025,9,3));

        assertEquals(4,sortedProducts.size());

    }

    @Test
    public void throwsIllegalArgumentExpressionIfListToFilterForGivenDayIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> productService.getProductsCreatedAfter(LocalDate.now()));
    }

    @Test
    public void returnsProductsWhereCreatedDatedNotIsEqualToModifiedDate(){
        managingProduct.addProduct(builder.setId("Tina").setName("Flared jeans").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.of(2025, 9, 1)).setModifiedDate(LocalDate.of(2025, 9, 1)).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.of(2025, 9, 2)).setModifiedDate(LocalDate.of(2025, 9, 2)).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.of(2025, 9, 5)).setModifiedDate(LocalDate.of(2025, 9, 5)).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 7)).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 8)).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Product>  sortedProducts = productService.getModifiedProducts();

        assertEquals(2,sortedProducts.size());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForModifiedDateIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> productService.getModifiedProducts());
    }

    @Test
    public void returnsAllProductsWithAtLeastOneProduct(){
        managingProduct.addProduct(builder.setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Category> categories = productService.getCategoriesWithProducts();

        assertEquals(3,categories.size());
        assertEquals(Category.JEANS,categories.getFirst());
        assertEquals(Category.PANTS,categories.get(1));
        assertEquals(Category.DRESS,categories.getLast());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForProductsWithAtLeastOneProductIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> productService.getCategoriesWithProducts());
    }

    @Test
    public void returnNumberOfProductsInACategory(){
        managingProduct.addProduct(builder.setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(4, productService.countProductsInCategory(Category.DRESS));
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForProductsInCategoryIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> productService.countProductsInCategory(Category.DRESS));
    }

    @Test
    public void returnsAMapWithFirstLetterInProductAndTheirCount(){
        managingProduct.addProduct(builder.setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        Map<Character, Integer> products = productService.getProductsInitialsMap();

        assertEquals(3, products.size());
        assertEquals(4, products.get('D'));
        assertEquals(1, products.get('J'));
        assertEquals(1, products.get('P'));

    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToCollectCharacterAndCountIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> productService.getProductsInitialsMap());
    }

    @Test
    public void returnsProductsWithMaxRatingSortedForThisMonth(){

        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate secondDay = today.withDayOfMonth(2);

        managingProduct.addProduct(builder.setId("Tina").setName("Flared jeans").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.of(2025, 8, 1)).setModifiedDate(LocalDate.of(2025, 8, 1)).createProduct());
        managingProduct.addProduct(builder.setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.of(2025, 8, 2)).setModifiedDate(LocalDate.of(2025, 8, 2)).createProduct());
        managingProduct.addProduct(builder.setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(7).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(firstDay).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(builder.setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(secondDay).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(2, productService.getTopProductsThisMonth().size());
        assertEquals("Milo", productService.getTopProductsThisMonth().getFirst().getId());
        assertEquals("Leon", productService.getTopProductsThisMonth().getLast().getId());

    }
}