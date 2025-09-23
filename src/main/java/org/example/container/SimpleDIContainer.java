package org.example.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleDIContainer {
    private Map<Class<?>, Class<?>> registry = new HashMap<>();
    private Map<Class<?>, Object> instances = new HashMap<>();

    public <T> void register(Class<T> interfaceType,
                             Class<? extends T> implementationType) {
        registry.put(interfaceType, implementationType);
        System.out.println("Registered " + interfaceType.getSimpleName() + " -> " +
          implementationType.getSimpleName());
    }

    @SuppressWarnings("unchecked") public <T> T getInstance(Class<T> type) {
        System.out.println("\nRequesting instance of: " + type.getSimpleName());

        if (instances.containsKey(type)) {
            System.out.println(
              "Returning cached instance of " + type.getSimpleName());
            return (T) instances.get(type);
        }

        Class<?> implementationType = registry.get(type);
        if (implementationType == null) {
            implementationType = type;
        }
        try {
            Constructor<?> constructor = implementationType.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            System.out.println(
              "Found constructor for " + implementationType.getSimpleName() +
                " with " + parameterTypes.length + " parameters");

            if (parameterTypes.length == 0) {
                T instance = (T) constructor.newInstance();
                instances.put(type, instance);
                return instance;
            }

            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.println(
                  "  Getting dependency: " + parameterTypes[i].getSimpleName());
                dependencies[i] = getInstance(parameterTypes[i]);
            }

            T instance = (T) constructor.newInstance(dependencies);
            instances.put(type, instance);
            return instance;

        } catch (Exception e) {
            throw new RuntimeException(
              "Failed to create instance of " + type.getSimpleName(), e);
        }

    }

    public void showRegistry() {
        System.out.println("\n=== Container Registry ===");
        for (Map.Entry<Class<?>, Class<?>> entry : registry.entrySet()) {
            System.out.println(entry.getKey()
                                    .getSimpleName() + " -> " + entry.getValue()
                                                                     .getSimpleName());
        }
        System.out.println("================\n");
    }

}
