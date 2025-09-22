package org.example.container;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple dependency injection container that uses Java Reflection
 * to automatically create objects and resolve their dependencies.
 * <p>
 * MAIN FUNCTIONALITY:
 * - Bind mappings between interfaces and concrete classes.
 * - Create objects automatically with all their dependencies.
 * - Uses reflection to inspect constructors and parameters.
 * - Solves dependencies recursively.
 */

public class SimpleContainer {

    /**
     * A Map that stores all "bindings".
     * Key: Interface or abstract class (ex: MessageService.class)
     * Value: Concrete implementation (ex: EmailMessageService.class)
     * <p>
     * - This allows us to say: "When someone asks for MessageService, give them EmailMessageService".
     */

    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    /**
     * Register a binding between an interface and a concrete class.
     * <p>
     * EXAMPLE:
     * container.bind(DataRepository.class, DatabaseRepository.class);
     * means: "When someone asks for DataRepository, create a DatabaseRepository".
     * <p>
     * Generics makes sure that implementationClass actually implements interfaceClass.
     */

    public <T> void bind(Class<T> interfaceClass, Class<? extends T> implementationClass) {
        bindings.put(interfaceClass, implementationClass); // Store the binding in the map
    }

    /**
     * Main method that creates and returns an instance of the requested class.
     * This method is recursive - it calls itself to resolve dependencies.
     * <p>
     * PROCESS:
     * 1. Takes a Class to create (can be interface or concrete class).
     * 2. If it's an interface, find the right implementation from bindings.
     * 3. Analyze the constructor with reflection.
     * 4. If the constructor has parameters, create these first (recursive call).
     * 5. Creates the object with all dependencies.
     */

    @SuppressWarnings("unchecked")
    // Since the cast is safe due to the way we use generics, we suppress unchecked warnings.
    public <T> T getInstance(Class<T> requestedType) {
        try {
            // STEP 1: Determine which class to instantiate.
            // If a user requests an interface, then we must find the implementation.
            Class<?> implementationClass = requestedType;
            // Check: Is it an interface, and do we have a binding for it?
            if (requestedType.isInterface() && bindings.containsKey(requestedType)) {
                // If yes, Change the interface to the concrete class from bindings.
                // For example, MessageService.class -> EmailMessageService.class
                implementationClass = bindings.get(requestedType);
            }
            // If its not an interface, or no binding exists, try to create requestedType directly.

            //STEP 2: Find the constructor with reflection.
            //getConstructors() returns ALL public constructors.
            Constructor<?>[] constructors = implementationClass.getConstructors();
            if (constructors.length == 0) {
                throw new RuntimeException("No public constructors found for " + implementationClass.getName()); // safeguard if no public constructors
            }

            // We assume that there is only one constructor.
            // Takes the first constructor (index 0).
            Constructor<?> constructor = constructors[0];

            // STEP 3: Check if the constructor has parameters.
            if (constructor.getParameterCount() == 0) {
                // If no parameters, simply create a new instance.
                return (T) constructor.newInstance();
            }

            // STEP 4: Constructor HAS parameters - we must resolve these dependencies first.
            // Get information about all parameters in the constructor.
            Parameter[] parameters = constructor.getParameters();

            // Create an array to hold the dependencies.
            Object[] dependencies = new Object[parameters.length];

            // STEP 5: Create each dependency recursively.
            for (int i = 0; i < parameters.length; i++) {
                // Get the type for each parameter.
                // Example: If EmailMessageService(DataRepository repo)
                // Then paramType = DataRepository.class
                Class<?> paramType = parameters[i].getType();

                // Recursive call!
                // We call getInstance() again to create this dependency.
                // This will continue until we reach classes without dependencies.
                dependencies[i] = getInstance(paramType);
            }

            // STEP 6: Now we have all dependencies - so we can create the object.
            // newInstance(dependencies) = Call the constructor with all the arguments.
            // Example: new EmailMessageService(DatabaseRepositoryInstance)
            return (T) constructor.newInstance(dependencies);

        } catch (Exception e) {
            throw new RuntimeException("Could not create instance of " + requestedType.getName(), e);
        }
    }

}
