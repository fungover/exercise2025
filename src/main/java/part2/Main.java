package part2;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("   PART 2: DI CONTAINER      ");
        System.out.println("====================================\n");

        // Scenario 1: Standard CoffeeMaker
        System.out.println("--- SCENARIO 1: Standard (Kranvatten + Handkvarn) ---");
        DIContainer container1 = new DIContainer();
        container1.register(WaterSource.class, TapWater.class);
        container1.register(BeanGrinder.class, ManualGrinder.class);

        CoffeeMaker maker1 = container1.getInstance(CoffeeMaker.class);
        maker1.makeCoffee("arabica beans");

        // Scenario 2: Deluxe CoffeeMaker
        System.out.println("\n--- SCENARIO 2: Deluxe Flaskvatten + Elektrisk kvarn ---");
        DIContainer container2 = new DIContainer();
        container2.register(WaterSource.class, BottledWater.class);
        container2.register(BeanGrinder.class, ElectricGrinder.class);

        CoffeeMaker maker2 = container2.getInstance(CoffeeMaker.class);
        maker2.makeCoffee("robusta beans");
    }
}