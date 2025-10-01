package org.example.di.part1_manual_constructor_injection;

// A checkout service that uses constructor injection
public class SimpleCheckoutService implements CheckoutService {
    private final PaymentMethod paymentMethod;

    // Constructor injection happens here
    public SimpleCheckoutService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String checkout(int amount) {
        return paymentMethod.pay(amount);
    }
}
