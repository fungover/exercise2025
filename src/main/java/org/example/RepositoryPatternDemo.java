package org.example;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.InMemoryProductRepository;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

public class RepositoryPatternDemo {
    public static void main(String[] args) {
        System.out.println("🏛️ Repository Pattern Demo");
        System.out.println("===========================");

        //-- repository instance --
        System.out.println("\n📦 Step 1: Create InMemoryProductRepository");
        ProductRepository repository = new InMemoryProductRepository();

        //-- Dependency Injection --
        System.out.println("🔧 Step 2: Inject repository into ProductService");
        ProductService productService = new ProductService(repository);

        System.out.println("📱 Step 3: Add products via ProductService");

        Product iPhone = new Product.Builder()
            .id("IPHONE001")
            .name("iPhone 15 Pro")
            .category(Category.ELECTRONICS)
            .rating(9)
            .build();

        Product laptop = new Product.Builder()
            .id("LAPTOP001")
            .name("MacBook Pro")
            .category(Category.ELECTRONICS)
            .rating(10)
            .build();

        productService.addProduct(iPhone);
        productService.addProduct(laptop);

        System.out.println("✅ Added 2 products through ProductService");

        //--separation of concerns--
        System.out.println("\n🎯 Step 4: Repository Pattern Benefits");
        System.out.println("• ProductService Contains business logic");
        System.out.println("• InMemoryProductRepository: Contains data access logic");
        System.out.println("• Dependency Injection: Service depends on repository interface");

        //--business logic in service---
        System.out.println("\n📊 Step 5: Business logic examples:");
        System.out.println("All products: " + productService.getAllProducts().size());
        System.out.println("Electronics count: " + productService.countProductsInCategory(Category.ELECTRONICS));
        System.out.println("Categories with products: " + productService.getCategoriesWithProducts());

        //-- repository can be swapped--
        System.out.println("\n🔄 Step 6: Repository is easily replaceable");
        System.out.println("// Could easily swap InMemoryProductRepository with:");
        System.out.println("// DatabaseProductRepository, FileProductRepository, etc.");
        System.out.println("// ProductService code stays the same!");

        System.out.println("\n🎉 Repository Pattern implementation complete!");
    }
}
