package org.example.decorator;

import org.example.entities.Sellable;

import java.math.BigDecimal;

public abstract class ProductDecorator implements Sellable {
    protected Sellable decoratedProduct;

    public ProductDecorator(Sellable decoratedProduct) {
        this.decoratedProduct = decoratedProduct;
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
