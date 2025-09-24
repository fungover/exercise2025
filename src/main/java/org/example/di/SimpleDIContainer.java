package org.example.di;

import org.example.interfaces.DIContainer;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleDIContainer implements DIContainer {
    private final Map<Class<?>, Class<?>> registrations = new HashMap<>();
    private final Map<Class<?>, Object> instances = new HashMap<>();

    @Override
    public <T> void register(Class<T> interfaceType, Class<? extends T> implementationType) {
        registrations.put(interfaceType, implementationType);

    }

    @Override
    public <T> T resolve(Class<T> type) {
        if (instances.containsKey(type)) {
            return (T) instances.get(type);
        }

        Class<?> implClass = registrations.get(type);
        if (implClass == null) {
            throw new RuntimeException("No implementation found for " + type.getName());
        }

        try {
            Constructor<?>[] constructors = implClass.getConstructors();
            if (constructors.length == 0) {
                throw new RuntimeException("No constructors found for: " + implClass.getName());
            }

            Constructor<?> constructor = constructors[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] parameter = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                parameter[i] = resolve(parameterTypes[i]);
            }

            T instance = (T) constructor.newInstance(parameter);

            instances.put(type, instance);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance", e);
        }


    }
}
