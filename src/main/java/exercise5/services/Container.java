package exercise5.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class Container {

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();
    //Create register for which classes to use for specific interface
    public <T> void register(Class<T> abstraction, Class<? extends T> implementation) {
        bindings.put(abstraction, implementation);
    }

    public <T> T getAccess(Class<T> type){
        return getClass(type);
    }

    private <T> T getClass(Class<T> type){

        try{
            Class<?> target = resolve(type);
            Constructor<?>[] constructor= target.getDeclaredConstructors();

            if(constructor.length == 0){
                throw new RuntimeException("No constructor could be found");
            }
            //Sets accessible if private, assumes only one constructor
            constructor[0].setAccessible(true);

            Class<?>[] parameters = constructor[0].getParameterTypes();

            Object[] arguments = new Object[parameters.length];
            //Recursively request dependencies
            for (int i = 0; i < parameters.length; i++) {
                arguments[i] = getClass(parameters[i]);
            }
            //Returns new instance of constructor is created with needed dependencies
            return type.cast(constructor[0].newInstance(arguments));

        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
    //Checks if possible to create instance directly or to find registered class for interface
    private <T> Class<?> resolve(Class<T> type){
        if(!type.isInterface() && !Modifier.isAbstract(type.getModifiers())){
            return type;
        }

        Class<?> implementation = bindings.get(type);

        if(implementation == null){
            throw new RuntimeException(String.format("No binding found for %s", type.getName()));
        }

        return implementation;
    }

}
