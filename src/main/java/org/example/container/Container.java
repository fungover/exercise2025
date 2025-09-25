package org.example.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <A, B extends A> void bind(Class<A> abstraction, Class<B> implementation) {
        bindings.put(abstraction, implementation);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        try {

            Class<?> impl = type;
            if (impl.isInterface()) {
                impl = bindings.get(type);
                if (impl == null) {
                    throw new IllegalStateException("No binding registered for " + type.getName());
                }
            }
            Constructor<?> ctor = impl.getDeclaredConstructors()[0];
            Class<?>[] paramTypes = ctor.getParameterTypes();

            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = get(paramTypes[i]);
            }

            return (T) ctor.newInstance(args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
