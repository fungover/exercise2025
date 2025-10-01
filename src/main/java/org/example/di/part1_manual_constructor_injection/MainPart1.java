package org.example.di.part1_manual_constructor_injection;

public class MainPart1 {
    public static void main(String[] args) {
        PaymentMethod swish = new SwishPayment();
        CheckoutService checkout = new SimpleCheckoutService(swish);
        Customer customer = new Customer(checkout);

        String result = customer.makePurchase(200);
        System.out.println(result);
    }
}