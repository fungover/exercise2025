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

            Constructor<?> constructor = type.getConstructors()[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();

            if (paramTypes.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            }

            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }

            return (T) constructor.newInstance(dependencies);

        } catch (Exception e) {
            throw new RuntimeException("Could not create an instance for: " + type, e);
        }
    }
}
