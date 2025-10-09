package org.example.di;

public class LocalIngredientSupplier implements IngredientSupplier {

    @Override
    public String get(String ingredient) {
        return "ingrediens " +  ingredient;
    }
}
