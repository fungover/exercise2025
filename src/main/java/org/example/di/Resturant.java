package org.example.di;

import org.example.di.container.RobotChefContainer;

public class Resturant {
    public static void main(String[] args) {

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
    }
}
