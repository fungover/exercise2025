package org.example.di;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LocalIngredientSupplier implements IngredientSupplier {

    @Override
    public String get(String ingredient) {
        return "ingrediens " +  ingredient;
    }
}
