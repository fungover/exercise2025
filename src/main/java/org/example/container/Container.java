package org.example.container;
import org.example.repository.InMemoryMessageRepository;
import org.example.repository.MessageRepository;
import org.example.service.MessageService;
import org.example.service.MessageServiceHandler;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class Container {
    /* A map to store which concrete class to use for each interface.
    put in only the interfaces we want so that the container will use the class I give it. */
    private final Map<Class<?>, Class<?>> interfaceMappings = new HashMap<>();

    /* The constructor registers which concrete class to use for each interface.
     it tells the container what to create whenever an interface is requested. */
    public Container() {

        interfaceMappings.put(MessageService.class, MessageServiceHandler.class);
        interfaceMappings.put(MessageRepository.class, InMemoryMessageRepository.class);

    }

    // A generic method to get an object from the container.
    public <T> T getInstance(Class<T> clazz) {
        try {
            // If the class is an interface, look up the real class in the map
            // Set clazz to the real class so an object can be created
            if (clazz.isInterface()) {
                Class<?> implClass = interfaceMappings.get(clazz);
                if (implClass == null) {
                    throw new RuntimeException("No implementation registered for " + clazz);
                }
                clazz = (Class<T>) implClass;
            }

            // get the constructor
            Constructor<?> constructor = clazz.getConstructors()[0];

            // Get constructor parameters and make an object for each one
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] dependencies = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {
                dependencies[i] = getInstance(paramTypes[i]);
            }

            // Create a new object with its dependencies and return it
            return clazz.cast(constructor.newInstance(dependencies));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Couldn't create instance of " + clazz, e);
        }
    }
}
