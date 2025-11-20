package org.example;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container {

    private final Map<Class<?>, Class<?>> registrations = new HashMap<>();

    public <T> void register(Class<T> abstraction, Class<? extends T> implementation) {
        registrations.put(abstraction, implementation);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        try {
            Class<?> implementationClass = type;

            if (type.isInterface() || java.lang.reflect.Modifier.isAbstract(type.getModifiers())) {
                implementationClass = registrations.get(type);
                if (implementationClass == null) {
                    throw new RuntimeException("No registration found for " + type.getName());
                }
            }

            Constructor<?> constructor = implementationClass.getDeclaredConstructors()[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();

            if (paramTypes.length == 0) {

                return (T) implementationClass.getDeclaredConstructor().newInstance();
            }

            Object[] params = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = getInstance(paramTypes[i]);
            }

            return (T) constructor.newInstance(params);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }
}
