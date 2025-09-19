package exercise4.service;

public class DiscountDecorator extends ProductDecorator {

    private final double discount;

    public DiscountDecorator(Sellable sellable, double discount) {
        super(sellable);
        this.discount = discount;
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
        double procent = 1 - (discount/100);
        return sellable.getPrice()*procent;
    }

    @Override
    public int getRating() {
        return sellable.getRating();
    }
}
