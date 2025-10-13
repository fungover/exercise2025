package org.example.di;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class BurgerChef implements BurgerMaker {
    private final IngredientSupplier supplier;

    @Inject
    public BurgerChef(IngredientSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void makeBurger(String customer) {
        System.out.println("BurgerChef: Gör en burgare till "+ customer);
        System.out.println(" Hämtar: " + supplier.get("hamburgarbröd"));
        System.out.println(" Hämtar: " + supplier.get("hamburgare"));
        System.out.println(" Hämtar: " + supplier.get("ost"));
        System.out.println("Burgare färdig!");
    }
}
