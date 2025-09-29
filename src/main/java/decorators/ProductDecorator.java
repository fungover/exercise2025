package decorators;

import entities.Sellable;


 // Abstract ProductDecorator - base for all product decorators
 // This class "wraps" a Sellable object and delegates all calls to it.
 // Subclasses can override specific methods to add functionality.

public abstract class ProductDecorator implements Sellable {

    // Hold a reference to a Sellable object
    protected Sellable decoratedProduct;

    // Constructor accepts a Sellable object to wrap
    public ProductDecorator(Sellable decoratedProduct) {
        if (decoratedProduct == null) {
            throw new IllegalArgumentException("Decorated product cannot be null");
        }
        this.decoratedProduct = decoratedProduct;
    }

    // Delegate calls to the decoratedProduct
    @Override
    public String getName() {
        return decoratedProduct.getName();
    }

    @Override
    public double getPrice() {
        return decoratedProduct.getPrice();
    }

    @Override
    public String getId() {
        return decoratedProduct.getId();
    }

    @Override
    public String getDescription() {
        return decoratedProduct.getDescription();
    }
}