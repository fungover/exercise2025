package org.example.di.part3_weld_cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped // Weld creates this once and reuses it
public class Customer {

    private CheckoutService checkoutService;

    @Inject // Weld provides a CheckoutService automatically
    public Customer(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // Default payment method (Swish)
    public String makePurchase(int amount) {
        return "Customer completed purchase: " + checkoutService.checkout(amount);
    }

    // Choose payment method by name
    public String makePurchase(int amount, String methodName) {
        return "Customer completed purchase: " + checkoutService.checkout(amount, methodName);
    }
}
