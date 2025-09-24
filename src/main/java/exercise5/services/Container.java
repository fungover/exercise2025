package exercise5.services;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Container {

    public <T> T getAccess(Class<T> name){
            return getClass(name);
    }

    //Explicit method to handle recursive calls to catch constructors and parameters for all dependencies
    private <T> T getClass(Class<T> name){

        try{
            //<?> tells the compiler that the class is unknown
            Constructor<?>[] constructor= name.getDeclaredConstructors();

            if(constructor.length == 0){
                throw new RuntimeException("No constructor could be found");
            }
            //Sets accessible if private, assumes only one constructor
            constructor[0].setAccessible(true);
            //Checks for constructor parameters
            Class<?>[] parameters = constructor[0].getParameterTypes();
            //Creates object with length of parameters
            Object[] arguments = new Object[parameters.length];
            //Recursively request dependencies
            for (int i = 0; i < parameters.length; i++) {
                arguments[i] = getClass(parameters[i]);
            }
            //Returns new instance of constructor is created with needed dependencies
            return name.cast(constructor[0].newInstance(arguments));

        }catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

}
