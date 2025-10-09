package org.example.DIContainer;

import org.example.service.ChatService;
import org.example.service.MainChatService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;


public class DIContainer {
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> type) {
        try {
            if (type.equals(ChatService.class)) {
                return (T) new MainChatService();
            }

            if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
                throw new RuntimeException("Cannot instantiate Interface or abstract type " + type.getName());
            }
            Constructor<?>[] ctors = type.getConstructors();
            if (ctors.length == 0) {
                throw new RuntimeException("No public constructors found for " + type.getName());
            }

            Constructor<?> constructor = ctors[0];
            Class<?>[] paramTypes = constructor.getParameterTypes();

            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }
            return type.cast(constructor.newInstance(dependencies));
        } catch (Exception e) {
            throw new RuntimeException("DIContainer failed to create " + type, e);
        }
    }
}