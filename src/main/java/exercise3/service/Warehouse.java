package exercise3.service;

import exercise3.enteties.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Warehouse {
    private final List<Product> products;

    public Warehouse(){
        products = new ArrayList<>();
    }
    public void addProduct(Product product) {
        if(product.getName() != null){
            products.add(product);
        }else{
            System.out.println("Product could not be added");
        }
    }
    public void updateProduct(String id, String name, Category category, int rating) {
        Optional<Product> productToUpdate = products.stream().filter(product -> product.getId().equals(id)).findFirst();
        if(productToUpdate.isPresent()){
            productToUpdate.ifPresent(product -> product.setName(name));
            productToUpdate.ifPresent(product -> product.setCategory(category));
            productToUpdate.ifPresent(product -> product.setRating(rating));
            productToUpdate.ifPresent(product -> product.setModifiedDate(LocalDate.now()));
        }else{
            System.out.println("Product not found");
        }
    }
    public List<Product> getAllProducts() {
        if(products.isEmpty()){
            return null;
        }else {
            return List.copyOf(products);
        }
    }
    public Product getProductById(String id){
        Optional<Product> productToUpdate = products.stream().filter(product -> product.getId().equals(id)).findFirst();
        return productToUpdate.orElse(null);
    }

    public List<Product> getProductsByCategorySorted(Category category){
        List <Product> productsInCategory = products.stream().filter(product -> product.getCategory().equals(category)).sorted((product1, product2) -> product1.getName().compareToIgnoreCase(product2.getName())).toList();
        if(productsInCategory.isEmpty()){
            return null;
        }else{
            return List.copyOf(productsInCategory);
        }
    }

    public List<Product> getProductsCreatedAfter(LocalDate date){
        List<Product> productsCreatedAfter = products.stream().filter(param -> param.getCreatedDate().isAfter(date)).toList();
        if(productsCreatedAfter.isEmpty()){
            return null;
        }else{
            return List.copyOf(productsCreatedAfter);
        }
    }

    public List<Product> getModifiedProducts(){
        List<Product> modifiedProducts = products.stream().filter(param -> !param.getModifiedDate().equals(param.getCreatedDate())).toList();
        if(modifiedProducts.isEmpty()){
            return null;
        }else{
            return List.copyOf(modifiedProducts);
        }
    }
}
