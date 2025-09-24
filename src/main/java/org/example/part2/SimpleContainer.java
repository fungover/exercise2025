package org.example.part2;

import java.lang.reflect.InvocationTargetException;

public class SimpleContainer {

    public <T> T getInstance(Class<T> clazz) {
        try {
            System.out.println("Creating instance of: " + clazz.getName());

            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Failed to create " + clazz.getName() + ": " + e.getMessage());
            return null;
        }

    }
}
