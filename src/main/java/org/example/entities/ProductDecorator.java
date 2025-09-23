package org.example.entities;

import java.util.Objects;

public abstract class ProductDecorator implements Sellable {
    protected final Sellable decoratedProduct;

    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = Objects.requireNonNull(decoratedProduct, "decoratedProduct");
    }

    @Override
    public String getName() {
        return this.decoratedProduct.getName();
    }

    @Override
    public String getId() {
        return this.decoratedProduct.getId();
    }

    @Override
    public double getPrice() {
        return this.decoratedProduct.getPrice();
    }

}
