package org.example.di.part3_weld_cdi;

public interface CheckoutService {
    // Default checkout method (Swish)
    String checkout(int amount);

    // Choose explicit method (Swish, creditcard, trustly)
    String checkout(int amount, String methodName);
}
