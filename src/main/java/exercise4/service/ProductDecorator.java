package exercise4.service;

public abstract class ProductDecorator implements Sellable {

    protected Sellable sellable;

    public ProductDecorator(Sellable sellable) {
        this.sellable = sellable;
    }



}
