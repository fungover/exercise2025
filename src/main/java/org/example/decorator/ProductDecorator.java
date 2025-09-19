package org.example.decorator;

import org.example.entities.Sellable;

import java.util.Objects;

public abstract class ProductDecorator implements Sellable {
    protected final Sellable decoratedProduct;

    protected ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = Objects.requireNonNull(decoratedProduct, "decoratedProduct");
    }

    @Override public String getId() { return decoratedProduct.getId(); }
    @Override public String getName() { return decoratedProduct.getName(); }
    @Override public double getPrice() { return decoratedProduct.getPrice(); }
}
