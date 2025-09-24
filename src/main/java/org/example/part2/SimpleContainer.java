package org.example.part2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SimpleContainer {

    public <T> T getInstance(Class<T> clazz) {
        try {
            System.out.println("Creating instance of: " + clazz.getName());

            // Get all constructors
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();

            if (constructors.length == 0) {
                throw new RuntimeException("No constructors found for " + clazz.getName());
            }

            // Take the first constructor assuming only one constructor per class
            Constructor<?> constructor = constructors[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            System.out.println("Constructor has " + parameterTypes.length + " parameters");

            if (parameterTypes.length == 0) {
                // Default constructor
                return (T) constructor.newInstance();
            } else {
                // Constructor with parameters - recursion
                Object[] dependencies = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    System.out.println(" Creating dependency " + (i + 1) + " : " +
                            parameterTypes[i].getSimpleName());

                    dependencies[i] = getInstance(parameterTypes[i]); // Recursive call
                }

                return (T) constructor.newInstance(dependencies);
            }

        } catch (Exception e) {
            System.out.println("Failed to create " + clazz.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
