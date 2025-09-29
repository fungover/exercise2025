package exercise5.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container{
    private final Map<Class<?>, Class<?>> registry = new HashMap<>();
    private Map<Class<?>, Object> instances = new HashMap<>();
    public <T> void register(Class<T> type, Class<? extends T> instance) {
        registry.put(type, instance);
    }

    public <T> T resolve(Class<T> type) {
        System.out.println("Resolving "+type.getSimpleName());
        /*if(registry.containsKey(type)) {
            System.out.println(type.getSimpleName()+" already exists");
            return (T) instances.get(type);
        }
        Class<?> instanceType = registry.get(type);
        if(instanceType == null) {
            instanceType = type;
        }*/
        Object cached = instances.get(type);
        if(cached != null) {
            System.out.println(type.getSimpleName());
            return type.cast(cached);
        }
        Class<?> instanceType = registry.getOrDefault(type, type);
        try {
            Constructor<?>[] constructors = instanceType.getDeclaredConstructors();
            Constructor<?> constructor = constructors[0];
            constructor.setAccessible(true);
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if(parameterTypes.length == 0){
                System.out.println("No parameters in constructor for "+ instanceType.getSimpleName());
                T result = type.cast(constructor.newInstance());
                instances.put(type, result);
                return result;
            }
            Object[] dependencies = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.println("Creating the dependency "+parameterTypes[i].getSimpleName());
                dependencies[i] = resolve(parameterTypes[i]);
            }
            T classInstance = (T) type.cast(constructor.newInstance(dependencies));
            System.out.println("Created instance of "+classInstance.getClass().getSimpleName());
            instances.put(type, classInstance);
            return classInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
