package org.example.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>(); // Holds interface and the concrete class (example: DataRepository -> DatabaseRepository)

    // Holds its own stack (per thread) over what types are currently about to be instantiated.
    // This is to detect circular dependencies.
    private final ThreadLocal<Deque<Class<?>>> resolving =
            ThreadLocal.withInitial(ArrayDeque::new);

    public <T> void bind(Class<T> interfaceClass, Class<? extends T> implementationClass) {
        bindings.put(interfaceClass, implementationClass); // Saves what implementation to use for the given interface.
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> requestedType) {
        Deque<Class<?>> stack = resolving.get(); // Gets the stack for this thread.


        if (stack.contains(requestedType)) { // If the stack already contains the type that we are about to create, we have a circular dependency.
            throw new IllegalStateException("Circular dependency detected: " + stack + " -> " + requestedType); // Example: A -> B -> C -> A and we are about to create A again. Then we throw an exception.
        }

        stack.push(requestedType); // If no circular dependency, we push the type that we are about to create.
        try {
            Class<?> implementationClass = requestedType; // Default to the requested type.

            if (requestedType.isInterface()) { // If the requested type is an interface, we need to find the implementation.
                Class<?> implementation = bindings.get(requestedType); // Look up the implementation in the bindings map.
                if (implementation == null) {
                    throw new RuntimeException("No implementation found for " + requestedType); // If no implementation is found, we throw an exception.
                }
                implementationClass = implementation; // Otherwise, we set the implementation class to the found implementation.
            }

            Constructor<?>[] constructors = implementationClass.getConstructors(); // Get all public constructors.
            if (constructors.length == 0) {
                throw new RuntimeException("No public constructors found for " + implementationClass.getName()); // If no public constructors, we throw an exception.
            }
            Constructor<?> constructor = constructors[0]; // For simplicity, we take the first constructor. In a real-world scenario, we might want to be more selective.

            if (constructor.getParameterCount() == 0) { // If the constructor has no parameters, we can create the instance directly.
                return (T) constructor.newInstance();
            }

            Parameter[] parameters = constructor.getParameters(); // Get the parameters of the constructor.
            Object[] dependencies = new Object[parameters.length]; // Array to hold the dependencies.
            for (int i = 0; i < parameters.length; i++) {
                dependencies[i] = getInstance(parameters[i].getType()); // Recursively get the instance for each parameter type.
            }

            return (T) constructor.newInstance(dependencies); // Create the instance with the resolved dependencies.
        } catch (Exception e) {
            throw new RuntimeException("Could not create instance of " + requestedType.getName(), e);
        } finally {
            stack.pop(); // Remove the type from the stack when done.
            if (stack.isEmpty()) {
                resolving.remove(); // Clean up the ThreadLocal if the stack is empty.
            }
        }
    }
}

