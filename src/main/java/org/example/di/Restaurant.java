package org.example.di;

import org.example.di.container.RobotChefContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Restaurant {
    static void main() {

        // Without DI container
        IngredientSupplier supplier = new LocalIngredientSupplier();
        BurgerMaker burgerChef = new BurgerChef(supplier);
        PizzaMaker pizzaChef = new PizzaChef(supplier);
        HeadChef head = new HeadChef(burgerChef, pizzaChef);
        head.serveBurger("Kevin");
        head.servePizza("Bob");

        // With DI container
        RobotChefContainer robotChef = new RobotChefContainer();
        robotChef.register(IngredientSupplier.class, LocalIngredientSupplier.class);
        robotChef.register(BurgerMaker.class, BurgerChef.class);
        robotChef.register(PizzaMaker.class, PizzaChef.class);
        HeadChef roboChef = robotChef.getInstance(HeadChef.class);

        roboChef.serveBurger("Kevin");
        roboChef.servePizza("Bob");

        // With Weld
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        try{
            HeadChef chef = container.select(HeadChef.class).get();
            chef.serveBurger("Kevin");
            chef.servePizza("Bob");
        } finally {
            weld.shutdown();
        }
    }
}
