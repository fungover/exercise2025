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

    public String makePurchase(int amount) {
        String result = checkoutService.checkout(amount);

        return "Customer completed purchase: " + result;
    }
}
