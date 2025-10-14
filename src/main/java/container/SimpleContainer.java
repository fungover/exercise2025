package container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
 public class SimpleContainer {

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public SimpleContainer() {
        bindings.put(api.Greetings.class, impl.GreetingsImpl.class);
        bindings.put(api.MessageRepository.class, impl.MessageMemoryRepository.class);
    }

    public <T> T getInstance(Class<T> requested) {
        Class<?> type = bindings.getOrDefault(requested, requested);

        try {
            Constructor<?> ctor = type.getDeclaredConstructors()[0];
            Class<?>[] paramTypes = ctor.getParameterTypes();

            Object[] args = new Object[paramTypes.length];
            for (int i = 0; i < paramTypes.length; i++) {

                @SuppressWarnings("Not checked")
                Class<Object> depType = (Class<Object>) paramTypes[i];
                args[i] = getInstance(depType);
            }

            ctor.setAccessible(true);
            Object instance = ctor.newInstance(args);

            return requested.cast(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Could not create " + requested.getName(), e);
        }
    }


}
