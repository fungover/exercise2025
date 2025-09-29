package exercise5.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Container{
    private final Map<Class<?>, Class<?>> registry = new HashMap<>();
    private Map<Class<?>, Object> instances = new HashMap<>();
    public <T> void register(Class<T> type, Class<? extends T> instance) {
        registry.put(type, instance);
    }
    private static final Logger log = Logger.getLogger(Container.class.getName());

    public <T> T resolve(Class<T> type) {
        log.info("Resolving "+type.getSimpleName());
        Object cached = instances.get(type);
        if(cached != null) {
            log.info(type.getSimpleName());
            return type.cast(cached);
        }
        Class<?> instanceType = registry.getOrDefault(type, type);
        try {
            Constructor<?>[] constructors = instanceType.getDeclaredConstructors();
            Constructor<?> constructor = constructors[0];
            constructor.setAccessible(true);
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if(parameterTypes.length == 0){
                log.info("No parameters in constructor for "+ instanceType.getSimpleName());
                T result = type.cast(constructor.newInstance());
                instances.put(type, result);
                return result;
            }
            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                log.info("Creating the dependency "+parameterTypes[i].getSimpleName());
                dependencies[i] = resolve(parameterTypes[i]);
            }
            T classInstance = (T) type.cast(constructor.newInstance(dependencies));
            log.info("Created instance of "+classInstance.getClass().getSimpleName());
            instances.put(type, classInstance);
            return classInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
