package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped // Weld creates this once and reuses it
public class SimpleCheckoutService implements CheckoutService {
    private final PaymentMethod paymentMethod;

    @Inject // Weld provides a PaymentMethod automatically
    public SimpleCheckoutService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String checkout(int amount) {
        return paymentMethod.pay(amount);
    }
}
