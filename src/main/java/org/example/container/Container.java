package org.example.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Class<?>, Object> instances = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clazz) {
        // Input validation
        if (clazz == null) {
            throw new IllegalArgumentException("Class cannot be null");
        }

        // Check if there already is an instance
        if (instances.containsKey(clazz)) {
            return (T) instances.get(clazz);
        }

        try {
            // Get the only constructor
            Constructor<?> constructor = getConstructor(clazz);
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            // If no parameters, create the instance
            if (parameterTypes.length == 0) {
                try{
                T instance = (T) constructor.newInstance();
                instances.put(clazz, instance);
                return instance;
            } catch (InstantiationException e) {
                throw new RuntimeException("Cannot instantiate abstract class or interface: " + clazz.getSimpleName(), e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Constructor not accessible for class: " + clazz.getSimpleName(), e);
            }
        }

            // Automatically create all required dependencies for this class
            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class<?> paramType = parameterTypes[i];

                // If it's an interface, find an implementation
                if (paramType.isInterface()) {
                    Class<?> implementation = findImplementation(paramType);
                    dependencies[i] = getInstance(implementation);
                } else {
                    dependencies[i] = getInstance(paramType);
                }
            }

            // Create an instance with resolved dependencies
            T instance = (T) constructor.newInstance(dependencies);
            instances.put(clazz, instance);
            return instance;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getSimpleName(), e);
        }
    }

    private static <T> Constructor<?> getConstructor(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new RuntimeException("Class " + clazz.getSimpleName() + " has no constructors");
        }
        if (constructors.length != 1) {
            throw new RuntimeException("Class " + clazz.getSimpleName() +
                    " must have exactly one constructor, found: " + constructors.length);
        }

        Constructor<?> constructor = constructors[0];
        return constructor;
    }

    private Class<?> findImplementation(Class<?> interfaceClass) {
        // Simple implementation discovery
        String packageName = interfaceClass.getPackage().getName();
        String interfaceName = interfaceClass.getSimpleName();

        // Try common implementation patterns
        String[] possibleImplementations = {
                packageName + "." + interfaceName + "Impl",
                packageName + "." + interfaceName.substring(0, 1).toUpperCase() + interfaceName.substring(1) + "Impl",
                packageName + ".Default" + interfaceName,
        };

        if (interfaceClass.getName().equals("org.example.detective.Detective")) {
            return org.example.detective.SherlockHolmes.class;
        }
        if (interfaceClass.getName().equals("org.example.repository.Crime")) {
            return org.example.repository.CrimeArchive.class;
        }

        // Try to find the implementation by package scanning
        for (String implName : possibleImplementations) {
            try {
                return Class.forName(implName);
            } catch (ClassNotFoundException e) {
                // Continue trying
            }
        }

        throw new RuntimeException("No implementation found for interface: " + interfaceClass.getName());
    }

    public void clear() {
        instances.clear();
    }
}
