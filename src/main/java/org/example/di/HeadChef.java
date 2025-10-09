package org.example.di;

public class HeadChef {
    private final BurgerMaker burgerMaker;
    private final PizzaMaker pizzaMaker;

    public HeadChef(BurgerMaker burgerMaker, PizzaMaker pizzaMaker) {
        this.burgerMaker = burgerMaker;
        this.pizzaMaker = pizzaMaker;
    }

    public void serveBurger(String customer) {
        burgerMaker.makeBurger(customer);
    }

    public void servePizza(String customer) {
        pizzaMaker.makePizza(customer);
    }
}
