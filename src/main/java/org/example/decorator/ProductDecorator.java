package org.example.decorator;

import org.example.entities.Sellable;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class ProductDecorator implements Sellable {
    protected final Sellable decoratedProduct;

    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = Objects.requireNonNull(decoratedProduct, "decoratedProduct cannot be null");
    }

    @Override
    public String getId() {
        return decoratedProduct.getId();
    }

    @Override
    public String getName() {
        return decoratedProduct.getName();
    }

    @Override
    public BigDecimal getPrice() {
        return decoratedProduct.getPrice();
    }
}
