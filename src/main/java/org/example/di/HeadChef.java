package org.example.di;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class HeadChef {
    private final BurgerMaker burgerMaker;
    private final PizzaMaker pizzaMaker;

    @Inject
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
