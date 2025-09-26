package com.example.di.di;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * DI- container
 * This is my own little object-building robot!
 * It handles recursion (when a method calls on itself to solve dependencies on multiple levels)
 * What it does step by step:
 * 1. checks if there is an interface to follow. The right implementation is then being found.
 * 2. checks the constructor to see what dependencies are required.
 * 3. creates the dependencies recursively
 * 4. creates the object and returns it
 **/
public class MiniContainer {
    private final Map<Class<?>, Class<?>> registrations = new HashMap<>();

    public <T> void register(Class<T> abstraction, Class<? extends T> implementation) {
        registrations.put(abstraction, implementation);
    }

    public <T> T get(Class<T> type) {
        try {
            Class<?> impl = type;
            if (type.isInterface()) {
                impl = registrations.get(type);
                if (impl == null) throw new RuntimeException("No registration for " + type.getName());
            }
            Constructor<?>[] ctors = impl.getConstructors();
            if (ctors.length != 1) {
                throw new RuntimeException("Expected exactly one public constructor for " + impl.getName() +
                        ", but found " + ctors.length);
            }
            Constructor<?> ctor = ctors[0];
            Object[] args = Arrays.stream(ctor.getParameterTypes())
                    .map(this::get)
                    .toArray();

            return (T) ctor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create: " + type.getName(), e);
        }
    }
}
