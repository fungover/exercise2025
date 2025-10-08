package org.fungover.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {
    // Store the interface and the concrete implementation
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <T> void bind(Class<T> abstraction, Class<? extends T> impl) {
        bindings.put(abstraction, impl);
    }

    public <T> T get(Class<T> type) {
        try {
            Class<?> concrete = type;

            if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
                concrete = bindings.get(type);
                if (concrete == null) {
                    throw new IllegalStateException("No binding for " + type.getName());
                }
            }

            Constructor<?>[] constructos = concrete.getDeclaredConstructors();
            if (constructos.length != 1) {
                throw new IllegalStateException(concrete.getName() + " must have exactly one constructor");
            }

            Constructor<?> constructor = constructos[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = get(paramTypes[i]);
            }

            constructor.setAccessible(true);
            @SuppressWarnings("unchecked")
            T instance = (T) constructor.newInstance(args);
            return instance;
        } catch (RuntimeException re) {
            throw re;
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct " + type.getName(), e);
        }
    }
}
