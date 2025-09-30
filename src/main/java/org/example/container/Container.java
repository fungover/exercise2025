package org.example.container;

import java.lang.reflect.Constructor;

public class Container {

    public static <T> T createInstance(Class<T> rootClass, Object... args) {
        try {
            for (Constructor<?> ctor : rootClass.getDeclaredConstructors()) {
                Class<?>[] paramTypes = ctor.getParameterTypes();
                if (paramTypes.length == args.length) {
                    boolean match = true;
                    for (int i = 0; i < paramTypes.length; i++) {
                        Class<?> argType = args[i] != null ? args[i].getClass() : null;
                        if (!isCompatible(paramTypes[i], argType)) {
                            match = false;
                            System.err.println("Argument " + i +
                                    " is not compatible with " +
                                    paramTypes[i].getName());
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
            System.err.println("Failed to instantiate: " + e);
        }
        throw new RuntimeException("Failed to instantiate " + rootClass.getName());
    }

    private static boolean isCompatible(Class<?> paramType, Class<?> argType) {
        if (argType == null) {
            return !paramType.isPrimitive();
        }
        if (paramType.isPrimitive()) {
            return getWrapperType(paramType).equals(argType);
        }
        return paramType.isAssignableFrom(argType);
    }

    private static Class<?> getWrapperType(Class<?> primitiveType) {
        if (primitiveType == boolean.class) return Boolean.class;
        if (primitiveType == byte.class) return Byte.class;
        if (primitiveType == char.class) return Character.class;
        if (primitiveType == short.class) return Short.class;
        if (primitiveType == int.class) return Integer.class;
        if (primitiveType == long.class) return Long.class;
        if (primitiveType == float.class) return Float.class;
        if (primitiveType == double.class) return Double.class;
        return primitiveType; // Default, non-primitive value
    }
}
