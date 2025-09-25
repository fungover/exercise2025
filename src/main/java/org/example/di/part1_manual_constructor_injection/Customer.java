package org.example.di.part1_manual_constructor_injection;

public class Customer {
    // The customer depends on a checkout service
    private CheckoutService checkoutService;

    // We give the customer a checkout service from the outside
    public Customer(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // Method to make a purchase
    public String makePurchase(int amount) {
        // Delegate the actual payment process to the checkout service
        String result = checkoutService.checkout(amount);

        // Add customer specific message
        return "Customer completed purchase: " + result;
    }
}
