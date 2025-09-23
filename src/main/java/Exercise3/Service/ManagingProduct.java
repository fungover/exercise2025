package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ManagingProduct implements Warehouse {

    private final List<Product> listOfProducts = new ArrayList<>();

    @Override
    public void addProduct(Product product) {

        Objects.requireNonNull(product, "Product cannot be null");

        boolean exists = listOfProducts.stream()
                        .anyMatch(item -> item.id().equals(product.id()));

        if(exists) {
            throw new IllegalArgumentException("Product already exists");
        }

        listOfProducts.add(product);
    }

    @Override
    public void updateProduct(String id, String name, Category category, int rating) {

        Product itemToUpdate = getProductById(id);
        listOfProducts.add(itemToUpdate.updateProduct(id, name, category, rating));
    }

    public List<Product> getAllProducts() {

        checkIsListIsEmpty();

        return Collections.unmodifiableList(listOfProducts);
    }

    @Override
    public Product getProductById(String id) {

        Objects.requireNonNull(id, "Product id cannot be null");

        Product productById = listOfProducts.stream()
                .filter(item -> item.id().equals(id.trim()))
                .max(Comparator.comparing(Product::modifiedDate))
                .orElse(null);

        if(productById == null){
            throw new IllegalArgumentException("Product doesn't exist");
        }

        return productById;
    }

    @Override
    public List<Product> getProductsByCategorySorted(Category category) {

        checkIsListIsEmpty();
        Objects.requireNonNull(category, "Category cannot be null");

        return listOfProducts.stream()
                .filter(item -> item.category().equals(category))
                .sorted(Comparator.comparing(Product::name)).toList();
    }

    @Override
    public List<Product> getProductsCreatedAfter(LocalDate after) {

        checkIsListIsEmpty();
        Objects.requireNonNull(after, "Date cannot be null");

        return listOfProducts.stream()
                .filter(item -> item.createdDate().isAfter(after)).toList();

    }

    @Override
    public List<Product> getModifiedProducts() {

        checkIsListIsEmpty();

               return listOfProducts.stream()
                .filter(item -> !item.createdDate().equals(item.modifiedDate())).toList();

    }

    @Override
    public List<Category> getCategoriesWithProducts() {

        checkIsListIsEmpty();

        var countedProducts = listOfProducts.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));

        return countedProducts.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getKey).toList();

    }

    @Override
    public Long countProductsInCategory(Category category) {

        checkIsListIsEmpty();
        Objects.requireNonNull(category, "Category cannot be null");

        return listOfProducts.stream()
                .filter(item -> item.category().equals(category)).count();
    }

    @Override
    public Map<Character, Integer> getProductsInitialsMap() {

        checkIsListIsEmpty();

        return listOfProducts.stream()
                .collect(Collectors.groupingBy(item -> item.category().name().charAt(0),Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    @Override
    public List<Product> getTopProductsThisMonth() {
        LocalDate today = LocalDate.now();
        List<Product> listFilteredByMonth = listOfProducts.stream()
                .filter(item -> item.createdDate().getYear() == today.getYear() &&
                        item.createdDate().getMonth().equals(today.getMonth())).toList();

        return listFilteredByMonth.stream()
                .filter(item -> item.rating() == 10)
                .sorted(Comparator.comparing(Product::createdDate)).toList();
    }

    private void checkIsListIsEmpty() {
        if (listOfProducts.isEmpty()) {
            throw new IllegalArgumentException("Product list is empty");
        }
    }


}
