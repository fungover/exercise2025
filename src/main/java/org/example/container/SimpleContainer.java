package org.example.container;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class SimpleContainer {
    private static class CircularDependencyException extends RuntimeException {
        CircularDependencyException(String message) {
            super(message);
        }
    }

    private final Map<Class<?>, Object> singletons = new ConcurrentHashMap<>();
    private final Map<Class<?>, Class<?>> implementations = new ConcurrentHashMap<>();


    private final ThreadLocal<List<Class<?>>> resolutionChain =
        ThreadLocal.withInitial(ArrayList::new);


    private static final Class<?> INJECT_ANNOTATION = loadInjectAnnotation();


    private static Class<?> loadInjectAnnotation() {
        try {
            return Class.forName("jakarta.inject.Inject");
        } catch (ClassNotFoundException e) {

            return null;
        }
    }


    private static boolean hasInjectAnnotation(Constructor<?> constructor) {
        if (INJECT_ANNOTATION == null) {
            return false;
        }
        try {
            @SuppressWarnings("unchecked")
            Class<java.lang.annotation.Annotation> annotationClass =
                (Class<java.lang.annotation.Annotation>) INJECT_ANNOTATION;
            return constructor.isAnnotationPresent(annotationClass);
        } catch (Exception e) {
            return false;
        }
    }


    public void register(Class<?> interfaceClass, Class<?> implementationClass) {
        implementations.put(interfaceClass, implementationClass);
    }


    public <T> T get(Class<T> clazz) {
        // Check if there's a mapped implementation for this interface
        Class<?> actualClass = implementations.getOrDefault(clazz, clazz);

        // Atomically get or create the singleton instance (thread-safe)
        return clazz.cast(singletons.computeIfAbsent(actualClass, key -> createInstance(key)));
    }

    private Object createInstance(Class<?> actualClass) {
        // Get current resolution chain
        List<Class<?>> chain = resolutionChain.get();

        // Detect circular dependencies
        if (chain.contains(actualClass)) {
            throw new CircularDependencyException(
                "Circular dependency detected while resolving: " + actualClass.getName() +
                " (resolution chain: " + formatChain(chain, actualClass) + ")"
            );
        }

        // Add current class to resolution chain
        chain.add(actualClass);

        try {
            // Get all public constructors of the class
            Constructor<?>[] constructors = actualClass.getConstructors();

            if (constructors.length == 0) {
                throw new IllegalArgumentException(
                    "Class " + actualClass.getName() + " has no public constructors"
                );
            }

            // Select the best constructor using deterministic strategy
            Constructor<?> constructor = selectConstructor(constructors, actualClass);

            // Get parameter types of the selected constructor
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            // If constructor has no parameters, create a simple instance
            if (parameterTypes.length == 0) {
                return constructor.newInstance();
            }

            // Recursively resolve each dependency
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameters[i] = get(parameterTypes[i]);
            }

            // Create instance with resolved dependencies
            return constructor.newInstance(parameters);

        } catch (Exception e) {

            if (e instanceof CircularDependencyException) {
                throw (CircularDependencyException) e;
            }
            throw new RuntimeException(
                "Failed to instantiate class: " + actualClass.getName() +
                " - " + e.getMessage(),
                e
            );
        } finally {

            chain.remove(actualClass);


            if (chain.isEmpty()) {
                resolutionChain.remove();
            }
        }
    }


    private Constructor<?> selectConstructor(Constructor<?>[] constructors, Class<?> clazz)
            throws IllegalArgumentException {


        for (Constructor<?> constructor : constructors) {
            if (hasInjectAnnotation(constructor)) {
                return constructor;
            }
        }


        Constructor<?> selected = constructors[0];

        for (int i = 1; i < constructors.length; i++) {
            Constructor<?> candidate = constructors[i];

            int selectedParamCount = selected.getParameterTypes().length;
            int candidateParamCount = candidate.getParameterTypes().length;

            if (candidateParamCount > selectedParamCount) {

                selected = candidate;
            } else if (candidateParamCount == selectedParamCount && candidateParamCount > 0) {

                if (isLexicographicallySmaller(candidate.getParameterTypes(),
                                              selected.getParameterTypes())) {
                    selected = candidate;
                }
            }
        }

        return selected;
    }

    private boolean isLexicographicallySmaller(Class<?>[] paramTypes1, Class<?>[] paramTypes2) {
        int minLength = Math.min(paramTypes1.length, paramTypes2.length);

        for (int i = 0; i < minLength; i++) {
            String name1 = paramTypes1[i].getName();
            String name2 = paramTypes2[i].getName();

            int comparison = name1.compareTo(name2);
            if (comparison != 0) {
                return comparison < 0;
            }
        }

        return false;
    }

    private String formatChain(List<Class<?>> chain, Class<?> current) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> cls : chain) {
            if (sb.length() > 0) sb.append(" -> ");
            sb.append(cls.getSimpleName());
        }
        sb.append(" -> ").append(current.getSimpleName());
        return sb.toString();
    }


    public void clear() {
        singletons.clear();
    }


    public int getCachedInstanceCount() {
        return singletons.size();
    }
}
