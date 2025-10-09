package org.example.di;

public class PizzaChef implements PizzaMaker {
    private final IngredientSupplier supplier;

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
