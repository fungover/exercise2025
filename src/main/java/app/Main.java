import entities.Category;
import entities.Product;
import service.Warehouse;

import java.time.LocalDateTime;
import java.util.List;


    public static void main(String[] args) {
        System.out.println("=== BUILDER PATTERN DEMONSTRATION ===\n");

        // Skapa ett warehouse
        Warehouse warehouse = new Warehouse();

        // Demonstrera olika sätt att skapa produkter
        demonstrateBasicBuilder(warehouse);
        demonstrateConvenienceMethod(warehouse);
        demonstrateSpecificTimestamps(warehouse);
        demonstrateProductUpdate(warehouse);
        demonstrateWarehouseFeatures(warehouse);

        System.out.println("\n=== DEMONSTRATION SLUTFÖRD ===");
    }

    private static void demonstrateBasicBuilder(Warehouse warehouse) {
        System.out.println("1. GRUNDLÄGGANDE BUILDER ANVÄNDNING");
        System.out.println("===================================");

        // Skapa produkter med Builder Pattern
        Product laptop = new Product.Builder()
                .id("LAPTOP001")
                .name("Gaming Laptop")
                .category(Category.ELECTRONICS)
                .rating(9)
                .build();

        Product bok = new Product.Builder()
                .id("BOK001")
                .name("Java för Nybörjare")
                .category(Category.BOOKS)
                .rating(8)
                .build();

        Product tshirt = new Product.Builder()
                .id("TSHIRT001")
                .name("Svart T-shirt")
                .category(Category.CLOTHING)
                .rating(7)
                .build();

        // Lägg till i warehouse
        warehouse.addProduct(laptop);
        warehouse.addProduct(bok);
        warehouse.addProduct(tshirt);

        System.out.println("✅ Skapade 3 produkter med Builder Pattern");
        System.out.println("Laptop: " + laptop.name() + " (Betyg: " + laptop.rating() + ")");
        System.out.println("Bok: " + bok.name() + " (Betyg: " + bok.rating() + ")");
        System.out.println("T-shirt: " + tshirt.name() + " (Betyg: " + tshirt.rating() + ")");
        System.out.println();
    }

    private static void demonstrateConvenienceMethod(Warehouse warehouse) {
        System.out.println("2. BEKVÄMLIGHETSMETOD (addNewProduct)");
        System.out.println("====================================");

        // Använd den nya bekvämlighetsmetoden
        warehouse.addNewProduct("SPORT001", "Löparskor", Category.SPORTS, 8);
        warehouse.addNewProduct("TOY001", "LEGO Slott", Category.TOYS, 10);
        warehouse.addNewProduct("HOME001", "Kaffekopp", Category.HOME, 6);

        System.out.println("✅ Lade till 3 produkter med addNewProduct()");
        System.out.println("Detta är enklare när du bara vill skapa nya produkter snabbt");
        System.out.println();
    }

    private static void demonstrateSpecificTimestamps(Warehouse warehouse) {
        System.out.println("3. PRODUKTER MED SPECIFIKA DATUM");
        System.out.println("===============================");

        // Skapa produkt med specifikt datum (t.ex. för test eller historisk data)
        LocalDateTime gammalDatum = LocalDateTime.of(2023, 12, 15, 10, 30);

        Product gammalProdukt = new Product.Builder()
                .id("OLD001")
                .name("Vintage Kamera")
                .category(Category.ELECTRONICS)
                .rating(5)
                .createdDate(gammalDatum)
                .modifiedDate(gammalDatum)
                .build();

        warehouse.addProduct(gammalProdukt);

        System.out.println("✅ Skapade produkt med specifikt datum:");
        System.out.println("Produkt: " + gammalProdukt.name());
        System.out.println("Skapad: " + gammalProdukt.createdDate());
        System.out.println("Modifierad: " + gammalProdukt.isModified() + " (borde vara false)");
        System.out.println();
    }

    private static void demonstrateProductUpdate(Warehouse warehouse) {
        System.out.println("4. UPPDATERA PRODUKTER");
        System.out.println("=====================");

        System.out.println("Före uppdatering:");
        warehouse.getProductById("LAPTOP001").ifPresent(product -> {
            System.out.println("- Namn: " + product.name());
            System.out.println("- Betyg: " + product.rating());
            System.out.println("- Modifierad: " + product.isModified());
        });

        // Uppdatera produkten
        warehouse.updateProduct("LAPTOP001", "Gaming Laptop Pro Max", Category.ELECTRONICS, 10);

        System.out.println("\nEfter uppdatering:");
        warehouse.getProductById("LAPTOP001").ifPresent(product -> {
            System.out.println("- Namn: " + product.name());
            System.out.println("- Betyg: " + product.rating());
            System.out.println("- Modifierad: " + product.isModified());
        });

        System.out.println("✅ Produkten uppdaterades med Builder Pattern internt");
        System.out.println();
    }

    private static void demonstrateWarehouseFeatures(Warehouse warehouse) {
        System.out.println("5. WAREHOUSE FUNKTIONER");
        System.out.println("=======================");

        // Visa alla produkter
        System.out.println("Alla produkter (" + warehouse.getAllProducts().size() + " st):");
        warehouse.getAllProducts().forEach(product ->
                System.out.println("- " + product.name() + " (" + product.category() + ", Betyg: " + product.rating() + ")")
        );

        System.out.println();

        // Visa produkter per kategori
        System.out.println("Elektronik-produkter sorterade A-Z:");
        List<Product> elektronikProdukter = warehouse.getProductsByCategorySorted(Category.ELECTRONICS);
        elektronikProdukter.forEach(product ->
                System.out.println("- " + product.name() + " (Betyg: " + product.rating() + ")")
        );

        System.out.println();

        // Visa modifierade produkter
        System.out.println("Modifierade produkter:");
        List<Product> modifieradeProdukter = warehouse.getModifiedProducts();
        modifieradeProdukter.forEach(product ->
                System.out.println("- " + product.name() + " (ursprungligen skapad: " +
                        product.createdDate().toLocalDate() + ")")
        );

        System.out.println();

        // Visa kategorier som har produkter
        System.out.println("Kategorier med produkter:");
        warehouse.getCategoriesWithProducts().forEach(category ->
                System.out.println("- " + category + " (" +
                        warehouse.countProductsInCategory(category) + " produkter)")
        );

        System.out.println();
        System.out.println("✅ Alla warehouse-funktioner fungerar perfekt med Builder Pattern!");

}