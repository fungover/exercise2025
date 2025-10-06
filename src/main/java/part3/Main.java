package part3;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Main {
    public static void main(String[] args) {

        System.out.println("   PART 3: WELD DI CONTAINER      ");
        System.out.println("======================================\n");

        System.out.println("Startar Weld container...\n");

        Weld weld = new Weld();
        WeldContainer container = weld.initialize();

        // Scenario 1: Kranvatten + Handkvarn
        System.out.println("--- SCENARIO 1: Kranvatten + Handkvarn ---");

        // Hämta specifika implementations direkt via klassen
        WaterSource tap = container.select(TapWater.class).get();
        BeanGrinder manual = container.select(ManualGrinder.class).get();

        CoffeeMaker maker1 = new CoffeeMaker(tap, manual);
        maker1.makeCoffee("arabica beans");


        // Scenario 2: Flaskvatten + Elektrisk kvarn
        System.out.println("--- SCENARIO 2: Flaskvatten + Elektrisk kvarn ---");

        WaterSource bottled = container.select(BottledWater.class).get();
        BeanGrinder electric = container.select(ElectricGrinder.class).get();

        CoffeeMaker maker2 = new CoffeeMaker(bottled, electric);
        maker2.makeCoffee("robusta beans");


        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("            JÄMFÖRELSE");
        System.out.println("═══════════════════════════════════════════");
        System.out.println();
        System.out.println("PART 1 (Manuell DI):");
        System.out.println("  WaterSource tap = new TapWater();");
        System.out.println("  CoffeeMaker maker = new CoffeeMaker(tap, manual);");
        System.out.println();
        System.out.println("PART 2 (Egen Container):");
        System.out.println("  container.register(WaterSource.class, TapWater.class);");
        System.out.println("  CoffeeMaker maker = container.getInstance(CoffeeMaker.class);");
        System.out.println();
        System.out.println("PART 3 (Weld):");
        System.out.println("  WaterSource tap = container.select(TapWater.class).get();");
        System.out.println("  CoffeeMaker maker = new CoffeeMaker(tap, manual);");
        System.out.println();
        System.out.println("✓ Alla tre delar skapar samma scenarios!");
        System.out.println("✓ Part 1: Full manuell kontroll");
        System.out.println("✓ Part 2: Container löser dependencies");
        System.out.println("✓ Part 3: Weld hanterar bean lifecycle");

        weld.shutdown();
        System.out.println("\nWeld container stängd.");
    }
}