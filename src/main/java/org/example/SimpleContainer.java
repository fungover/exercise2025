package org.example;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        bindings.put(abstraction, implementation);
    }

    public <T> T getInstance(Class<T> clazz) {
        try {
            Class<?> implementationClass = bindings.getOrDefault(clazz, clazz);

            Constructor<?> constructor = implementationClass.getDeclaredConstructors()[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }

            return (T) constructor.newInstance(dependencies);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}

