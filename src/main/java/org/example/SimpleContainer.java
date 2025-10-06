package org.example;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <A> SimpleContainer bind(Class<A> abstraction, Class<? extends A> implementation) {
        bindings.put(abstraction, implementation);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> requested) {
        Class<?> type = bindings.getOrDefault(requested, requested);
        try {
            Constructor<?>[] ctors = type.getDeclaredConstructors();
            if (ctors.length == 0) {
                throw new IllegalArgumentException("No constructors available for: " + type.getName());
            }
            Constructor<?> constructor = ctors[0];
            constructor.setAccessible(true);

            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] deps = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                deps[i] = get((Class<?>) paramTypes[i]); // rekursivt
            }
            return (T) constructor.newInstance(deps);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create: " + type.getName(), e);
        }
    }
}
