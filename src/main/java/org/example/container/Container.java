package org.example.container;

import jakarta.inject.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <A, B extends A> void bind(Class<A> abstraction, Class<B> implementation) {
        if (implementation == null) {
            throw new IllegalArgumentException("Implementation must not be null for " + abstraction.getName());
        }
        if (!abstraction.isAssignableFrom(implementation)) {
            throw new IllegalArgumentException(implementation.getName() + " does not implement " + abstraction.getName());
        }
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
            if (Modifier.isAbstract(impl.getModifiers())) {
                throw new IllegalStateException("Cannot instantiate abstract type: " + impl.getName());
            }

            Constructor<?>[] ctors = impl.getDeclaredConstructors();
            Constructor<?> ctor = null;
            for (Constructor<?> c : ctors) {
                if (c.isAnnotationPresent(Inject.class)) {
                    if (ctor != null) {
                        throw new IllegalStateException("Multiple @Inject constructors on " + impl.getName());
                    }
                    ctor = c;
                }
            }
            if (ctor == null) {
                if (ctors.length == 1) {
                    ctor = ctors[0];
                } else {
                    throw new IllegalStateException("Multiple constructors on " + impl.getName() + " — annotate one with @Inject");
                }
            }
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
