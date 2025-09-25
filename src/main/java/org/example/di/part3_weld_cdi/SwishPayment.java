package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;

@ApplicationScoped // Weld will manage this as a bean
@Default // This is the default implementation
public class SwishPayment implements PaymentMethod {
    @Override
    public String pay(int amount) {
        return "Paid " + amount + " SEK with Swish";
    }
}
