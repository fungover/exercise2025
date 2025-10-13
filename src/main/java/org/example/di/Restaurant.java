package org.example.di;

import org.example.di.container.RobotChefContainer;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Restaurant {
    static void main() {

        // Part 1: Manual Constructor Injection
        System.out.println("\n Part 1: Manual Dependency Injection");
        IngredientSupplier supplier = new LocalIngredientSupplier();
        BurgerMaker burgerChef = new BurgerChef(supplier);
        PizzaMaker pizzaChef = new PizzaChef(supplier);
        HeadChef head = new HeadChef(burgerChef, pizzaChef);
        head.serveBurger("Kevin");
        head.servePizza("Bob");

        // Part 2: Minimal Dependency Injection container
        System.out.println("\n Part 2: Reflection-based Dependency Injection");
        RobotChefContainer robotChef = new RobotChefContainer();
        robotChef.register(IngredientSupplier.class, LocalIngredientSupplier.class);
        robotChef.register(BurgerMaker.class, BurgerChef.class);
        robotChef.register(PizzaMaker.class, PizzaChef.class);
        HeadChef roboChef = robotChef.getInstance(HeadChef.class);

        roboChef.serveBurger("Kevin");
        roboChef.servePizza("Bob");

        // Part 3: Using Weld CDI container
        System.out.println("\n Part 3: Weld Dependency Injection");
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
