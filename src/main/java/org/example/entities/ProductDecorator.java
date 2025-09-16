package org.example.entities;

/**
 * Abstract decorator for {@link Sellable} products.
 */

public abstract class ProductDecorator implements Sellable{
    protected final Sellable decoratedProduct;

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
    public double getPrice() {
        return decoratedProduct.getPrice();
    }
}
