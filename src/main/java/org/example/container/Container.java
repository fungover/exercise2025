package org.example.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <T> T create(Class<?> clazz) throws Exception {
        Class<?> impl = bindings.getOrDefault(clazz, clazz);

        // get constructor
        Constructor<?>[] constructors = impl.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new IllegalArgumentException("No constructors found for class: " + impl.getName());
        }
        // For simplicity, use the first constructor, but document this limitation
        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);

        // get parameters from constructor
        Class<?>[] paramTypes = constructor.getParameterTypes();
        System.out.println(paramTypes);
        Object[] dependencies = new Object[paramTypes.length];
        System.out.println(paramTypes.length + " hej");

        for (int i = 0; i < paramTypes.length; i++) {
            dependencies[i] = create(paramTypes[i]);
        }


        return (T) constructor.newInstance(dependencies);
    }

    public <T> void bind(Class<T> base, Class<? extends T> impl) {
        bindings.put(base, impl);
    }

}
