package org.example.di.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import jakarta.inject.Inject;
import java.util.Arrays;

public final class SimpleContainer implements Container {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();
    private final Map<Class<?>, Object> instances = new HashMap<>();

    @Override
    public <A> void bind(Class<A> interfaceType, Class<? extends A> implType) {
        bindings.put(interfaceType, implType);
    }

    @Override
    public <T> void bindInstance(Class<T> type, T instance) {
        instances.put(type, instance);
    }

    @Override
    public <T> T get(Class<T> type) {
        try {
            Object prebuiltInstance = instances.get(type);
            if (prebuiltInstance != null) return type.cast(prebuiltInstance);

            Class<?> implClass = type.isInterface() ? bindings.get(type) : type;
            if (implClass == null) throw new IllegalStateException("No binding for " + type.getName());

            Constructor<?> constructor = Arrays.stream(implClass.getDeclaredConstructors())
                    .filter(c -> c.isAnnotationPresent(Inject.class))
                    .findFirst()
                    .orElseGet(() -> Arrays.stream(implClass.getDeclaredConstructors())
                            .filter(c -> c.getParameterCount() == 0)
                            .findFirst()
                            .orElseGet(() -> Arrays.stream(implClass.getDeclaredConstructors())
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalStateException("No suitable constructor for " + implClass.getName()))));
            constructor.setAccessible(true);
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                args[i] = get(paramTypes[i]);
            }
            return type.cast(constructor.newInstance(args));

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
