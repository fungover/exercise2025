package exercise4.service;

public class DiscountDecorator extends ProductDecorator {

    private final double discountPercent;

    public DiscountDecorator(Sellable sellable, double discountPercent) {
        super(sellable);

        if(discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        this.discountPercent = discountPercent;
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
        double precent = 1 - (discountPercent /100);
        return sellable.getPrice()*precent;
    }

    @Override
    public int getRating() {
        return sellable.getRating();
    }
}
