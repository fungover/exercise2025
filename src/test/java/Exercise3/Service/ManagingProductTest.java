package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ManagingProductTest {

    ManagingProduct managingProduct = new ManagingProduct();

    @Test
    public void productIsAddedToListWhenCreated() {

        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9, LocalDate.now(), LocalDate.now()));

        assertEquals(1,managingProduct.getAllProducts().size());
        assertEquals("Knitted shirt",managingProduct.getAllProducts().getFirst().name());
        assertEquals(Category.SHIRT, managingProduct.getAllProducts().getFirst().category());
        assertEquals(LocalDate.now(), managingProduct.getAllProducts().getFirst().createdDate());
        assertEquals(LocalDate.now(), managingProduct.getAllProducts().getFirst().modifiedDate());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfProductAlreadyExist() {
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 1,LocalDate.now(), LocalDate.now()));

        assertThrows(IllegalArgumentException.class, () -> managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 1,LocalDate.now(), LocalDate.now())));
    }

    @Test
    public void productIsAddedToListWhenUpdated() {
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9, LocalDate.of(2025,9,1), LocalDate.of(2025,9,1)  ));
        managingProduct.updateProduct("Bella", "Knitted shirt", Category.SHIRT, 7);

       assertEquals(2,managingProduct.getAllProducts().size());
       assertEquals(1, managingProduct.getModifiedProducts().size());

    }

    @Test
    public void throwsIllegalArgumentExceptionWhenProductToUpdateDoseNotExist(){
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9, LocalDate.now(), LocalDate.now()));

        assertThrows(IllegalArgumentException.class, () -> managingProduct.updateProduct("Tina", "Dressed Shirt",  Category.SHIRT, 9));
    }

    @Test
    public void allProductsAreAddedToList(){
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.JEANS, 9,  LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Joell", "Dressed pants", Category.PANTS, 9,  LocalDate.now(), LocalDate.now()));

        assertEquals(3,managingProduct.getAllProducts().size());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenListIsEmpty() {

        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getAllProducts());
    }

    @Test
    public void returnsCorrectIdWhenSearchingProductById(){
        managingProduct.addProduct(new Product("Bella", "Knitted shirt", Category.SHIRT, 9, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.JEANS, 9,  LocalDate.now(), LocalDate.now()));

        assertEquals("Bella", managingProduct.getProductById("Bella").id());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenSearchingProductById(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductById("Tina"));
    }

    @Test
    public void returnsListSortedByCategoryInAlphabeticOrder(){
        managingProduct.addProduct(new Product("Tina", "Knitted dress", Category.DRESS, 9, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10, LocalDate.now(), LocalDate.now()));

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

        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.DRESS, 9, LocalDate.of(2025,9,1), LocalDate.of(2025,9,1)));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6, LocalDate.of(2025,9,2), LocalDate.of(2025,9,2)));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.of(2025,9,5), LocalDate.of(2025,9,5)));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10,  LocalDate.of(2025,9,7), LocalDate.of(2025,9,7)));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10,  LocalDate.of(2025,9,8), LocalDate.of(2025,9,8)));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10, LocalDate.now(),LocalDate.now()));

        List<Product>  sortedProducts = managingProduct.getProductsCreatedAfter(LocalDate.of(2025,9,3));

        assertEquals(4,sortedProducts.size());

    }

    @Test
    public void throwsIllegalArgumentExpressionIfListToFilterForGivenDayIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getProductsCreatedAfter(LocalDate.now()));
    }

    @Test
    public void returnsProductsWhereCreatedDatedNotIsEqualToModifiedDate(){
        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.DRESS, 9, LocalDate.of(2025,9,1), LocalDate.of(2025,9,1)));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6, LocalDate.of(2025,9,2), LocalDate.of(2025,9,2)));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.of(2025,9,5), LocalDate.of(2025,9,5)));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10,  LocalDate.of(2025,9,7), LocalDate.now()));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10,  LocalDate.of(2025,9,8), LocalDate.now()));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10, LocalDate.now(), LocalDate.now()));

        List<Product>  sortedProducts = managingProduct.getModifiedProducts();

        assertEquals(2,sortedProducts.size());
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForModifiedDateIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getModifiedProducts());
    }

    @Test
    public void returnsAllProductsWithAtLeastOneProduct(){
        managingProduct.addProduct(new Product("Tina", "Knitted dress", Category.DRESS, 9,LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6,LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10, LocalDate.now(), LocalDate.now()));

        List<Category> categories = managingProduct.getCategoriesWithProducts();

        assertEquals(3,categories.size());
        assertEquals(Category.PANTS,categories.getFirst());
        assertEquals(Category.JEANS,categories.get(1));
        assertEquals(Category.DRESS,categories.getLast());

    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForProductsWithAtLeastOneProductIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.getCategoriesWithProducts());
    }

    @Test
    public void returnNumberOfProductsInACategory(){
        managingProduct.addProduct(new Product("Tina", "Knitted dress", Category.DRESS, 9, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10, LocalDate.now(), LocalDate.now()));

        assertEquals(4, managingProduct.countProductsInCategory(Category.DRESS));
    }

    @Test
    public void throwsIllegalArgumentExceptionIfListToFilterForProductsInCategoryIsEmpty(){
        assertThrows(IllegalArgumentException.class, ()-> managingProduct.countProductsInCategory(Category.DRESS));
    }

    @Test
    public void returnsAMapWithFirstLetterInProductAndTheirCount(){
        managingProduct.addProduct(new Product("Tina", "Knitted dress", Category.DRESS, 9, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10, LocalDate.now(), LocalDate.now()));

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

        managingProduct.addProduct(new Product("Tina", "Flared jeans", Category.DRESS, 9, LocalDate.of(2025,8,1), LocalDate.of(2025,8,1)));
        managingProduct.addProduct(new Product("Joell", "Dressed dress", Category.DRESS, 6, LocalDate.of(2025,8,2), LocalDate.of(2025,8,2)));
        managingProduct.addProduct(new Product("Bella", "Evening dress", Category.DRESS, 5, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Anna", "Cocktail dress", Category.DRESS, 7, LocalDate.now(), LocalDate.now()));
        managingProduct.addProduct(new Product("Milo", "Flared jeans", Category.JEANS, 10, firstDay, LocalDate.now()));
        managingProduct.addProduct(new Product("Leon", "Dressed pants", Category.PANTS, 10, secondDay, LocalDate.now()));

        assertEquals(2, managingProduct.getTopProductsThisMonth().size());
        assertEquals("Milo", managingProduct.getTopProductsThisMonth().getFirst().id());
        assertEquals("Leon", managingProduct.getTopProductsThisMonth().getLast().id());

    }
}