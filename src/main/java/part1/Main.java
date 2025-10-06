package part1;

public class Main {
    public static void main(String[] args) {

        System.out.println("      PART 1: MANUELL INJECTION      ");
        System.out.println("====================================\n");

        // Scenario 1: Kranvatten + Handkvarn
        WaterSource tap = new TapWater();
        BeanGrinder manual = new ManualGrinder();
        CoffeeMaker maker1 = new CoffeeMaker(tap, manual);
        maker1.makeCoffee("arabica beans");

        // Scenario 2: Flaskvatten + Elektrisk kvarn
        System.out.println("--- SCENARIO 2: Flaskvatten + Elektrisk kvarn ---");
        WaterSource bottled = new BottledWater();
        BeanGrinder electric = new ElectricGrinder();
        CoffeeMaker maker2 = new CoffeeMaker(bottled, electric);
        maker2.makeCoffee("robusta beans");
    }
}
