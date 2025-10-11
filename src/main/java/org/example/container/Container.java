package org.example.container;

import java.lang.reflect.Constructor;

public class Container {

    public static <T> T createInstance(Class<T> rootClass, Object... args) {
        if (rootClass == null) {
            throw new IllegalArgumentException("rootClass cannot be null");
        }
        try {
            Constructor<?>[] constructors = rootClass.getDeclaredConstructors();
            // Sort for deterministic selection when multiple constructors match
            java.util.Arrays.sort(constructors,
                    java.util.Comparator.comparing(Constructor::getParameterCount));
            for (Constructor<?> ctor : constructors) {
                Class<?>[] paramTypes = ctor.getParameterTypes();
                if (paramTypes.length == args.length) {
                    boolean match = true;
                    for (int i = 0; i < paramTypes.length; i++) {
                        Class<?> argType = args[i] != null ? args[i].getClass() : null;
                        if (!isCompatible(paramTypes[i], argType)) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        ctor.setAccessible(true);
                        return rootClass.cast(ctor.newInstance(args));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate " + rootClass.getName(), e);
        }
        throw new RuntimeException("No compatible constructor found for " +
                rootClass.getName() + " with " + args.length + " arguments");
    }

    private static boolean isCompatible(Class<?> paramType, Class<?> argType) {
        if (argType == null) {
            return !paramType.isPrimitive();
        }
        if (paramType.isPrimitive()) {
            return isPrimitiveAssignable(paramType, argType);
        }
        return paramType.isAssignableFrom(argType);
    }

    private static boolean isPrimitiveAssignable(Class<?> primitiveType, Class<?> argType) {
        if  (argType == null) return false;
        if (primitiveType == boolean.class) return argType == Boolean.class;
        if (primitiveType == byte.class) return argType == Byte.class;
        if (primitiveType == char.class) return argType == Character.class;
        if (argType == Character.class) return primitiveType == int.class ||
                primitiveType == long.class || primitiveType == float.class ||
                primitiveType == double.class;
        if (!Number.class.isAssignableFrom(argType)) return false;
        if (primitiveType == short.class) return argType == Short.class ||
                argType == Byte.class;
        if (primitiveType == int.class) return argType == Integer.class ||
                argType == Short.class || argType == Byte.class;
        if (primitiveType == long.class) return argType == Long.class ||
                argType == Integer.class || argType == Short.class ||
                argType == Byte.class;
        if (primitiveType == float.class) return argType == Float.class ||
                argType == Long.class || argType == Integer.class ||
                argType == Short.class || argType == Byte.class;
        if (primitiveType == double.class) return argType == Double.class ||
                argType == Float.class || argType == Long.class ||
                argType == Integer.class || argType == Short.class ||
                argType == Byte.class;
        return false;
    }
}
