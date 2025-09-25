package org.example.part2;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {

    private final Map<Class<?>, Class<?>> interfaceBindings = new HashMap<>();
    private final Map<Class<?>, Object> singletonInstances = new HashMap<>();


    public SimpleContainer() {

        // Config which implementation to use for interfaces
        interfaceBindings.put(org.example.part1.services.CPUService.class,
                org.example.part1.services.IntelCPUService.class);
        interfaceBindings.put(org.example.part1.services.GPUService.class,
                org.example.part1.services.NvidiaGPUService.class);
        interfaceBindings.put(org.example.part1.services.AssemblyService.class,
                org.example.part1.services.ProffesionalAssemblyService.class);
        interfaceBindings.put(org.example.part1.repository.ComponentRepository.class,
                org.example.part1.repository.InMemoryComponentRepository.class);
    }

    public <T> T getInstance(Class<T> clazz) {
        try {
            // Check for existing singleton
            if (singletonInstances.containsKey(clazz)) {
                System.out.println("Reusing singleton: " + clazz.getSimpleName());
                return (T) singletonInstances.get(clazz);
            }

            // Check if this is an interface that need mapping
            Class<?> implementationClass = clazz;
            if (interfaceBindings.containsKey(clazz)) {
                // Also check if we have a singleton of the implementation
                if (singletonInstances.containsKey(interfaceBindings.get(clazz))) {
                    System.out.println("Reusing singleton implementation: " +
                            interfaceBindings.get(clazz).getSimpleName());

                    return (T) singletonInstances.get(interfaceBindings.get(clazz));
                }

                implementationClass = interfaceBindings.get(clazz);
                System.out.println("Mapping interface " + clazz.getSimpleName() + " to " +
                        implementationClass.getSimpleName());
            }
            System.out.println("Creating instance of: " + implementationClass.getName());

            // Get all constructors
            Constructor<?>[] constructors = implementationClass.getDeclaredConstructors();

            if (constructors.length == 0) {
                throw new RuntimeException("No constructors found for " + implementationClass.getName());
            }

            // Take the first constructor assuming only one constructor per class
            Constructor<?> constructor = constructors[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            System.out.println("Constructor has " + parameterTypes.length + " parameters");

            T instance;
            if (parameterTypes.length == 0) {
                instance = (T) constructor.newInstance();
            } else {
                Object[] dependencies = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    System.out.println(" Creating dependency " + (i + 1) + " : " +
                            parameterTypes[i].getSimpleName());

                    dependencies[i] = getInstance(parameterTypes[i]); // Recursive call
                }

                instance = (T) constructor.newInstance(dependencies);
            }

            // Store as singleton for future use
            singletonInstances.put(implementationClass, instance);
            if (clazz.isInterface()) {
                singletonInstances.put(clazz, instance);
            }

            return instance;

        } catch (Exception e) {
            System.out.println("Failed to create " + clazz.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
