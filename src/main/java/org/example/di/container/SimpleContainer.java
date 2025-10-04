package org.example.di.container;

import org.example.di.common.GreetingsService;
import org.example.di.common.MessageRepository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleContainer {
    private final Map<Class<?>, Class<?>> interfacesToImpl = new HashMap<>();
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final ThreadLocal<Set<Class<?>>> instantiating = ThreadLocal.withInitial(HashSet::new);
    public SimpleContainer() {
        interfacesToImpl.put(MessageRepository.class, MessageRepoImp.class);
        interfacesToImpl.put(GreetingsService.class, GreetingServiceImp.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clazz) {
        try {
            if (!instantiating.get().add(clazz)) {
                throw new RuntimeException("Recursive dependency detected for " + clazz.getName());
            }
            if (singletons.containsKey(clazz)) {
                return (T) singletons.get(clazz);
            }
            Class<?> targetClass = clazz.isInterface() ? interfacesToImpl.getOrDefault(clazz, clazz) : clazz;
            if (targetClass.isInterface()) {
                throw new RuntimeException("No implementation found for " + clazz.getName());
            }

            Constructor<?>[] constructors = targetClass.getDeclaredConstructors();
            Constructor<?> constructor = constructors[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] params = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = getInstance(paramTypes[i]);
            }
            Object instance = constructor.newInstance(params);
            if (!clazz.isInstance(instance)) {
                throw new RuntimeException("Instance is not of type " + clazz.getName());
            }
            singletons.put(clazz, instance);
            return (T) instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to instantiate " + clazz.getName(), e);
        } finally {
            instantiating.get().remove(clazz);
        }
    }
}
