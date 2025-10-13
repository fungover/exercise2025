package org.example.di.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


// RobotChefContainer that uses Java Reflection to instantiate objects
// and resolve dependencies based on constructor parameters.
public class RobotChefContainer {
    public final Map<Class<?>, Class<?>> typeMappings = new HashMap<>();

    public <T> void register(Class<T> baseType, Class<? extends T> implType) {
        typeMappings.put(baseType, implType);
    }


    public <T> T getInstance(Class<T> type) {
        try {
            System.out.println("[Skapar robot instans av:] " + type.getSimpleName());

            if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
                Class<?> impl = typeMappings.get(type);
                if (impl == null) {
                    throw new RuntimeException("Ingen binding hittades för " + type);
                }
                type = (Class<T>) impl;
            }

            Constructor<?> constructor = type.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            if (parameterTypes.length == 0) {
                return (T) constructor.newInstance();
            }

            Object[] dependencies = Arrays.stream(parameterTypes)
                    .map(this::getInstance)
                    .toArray();

            return (T) constructor.newInstance(dependencies);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}