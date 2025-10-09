package org.example.di;

public class Resturant {
    static void main(String[] args) {

        IngredientSupplier supplier = new LocalIngredientSupplier();
        BurgerMaker burgerChef = new BurgerChef(supplier);
        PizzaMaker pizzaChef = new PizzaChef(supplier);

        HeadChef head = new HeadChef(burgerChef, pizzaChef);

        head.serveBurger("Kevin");
        head.servePizza("Bob");
    }
}
