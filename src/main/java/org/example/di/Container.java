package org.example.di;


import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Container {

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    //Register binding in between interface and implementation
    public <T> void bind(Class<T> abstraction, Class<? extends T> implementation) {
        bindings.put(abstraction, implementation);
    }

    private final ThreadLocal<Deque<Class<?>>> resolving = ThreadLocal.withInitial(ArrayDeque::new);
    public <T> T getInstance(Class<T> type) {
        Deque<Class<?>> stack = resolving.get();
        if (stack.contains(type)) {
            throw new IllegalStateException("Circular dependency detected: " + stack + " -> " + type);
        }
        stack.push(type);
        try {
            // If type is interface use implementation from bindings
            if (type.isInterface()) {
                Class<?> implementation = bindings.get(type);
                if (implementation == null) {
                   throw new RuntimeException("No implementation found for " + type);
                }
                type = (Class<T>) implementation;
            }
            // Get constructor
            Constructor<?>[] constructors = type.getConstructors();
            if (constructors.length != 1) {
                throw new IllegalStateException(
                        "Expected exactly one public constructor for " + type +
                                " but found " + constructors.length
                );
            }
            Constructor<?> constructor = constructors[0];
            // Get param types
            Class<?>[] paramTypes = constructor.getParameterTypes();
            // No params then create
            if (paramTypes.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            }
            // Or else create dependency recursive
            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }
            // Create object with dependencies
            return type.cast(constructor.newInstance(dependencies));
        } catch (Exception e) {
            throw new RuntimeException("Could not create instance of " + type, e);
        } finally {
            stack.pop();
            if (stack.isEmpty()) {
                resolving.remove();
            }
        }

    }
}
