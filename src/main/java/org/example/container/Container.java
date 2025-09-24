package org.example.container;

import org.example.repositories.FileRepository;
import org.example.repositories.MessageRepository;
import org.example.services.MessageService;
import org.example.services.SMSService;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container {

    // Save which implementations that can be used for interfaces
    private static final Map<Class<?>, Class<?>> interfaceMappings = new HashMap<>();

    static {
        // The different implementations that can be used
        interfaceMappings.put(MessageService.class, SMSService.class);
        interfaceMappings.put(MessageRepository.class, FileRepository.class);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> type) {
        try {
            // If type is an interface, change to implementation from map
            if (type.isInterface()) {
                Class<?> impl = interfaceMappings.get(type);
                if (impl == null) {
                    throw new RuntimeException("No implementation registered for " + type);
                }
                type = (Class<T>) impl;
            }

            Constructor<?>[] publicCtors = type.getConstructors();
            Constructor<?> constructorToUse = null;

            // Prefer a public no-arg ctor if available
            for (Constructor<?> c : publicCtors) {
                if (c.getParameterCount() == 0) {
                    constructorToUse = c;
                    break;
                }
            }

            // Otherwise choose the public ctor with the most params (deterministic)
            if (constructorToUse == null) {
                for (Constructor<?> c : publicCtors) {
                    if (constructorToUse == null || c.getParameterCount() > constructorToUse.getParameterCount()) {
                        constructorToUse = c;
                    }
                }
            }

            // If there are no public ctors, try a declared no-arg ctor
            if (constructorToUse == null) {
                try {
                    Constructor<?> noArg = type.getDeclaredConstructor();
                    noArg.setAccessible(true);
                    return (T) noArg.newInstance();
                } catch (NoSuchMethodException ex) {
                    throw new RuntimeException("No accessible constructors found for " + type.getName(), ex);
                }
            }

            Class<?>[] paramTypes = constructorToUse.getParameterTypes();
            if (paramTypes.length == 0) {
                return (T) constructorToUse.newInstance();
            }

            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }

            return (T) constructorToUse.newInstance(dependencies);

        } catch (Exception e) {
            throw new RuntimeException("Could not create an instance for: " + type, e);
        }
    }
}
