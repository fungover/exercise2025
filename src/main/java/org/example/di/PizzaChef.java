package org.example.di;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class PizzaChef implements PizzaMaker {
    private final IngredientSupplier supplier;

    @Inject
    public PizzaChef(IngredientSupplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public void makePizza(String customer) {
        System.out.println("PizzaChef: Gör en pizza till " + customer);
        System.out.println(" Hämtar: "+ supplier.get("deg"));
        System.out.println(" Hämtar: "+ supplier.get("tomatsås"));
        System.out.println(" Hämtar: " + supplier.get("ost"));
        System.out.println("Pizza färdig!");
    }
}
