package org.example;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    // Kopplar ett interface till en implementation
    public <A> SimpleContainer bind(Class<A> abstraction, Class<? extends A> implementation) {
        bindings.put(abstraction, implementation);
        return this;
    }

    // Returnerar instans av önskad klass och löser dess beroenden rekursivt
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> requested) {
        Class<?> type = bindings.getOrDefault(requested, requested);
        try {
            Constructor<?> ctor = type.getDeclaredConstructors()[0];
            Constructor<?>[] ctors = type.getDeclaredConstructors();
            if (ctors.length == 0) {
            throw new IllegalArgumentException("No constructors available for: " + type.getName());
            }
            Constructor<?> ctor = ctors[0];
            ctor.setAccessible(true);
            Class<?>[] paramTypes = ctor.getParameterTypes();
            Object[] deps = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                deps[i] = get(paramTypes[i]);
            }
            return (T) ctor.newInstance(deps);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to create: " + type.getName(), e);
        }
    }
}
