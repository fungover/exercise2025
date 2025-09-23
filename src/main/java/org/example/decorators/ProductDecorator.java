package org.example.decorators;

import org.example.entities.Sellable;

public abstract class ProductDecorator implements Sellable {
    protected final Sellable sellable;

    // --- Constructor ---
    public ProductDecorator(Sellable sellable) {
        this.sellable = sellable;

    }
    @Override
    public String getId() {
        return sellable.getId();
    }
    @Override
    public String getName() {
        return sellable.getName();
    }
    @Override
    public double getPrice() {
        return sellable.getPrice();
    }
}
