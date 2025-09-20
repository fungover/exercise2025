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
        Class<?> implClass = registrations.get(type);
        Constructor<?>[] constructors = implClass.getConstructors();
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameter = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            parameter[i] = resolve(parameterTypes[i]);
        }

        try {
            T instance = (T) constructor.newInstance(parameter);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance", e);
        }

    }
}
