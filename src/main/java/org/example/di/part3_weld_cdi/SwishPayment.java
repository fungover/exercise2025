package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

@ApplicationScoped // Weld will manage this as a bean
@Swish // Qualifier so it can be chosen explicitly
@Default // Marks this as the fallback (default) payment method
public class SwishPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Swish";
    }
}
