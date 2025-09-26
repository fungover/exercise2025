package org.example.container;

import jakarta.inject.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class SimpleContainer {

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>(); // Map to store bidings between interface and implementation (E.g., MessageService -> EmailMessageService)


    private final ThreadLocal<Deque<Class<?>>> resolving = //ThreadLocal stack to track all types that are currently being resolved to detect circular dependencies.
            ThreadLocal.withInitial(ArrayDeque::new);

    public <T> void bind(Class<T> interfaceClass, Class<? extends T> implementationClass) { // Method to bind an interface to its implementation
        bindings.put(interfaceClass, implementationClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> requestedType) {
        Deque<Class<?>> stack = resolving.get(); // Get the current stack of types being resolved

        if (stack.contains(requestedType)) {
            throw new IllegalStateException("Circular dependency detected: " + stack + " -> " + requestedType); // Check for circular dependency
        }

        stack.push(requestedType); // Push the requested type onto the stack
        try {
            Class<?> implementationClass = requestedType; // Default to the requested type

            //If the requested type is an interface, look up its implementation in the bindings map.
            if (requestedType.isInterface()) {
                Class<?> implementation = bindings.get(requestedType);
                if (implementation == null) {
                    throw new ContainerException("No implementation found for " + requestedType);
                }
                implementationClass = implementation;
            }

            Constructor<?> constructor = chooseConstructor(implementationClass); // Choose the appropriate constructor with our helper method.

            // If the constructor has no parameters, simply instantiate it.
            if (constructor.getParameterCount() == 0) {
                return (T) constructor.newInstance();
            }

            // For constructors with parameters, resolve each dependency recursively.
            Parameter[] parameters = constructor.getParameters();
            Object[] dependencies = new Object[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                dependencies[i] = getInstance(parameters[i].getType());
            }

            return (T) constructor.newInstance(dependencies); // Instantiate the object with its dependencies.
        } catch (Exception e) {
            throw new ContainerException("Could not create instance of " + requestedType.getName(), e);
        } finally {
            stack.pop(); // Pop the type off the stack after resolution is complete
            if (stack.isEmpty()) {
                resolving.remove(); // Clean up the ThreadLocal if the stack is empty
            }
        }
    }

    private Constructor<?> chooseConstructor(Class<?> impl) { // Helper method to choose the appropriate constructor for instantiation.
        Constructor<?>[] all = impl.getConstructors(); // Get all public constructors of the class.


        // Filter out those that have the @Inject annotation.
        Constructor<?>[] injectCtors = Arrays.stream(all)
                .filter(c -> c.isAnnotationPresent(Inject.class))
                .toArray(Constructor<?>[]::new);

        // If there's exactly one @Inject constructor, use it.
        if (injectCtors.length == 1) {
            return injectCtors[0];
        }
        // If there are multiple @Inject constructors, that's an error.
        if (injectCtors.length > 1) {
            throw new ContainerException("Multiple @Inject constructors found for " + impl.getName());
        }

        // If there are no @Inject constructors, but exactly one public constructor, use that.
        if (all.length == 1) {
            return all[0];
        }
        // If there are no public constructors, that's an error.
        if (all.length == 0) {
            throw new ContainerException("No public constructors found for " + impl.getName());
        }

        // If there are multiple public constructors and none are annotated with @Inject, that's ambiguous.
        throw new ContainerException("Ambiguous constructors for " + impl.getName()
                + " (no @Inject and multiple public constructors)");
    }
}
