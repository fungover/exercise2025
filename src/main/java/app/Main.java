package app;

import entities.Category;
import entities.Product;
import repository.InMemoryProductRepository;
import repository.ProductRepository;
import service.ProductService;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== REPOSITORY PATTERN & BUILDER PATTERN DEMONSTRATION ===\n");

        // Skapa repository och service med dependency injection
        ProductRepository productRepository = new InMemoryProductRepository();
        ProductService productService = new ProductService(productRepository);

        // Demonstrera olika sätt att skapa produkter
        demonstrateBuilderPattern(productService);
        demonstrateConvenienceMethod(productService);
        demonstrateSpecificTimestamps(productService);
        demonstrateProductUpdate(productService);
        demonstrateServiceFeatures(productService);

        System.out.println("\n=== DEMONSTRATION SLUTFÖRD ===");
    }

    private static void demonstrateBuilderPattern(ProductService productService) {
        System.out.println("1. BUILDER PATTERN DEMONSTRATION");
        System.out.println("================================");

        // Skapa produkter med Builder Pattern (REKOMMENDERAT SÄTT)
        Product laptop = new Product.Builder()
                .id("LAPTOP001")
                .name("Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .asNewProduct()  // Sätter current timestamps
                .build();

        Product bok = new Product.Builder()
                .id("BOK001")
                .name("Java för Nybörjare")
                .category(Category.BOOKS)
                .rating(8)
                .asNewProduct()
                .build();

        Product tshirt = new Product.Builder()
                .id("TSHIRT001")
                .name("Svart T-shirt")
                .category(Category.CLOTHING)
                .rating(7)
                .asNewProduct()
                .build();

        // Lägg till via service (som använder repository)
        productService.addProduct(laptop);
        productService.addProduct(bok);
        productService.addProduct(tshirt);

        System.out.println("✅ Skapade 3 produkter med Builder Pattern");
        System.out.println("Laptop: " + laptop.name() + " (Betyg: " + laptop.rating() + ")");
        System.out.println("Bok: " + bok.name() + " (Betyg: " + bok.rating() + ")");
        System.out.println("T-shirt: " + tshirt.name() + " (Betyg: " + tshirt.rating() + ")");
        System.out.println("Builder Pattern ger tydligare och säkrare kod!");
        System.out.println();
    }

    private static void demonstrateConvenienceMethod(ProductService productService) {
        System.out.println("2. BEKVÄMLIGHETSMETOD (addNewProduct)");
        System.out.println("====================================");

        // Använd bekvämlighetsmetoden (som använder Builder internt)
        productService.addNewProduct("SPORT001", "Löparskor", Category.SPORTS, 8);
        productService.addNewProduct("TOY001", "LEGO Slott", Category.TOYS, 10);
        productService.addNewProduct("HOME001", "Kaffekopp", Category.HOME, 6);

        System.out.println("✅ Lade till 3 produkter med addNewProduct()");
        System.out.println("addNewProduct() använder Builder Pattern internt för att skapa produkter");
        System.out.println();
    }

    private static void demonstrateSpecificTimestamps(ProductService productService) {
        System.out.println("3. PRODUKTER MED SPECIFIKA DATUM");
        System.out.println("===============================");

        // Skapa produkt med specifikt datum med Builder
        LocalDateTime gammalDatum = LocalDateTime.of(2023, 12, 15, 10, 30);

        Product gammalProdukt = new Product.Builder()
                .id("OLD001")
                .name("Vintage Kamera")
                .category(Category.ELECTRONICS)
                .rating(5)
                .createdDate(gammalDatum)
                .modifiedDate(gammalDatum)
                .build();

        productService.addProduct(gammalProdukt);

        System.out.println("✅ Skapade produkt med specifikt datum med Builder:");
        System.out.println("Produkt: " + gammalProdukt.name());
        System.out.println("Skapad: " + gammalProdukt.createdDate());
        System.out.println("Modifierad: " + gammalProdukt.isModified() + " (borde vara false)");
        System.out.println("Builder Pattern låter oss sätta specifika datum enkelt");
        System.out.println();
    }

    private static void demonstrateProductUpdate(ProductService productService) {
        System.out.println("4. UPPDATERA PRODUKTER");
        System.out.println("=====================");

        System.out.println("Före uppdatering:");
        productService.getProductById("LAPTOP001").ifPresent(product -> {
            System.out.println("- Namn: " + product.name());
            System.out.println("- Betyg: " + product.rating());
            System.out.println("- Modifierad: " + product.isModified());
        });

        // Uppdatera produkten via service (som använder Builder internt)
        productService.updateProduct("LAPTOP001", "Gaming Laptop Pro Max", Category.ELECTRONICS, 10);

        System.out.println("\nEfter uppdatering:");
        productService.getProductById("LAPTOP001").ifPresent(product -> {
            System.out.println("- Namn: " + product.name());
            System.out.println("- Betyg: " + product.rating());
            System.out.println("- Modifierad: " + product.isModified());
        });

        System.out.println("✅ updateProduct() använder Builder Pattern internt för säker uppdatering");
        System.out.println();
    }

    private static void demonstrateServiceFeatures(ProductService productService) {
        System.out.println("5. PRODUCTSERVICE FUNKTIONER");
        System.out.println("============================");

        // Visa alla produkter
        System.out.println("Alla produkter (" + productService.getAllProducts().size() + " st):");
        productService.getAllProducts().forEach(product ->
                System.out.println("- " + product.name() + " (" + product.category() + ", Betyg: " + product.rating() + ")")
        );

        System.out.println();

        // Visa produkter per kategori
        System.out.println("Elektronik-produkter sorterade A-Z:");
        List<Product> elektronikProdukter = productService.getProductsByCategorySorted(Category.ELECTRONICS);
        elektronikProdukter.forEach(product ->
                System.out.println("- " + product.name() + " (Betyg: " + product.rating() + ")")
        );

        System.out.println();

        // Visa modifierade produkter
        System.out.println("Modifierade produkter:");
        List<Product> modifieradeProdukter = productService.getModifiedProducts();
        modifieradeProdukter.forEach(product ->
                System.out.println("- " + product.name() + " (ursprungligen skapad: " +
                        product.createdDate().toLocalDate() + ")")
        );

        System.out.println();

        // Visa kategorier som har produkter
        System.out.println("Kategorier med produkter:");
        productService.getCategoriesWithProducts().forEach(category ->
                System.out.println("- " + category + " (" +
                        productService.countProductsInCategory(category) + " produkter)")
        );

        System.out.println();

        // Visa initials map
        System.out.println("Produkter efter första bokstav:");
        productService.getProductInitialsMap().forEach((character, count) ->
                System.out.println("- " + character + ": " + count + " produkter")
        );

        System.out.println();

        // Visa högkvalitativa produkter
        System.out.println("Högkvalitativa produkter (betyg >= 8):");
        productService.getHighQualityProducts(8)
                .forEach(product -> System.out.println("- " + product.name() +
                        " (Betyg: " + product.rating() + ")"));

        System.out.println();

        // Visa topprodukter denna månad
        System.out.println("Topprodukter denna månad:");
        List<Product> topProducts = productService.getTopRatedProductsThisMonth();
        if (topProducts.isEmpty()) {
            System.out.println("- Inga produkter skapade denna månad eller inga har maxbetyg");
        } else {
            topProducts.forEach(product ->
                    System.out.println("- " + product.name() + " (Betyg: " + product.rating() +
                            ", Skapad: " + product.createdDate().toLocalDate() + ")")
            );
        }

        System.out.println();

        // Test av kategorikontroll
        System.out.println("Kategorikontroller:");
        System.out.println("- Har elektronikprodukter: " +
                productService.hasProductsInCategory(Category.ELECTRONICS));
        System.out.println("- Har matprodukter: " +
                productService.hasProductsInCategory(Category.FOOD));

        System.out.println();
        System.out.println("✅ Alla ProductService-funktioner fungerar perfekt!");

        // Demonstrera arkitekturen
        System.out.println("\n6. ARKITEKTUR-FÖRDELAR");
        System.out.println("======================");
        System.out.println("✅ Builder Pattern fördelar:");
        System.out.println("   - Tydligare kod: ser exakt vad varje parameter är");
        System.out.println("   - Flexibilitet: kan sätta parametrar i vilken ordning som helst");
        System.out.println("   - Säkerhet: validering i både Builder och Product");
        System.out.println("   - Läsbarhet: method chaining gör koden självdokumenterande");

        System.out.println();
        System.out.println("✅ Repository Pattern fördelar:");
        System.out.println("   - Separation of Concerns: Business logic vs Data access");
        System.out.println("   - Dependency Injection: Service får repository injicerat");
        System.out.println("   - Testbarhet: Repository kan mockas för enhetstester");
        System.out.println("   - Flexibilitet: Kan byta till databas-repository senare");

        System.out.println();
        System.out.println("✅ Kombinerade patterns ger professionell arkitektur!");
    }

    // Bonus: Demonstrera avancerade Builder-funktioner
    private static void demonstrateAdvancedBuilder() {
        System.out.println("BONUS: AVANCERADE BUILDER-FUNKTIONER");
        System.out.println("====================================");

        // Olika sätt att använda Builder
        Product product1 = new Product.Builder()
                .id("ADV001")
                .name("Advanced Product")
                .category(Category.ELECTRONICS)
                .rating(9)
                .withCurrentTimestamps()  // Explicit sätt nuvarande tid
                .build();

        Product product2 = new Product.Builder()
                .id("ADV002")
                .name("Another Product")
                .category(Category.BOOKS)
                .rating(8)
                .asNewProduct()  // Shortcut för nya produkter
                .build();

        // Med specifika timestamps
        LocalDateTime specificTime = LocalDateTime.of(2024, 6, 15, 14, 30);
        Product product3 = new Product.Builder()
                .id("ADV003")
                .name("Historical Product")
                .category(Category.TOYS)
                .rating(7)
                .createdDate(specificTime)
                .modifiedDate(specificTime.plusHours(2))  // Modifierad 2 timmar senare
                .build();

        System.out.println("✅ Builder Pattern stödjer många olika användningsfall!");
    }
}