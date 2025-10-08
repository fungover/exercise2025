package di_lab;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public <T> T getInstance(Class<T> type) {
        try {
            // Get the first public constructor
            Constructor<?> constructor = type.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();

            // If no parameters, create it
            if (parameterTypes.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            }

            // Otherwise, create each dependency recursively
            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                dependencies[i] = getInstance(parameterTypes[i]);
            }

            // Return the final instance
            return (T) constructor.newInstance(dependencies);

        } catch (Exception e) {
            throw new RuntimeException("Could not create instance of " + type.getName(), e);
        }
    }
}

