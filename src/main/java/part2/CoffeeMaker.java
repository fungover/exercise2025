package part2;

public class CoffeeMaker {

    private final WaterSource waterSource;
    private final BeanGrinder beanGrinder;

    //constructor injection
    public CoffeeMaker(WaterSource waterSource, BeanGrinder beanGrinder) {
        this.waterSource = waterSource;
        this.beanGrinder = beanGrinder;
    }

    public void makeCoffee(String beans) {
        System.out.println("\n––– Fixar Kaffe med CONTAINER––");
        String water = waterSource.getWater();
        System.out.println("1. Hämtar " + water);

        String ground = beanGrinder.grind(beans);
        System.out.println("2. Jag maler " + ground);

        System.out.println("3. Brygger kaffet...");
        System.out.println("☕ Kaffet är klart!");
        System.out.println("Goooott!!!...");
        System.out.println("––– Klart –––\n");
    }
}
