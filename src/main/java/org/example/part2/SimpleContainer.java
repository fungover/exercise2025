package org.example.part2;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {

    // Map interfaces to concrete implementations
    private final Map<Class<?>, Class<?>> interfaceBindings = new HashMap<>();

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
            // Check if this is an interface that need mapping
            Class<?> implementationClass = clazz;
            if (interfaceBindings.containsKey(clazz)) {
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

            if (parameterTypes.length == 0) {
                // Default constructor
                return (T) constructor.newInstance();
            } else {
                // Constructor with parameters - recursion
                Object[] dependencies = new Object[parameterTypes.length];

                for (int i = 0; i < parameterTypes.length; i++) {
                    System.out.println(" Creating dependency " + (i + 1) + " : " +
                            parameterTypes[i].getSimpleName());

                    dependencies[i] = getInstance(parameterTypes[i]); // Recursive call
                }

                return (T) constructor.newInstance(dependencies);
            }

        } catch (Exception e) {
            System.out.println("Failed to create " + clazz.getSimpleName() + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }

    }
}
