package org.example.di.part2_minimal_di_container;

import org.example.di.part1_manual_constructor_injection.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;

public class DIContainer {
    public <T> T getInstance(Class<T> clazz) {
        try {
            // Special case: if someone asks for PaymentMethod (an interface),
            // we cannot build it directly. We just choose SwishPayment as default.
            if (clazz == PaymentMethod.class) {
                return (T) new SwishPayment();
            }

            // Special case: if someone asks for CheckoutService (also an interface),
            // we choose SimpleCheckoutService as the default.
            if (clazz == CheckoutService.class) {
                return (T) new SimpleCheckoutService(new SwishPayment());
            }

            // Guard against invalid input:
            // If clazz is an interface or abstract class (and not handled above),
            // we cannot create it automatically.
            if (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers())) {
                throw new IllegalArgumentException("No default binding for " + clazz.getName());
            }

            // Choose constructor more safely:
            // Instead of always taking the first constructor (order not guaranteed),
            // we pick the one with the *most parameters* (the "greediest").
            Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                    .max(Comparator.comparingInt(Constructor::getParameterCount))
                    .orElseThrow(() -> new IllegalArgumentException("No constructors found on " + clazz.getName()));
            constructor.setAccessible(true);

            // Figure out what parameters this constructor needs
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            // Recursively build all dependencies first
            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getInstance(parameterTypes[i]);
            }

            // Create the object with its dependencies
            return (T) constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}