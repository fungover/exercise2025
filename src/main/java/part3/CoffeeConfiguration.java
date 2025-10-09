package part3;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class CoffeeConfiguration {

    @Produces
    @Named("standard")
    public CoffeeMaker createStandardCoffeeMaker(
            TapWater water,
            ManualGrinder grinder) {
        return new CoffeeMaker(water, grinder);
    }

    @Produces
    @Named("deluxe")
    public CoffeeMaker createDeluxeCoffeeMaker(
            BottledWater water,
            ElectricGrinder grinder) {
        return new CoffeeMaker(water, grinder);
    }
}