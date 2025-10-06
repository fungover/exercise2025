package part2;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    // Map interface to implementation
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    // Register which implementation to use for each interface
    public <T> void register(Class<T> interfaceClass, Class<? extends T> implementationClass) {
        bindings.put(interfaceClass, implementationClass);
        System.out.println("  Registrerad: " + interfaceClass.getSimpleName() +
                " → " + implementationClass.getSimpleName());
    }

    // Get instance of requested class - creates entire dependency graph
    public <T> T getInstance(Class<T> clazz) throws Exception {
        System.out.println("  Löser upp: " + clazz.getSimpleName());

        // If it's an interface, get the registered implementation
        Class<?> classToInstantiate = bindings.getOrDefault(clazz, clazz);

        // Get the constructor (assuming only one constructor)
        Constructor<?>[] constructors = classToInstantiate.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new RuntimeException("Ingen konstruktor hittades för " + classToInstantiate.getSimpleName());
        }

        Constructor<?> constructor = constructors[0];

        // Get parameter types
        Class<?>[] parameterTypes = constructor.getParameterTypes();

        // If no parameters, just create instance
        if (parameterTypes.length == 0) {
            return (T) constructor.newInstance();
        }

        // Recursively resolve dependencies
        Object[] dependencies = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            System.out.println("    Behöver: " + parameterTypes[i].getSimpleName());
            dependencies[i] = getInstance(parameterTypes[i]);
        }

        // Create instance with all dependencies
        return (T) constructor.newInstance(dependencies);
    }
}
