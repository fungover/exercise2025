package org.example.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {

    private final Map<Class<?>, Class<?>> interfaceMap = new HashMap<>();

    public <X> void register(Class<X> iface, Class<? extends X> impl) {
        interfaceMap.put(iface, impl);
    }

    public <X> X getInstance(Class<X> clazz) {
        try {
            Class<?> concrete = clazz.isInterface() ? interfaceMap.get(clazz) : clazz;
            if (concrete == null) {
                throw new RuntimeException("No implementation registered for " + clazz.getName());
            }

            Constructor<?> constructor = concrete.getConstructors()[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }

            return clazz.cast(constructor.newInstance(dependencies));

        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
        }
    }
}