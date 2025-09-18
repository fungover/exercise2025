package org.example.decorator;

import java.util.Objects;

/**
 * ProductDecorator - Abstract base class for all product decorators.
 * - This is the "Base Decorator" which all concrete decorators will extend.
 * <p>
 * WHY ABSTRACT CLASS?
 * - Have the same logic for all decorators.
 * - Implements the Sellable interface with default methods.
 * - Concrete decorators only needs to override methods they want to change.
 * <p>
 * DELEGATION PATTERN:
 * - Receives a Sellable object to "wrap".
 * - Delegates all method calls to the wrapped object.
 * - Concrete decorators can override specific methods to change behavior.
 * <p>
 * COMPOSITION OVER INHERITANCE:
 * - Instead of inheriting from Product, we "wrap" it instead.
 * - Makes it possible to wrap any Sellable object, not just Product.
 * - More flexible then inheritence - can combine multiple decorators.
 */

public abstract class ProductDecorator implements Sellable {

    /**
     * WHY Sellable and not Product?
     * - Can wrap both Product AND other decorators (which also implement Sellable).
     * - Makes decorators composable - can stack multiple decorators.
     */

    protected final Sellable decoratedProduct; // The wrapped Sellable object.

    public ProductDecorator(Sellable decoratedProduct) { // Constructor takes the Sellable object to wrap with dependency injection.
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
    public double getPrice() {
        return decoratedProduct.getPrice();
    }
}
