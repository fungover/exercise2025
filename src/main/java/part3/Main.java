package part3;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {
    public static void main(String[] args) {

        System.out.println("   PART 3: WELD DI CONTAINER      ");
        System.out.println("======================================\n");

        try (WeldContainer container = new Weld().initialize()) {

            CoffeeConfiguration config = container.select(CoffeeConfiguration.class).get();

            // Scenario 1: Standard CoffeeMaker
            System.out.println("--- SCENARIO 1: Standard (Kranvatten + Handkvarn) ---");

            // Get beans from Weld
            TapWater tap = container.select(TapWater.class).get();
            ManualGrinder manual = container.select(ManualGrinder.class).get();

            // Factory method creates CoffeeMaker
            CoffeeMaker maker1 = config.createStandardCoffeeMaker(tap, manual);
            maker1.makeCoffee("arabica beans");


            // Scenario 2: Deluxe CoffeeMaker
            System.out.println("\n--- SCENARIO 2: Deluxe Flaskvatten + Elektrisk kvarn ---");

            // Get beans from Weld
            BottledWater bottled = container.select(BottledWater.class).get();
            ElectricGrinder electric = container.select(ElectricGrinder.class).get();

            // Factory method creates CoffeeMaker
            CoffeeMaker maker2 = config.createDeluxeCoffeeMaker(bottled, electric);
            maker2.makeCoffee("robusta beans");
        }
    }
}