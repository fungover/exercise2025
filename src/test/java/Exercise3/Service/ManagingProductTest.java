package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;
import Exercise3.Entities.ProductBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManagingProductTest {

    ManagingProduct managingProduct = new ManagingProduct();

    @Test
    public void productIsAddedToListWhenCreated() {

        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(1,managingProduct.getAllProducts().size());
        assertEquals("Knitted shirt",managingProduct.getAllProducts().getFirst().name());
        assertEquals(Category.SHIRT, managingProduct.getAllProducts().getFirst().category());
        assertEquals(LocalDate.now(), managingProduct.getAllProducts().getFirst().createdDate());
        assertEquals(LocalDate.now(), managingProduct.getAllProducts().getFirst().modifiedDate());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfProductAlreadyExist() {
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(1).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertThrows(IllegalArgumentException.class, () -> managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(1).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct()));
    }

    @Test
    public void productIsAddedToListWhenUpdated() {
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.of(2025, 9, 1)).setModifiedDate(LocalDate.of(2025, 9, 1)).createProduct());
        managingProduct.updateProduct("Bella", "Knitted shirt", Category.SHIRT, 7);

       assertEquals(2,managingProduct.getAllProducts().size());
       assertEquals(1, managingProduct.getModifiedProducts().size());

    }

    @Test
    public void throwsIllegalArgumentExceptionWhenProductToUpdateDoseNotExist(){
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertThrows(IllegalArgumentException.class, () -> managingProduct.updateProduct("Tina", "Dressed Shirt",  Category.SHIRT, 9));
    }

    @Test
    public void allProductsAreAddedToList(){
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Flared jeans").setCategory(Category.JEANS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed pants").setCategory(Category.PANTS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(3,managingProduct.getAllProducts().size());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenListIsEmpty() {

        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getAllProducts());
    }

    @Test
    public void returnsCorrectIdWhenSearchingProductById(){
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Flared jeans").setCategory(Category.JEANS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals("Bella", managingProduct.getProductById("Bella").id());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenSearchingProductById(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductById("Tina"));
    }

    @Test
    public void returnsListSortedByCategoryInAlphabeticOrder(){
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List <Product> sortedProducts = managingProduct.getProductsByCategorySorted(Category.DRESS);

        assertEquals(4,sortedProducts.size());
        assertEquals("Cocktail dress", sortedProducts.getFirst().name());
        assertEquals("Dressed dress",sortedProducts.get(1).name());
        assertEquals("Evening dress",sortedProducts.get(2).name());
        assertEquals("Knitted dress",sortedProducts.get(3).name());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToSortIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductsByCategorySorted(Category.DRESS));
    }

    @Test
    public void returnProductsCreatedAfterAGivenDate(){

        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Flared jeans").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.of(2025, 9, 1)).setModifiedDate(LocalDate.of(2025, 9, 1)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.of(2025, 9, 2)).setModifiedDate(LocalDate.of(2025, 9, 2)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.of(2025, 9, 5)).setModifiedDate(LocalDate.of(2025, 9, 5)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 7)).setModifiedDate(LocalDate.of(2025, 9, 7)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 8)).setModifiedDate(LocalDate.of(2025, 9, 8)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Product>  sortedProducts = managingProduct.getProductsCreatedAfter(LocalDate.of(2025,9,3));

        assertEquals(4,sortedProducts.size());

    }

    @Test
    public void throwsIllegalArgumentExpressionIfListToFilterForGivenDayIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductsCreatedAfter(LocalDate.now()));
    }

    @Test
    public void returnsProductsWhereCreatedDatedNotIsEqualToModifiedDate(){
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Flared jeans").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.of(2025, 9, 1)).setModifiedDate(LocalDate.of(2025, 9, 1)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.of(2025, 9, 2)).setModifiedDate(LocalDate.of(2025, 9, 2)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.of(2025, 9, 5)).setModifiedDate(LocalDate.of(2025, 9, 5)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 7)).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.of(2025, 9, 8)).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Product>  sortedProducts = managingProduct.getModifiedProducts();

        assertEquals(2,sortedProducts.size());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForModifiedDateIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getModifiedProducts());
    }

    @Test
    public void returnsAllProductsWithAtLeastOneProduct(){
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Category> categories = managingProduct.getCategoriesWithProducts();

        assertEquals(3,categories.size());
        assertEquals(Category.JEANS,categories.getFirst());
        assertEquals(Category.PANTS,categories.get(1));
        assertEquals(Category.DRESS,categories.getLast());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForProductsWithAtLeastOneProductIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getCategoriesWithProducts());
    }

    @Test
    public void returnNumberOfProductsInACategory(){
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(4, managingProduct.countProductsInCategory(Category.DRESS));
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForProductsInCategoryIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.countProductsInCategory(Category.DRESS));
    }

    @Test
    public void returnsAMapWithFirstLetterInProductAndTheirCount(){
        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Knitted dress").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        Map<Character, Integer> products = managingProduct.getProductsInitialsMap();

        assertEquals(3, products.size());
        assertEquals(4, products.get('D'));
        assertEquals(1, products.get('J'));
        assertEquals(1, products.get('P'));

    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToCollectCharacterAndCountIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductsInitialsMap());
    }

    @Test
    public void returnsProductsWithMaxRatingSortedForThisMonth(){

        LocalDate today = LocalDate.now();
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDate secondDay = today.withDayOfMonth(2);

        managingProduct.addProduct(new ProductBuilder().setId("Tina").setName("Flared jeans").setCategory(Category.DRESS).setRating(9).setCreatedDate(LocalDate.of(2025, 8, 1)).setModifiedDate(LocalDate.of(2025, 8, 1)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Joell").setName("Dressed dress").setCategory(Category.DRESS).setRating(6).setCreatedDate(LocalDate.of(2025, 8, 2)).setModifiedDate(LocalDate.of(2025, 8, 2)).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Bella").setName("Evening dress").setCategory(Category.DRESS).setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Anna").setName("Cocktail dress").setCategory(Category.DRESS).setRating(7).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Milo").setName("Flared jeans").setCategory(Category.JEANS).setRating(10).setCreatedDate(firstDay).setModifiedDate(LocalDate.now()).createProduct());
        managingProduct.addProduct(new ProductBuilder().setId("Leon").setName("Dressed pants").setCategory(Category.PANTS).setRating(10).setCreatedDate(secondDay).setModifiedDate(LocalDate.now()).createProduct());

        assertEquals(2, managingProduct.getTopProductsThisMonth().size());
        assertEquals("Milo", managingProduct.getTopProductsThisMonth().getFirst().id());
        assertEquals("Leon", managingProduct.getTopProductsThisMonth().getLast().id());

    }
}