package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.util.Map;

@ApplicationScoped // Weld creates this once and reuses it
public class SimpleCheckoutService implements CheckoutService {
    private final PaymentMethod defaultPaymentMethod; // Swish marked as @Default
    private final Map<String, PaymentMethod> paymentMethods;

    @Inject // Weld provides a PaymentMethod automatically
    public SimpleCheckoutService(
            @Default PaymentMethod defaultPaymentMethod,
            @Swish PaymentMethod swish,
            @CreditCard PaymentMethod creditCard,
            @Trustly PaymentMethod trustly) {
        this.defaultPaymentMethod = defaultPaymentMethod;
        this.paymentMethods = Map.of(
                "swish", swish,
                "creditCard", creditCard,
                "trustly", trustly
        );
    }

    // Use default method (Swish)
    @Override
    public String checkout(int amount) {
        return defaultPaymentMethod.pay(amount);
    }

    // Choose payment method
    @Override
    public String checkout(int amount, String method) {
        PaymentMethod chosenPaymentMethod = paymentMethods.get(method);
        if (chosenPaymentMethod == null) {
            throw new IllegalArgumentException("Invalid payment method: " + method);
        }
        return chosenPaymentMethod.pay(amount);
    }
}
