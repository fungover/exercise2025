package org.example.di.part2_minimal_di_container;

import org.example.di.part1_manual_constructor_injection.*;

import java.lang.reflect.Constructor;

public class DIContainer {
    public <T> T getInstance(Class<T> clazz) {
        try {
            // If asked for an interface, we return a default choice.
            if (clazz == PaymentMethod.class) {
                return (T) new SwishPayment(); // default PaymentMethod
            }

            if (clazz == CheckoutService.class) {
                return (T) new SimpleCheckoutService(new SwishPayment()); // default CheckoutService
            }

            // Find the constructor of the class (we assume only one exists).
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];

            // Check what parameters (dependencies) the constructor needs.
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            // Recursively build each dependency:
            // If a dependency also has its own dependencies,
            // we call getInstance() again, until we reach a class
            // that we know how to create directly (like SwishPayment).
            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getInstance(parameterTypes[i]);
            }

            // Finally, create the object with its dependencies.
            // Example: new Customer(new SimpleCheckoutService(new SwishPayment()))
            return (T) constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}
