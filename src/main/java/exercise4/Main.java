package exercise4;

import exercise4.entities.Category;
import exercise4.entities.Product;
import exercise4.repository.InMemoryProductRepository;
import exercise4.service.DiscountDecorator;
import exercise4.service.ProductService;
import exercise4.service.Sellable;

import java.time.LocalDate;
import java.util.List;

public class Main {

    static void main(String[] args) {
        Product.ProductBuilder builder = new Product.ProductBuilder();
        InMemoryProductRepository repository = new InMemoryProductRepository();
        ProductService productService = new ProductService(repository);

        repository.addProduct(builder.setId("Bella").setName("Knitted shirt").setCategory(Category.SHIRT)
                .setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        repository.addProduct(builder.setId("Tina").setName("Dressed pants").setCategory(Category.PANTS)
                .setRating(7).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());
        repository.addProduct(builder.setId("Joel").setName("Flared Jeans").setCategory(Category.JEANS)
                .setRating(5).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        List<Product> products = repository.getAllProducts();

        products.forEach(System.out::println);

        System.out.println(productService.countProductsInCategory(Category.JEANS));

        repository.addProduct(builder.setId("Fiona").setName("Cocktail dress").setPrice(100)
                .setRating(9).setCreatedDate(LocalDate.now()).setModifiedDate(LocalDate.now()).createProduct());

        Product dress = repository.getProductById("Fiona");
        System.out.printf("Original price: %.2f SEK %n", dress.getPrice());

        Sellable discount = new DiscountDecorator(dress, 20);

        System.out.println("The "+discount.getName() +" is discounted");
        System.out.printf("Discount price: %.2f SEK %n", discount.getPrice());
    }

}
