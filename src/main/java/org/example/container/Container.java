package org.example.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final Map<Class<?>, Class<?>> bindings = new HashMap<>();

    public Map<Class<?>, Class<?>> getBindings() {
        return bindings;
    }

    public <T> T create(Class<?> clazz) throws Exception {
        Class<?> impl = bindings.getOrDefault(clazz, clazz);

        // get constructor
        Constructor<?> constructor = impl.getDeclaredConstructors()[0];
        constructor.setAccessible(true);

        // get parameters from constructor
        Class<?>[] paramTypes = constructor.getParameterTypes();
        System.out.println(paramTypes);
        Object[] dependencies = new Object[paramTypes.length];
        System.out.println(paramTypes.length + " hej");

        for (int i = 0; i < paramTypes.length; i++) {
            dependencies[i] = create(paramTypes[i]);
        }


        return (T) constructor.newInstance(dependencies);
    }

    public <T> void bind(Class<T> base, Class<? extends T> impl) {
        bindings.put(base, impl);
    }

}

// TODO: Handle contructor injection
// TODO: Här är en lista på punkter som behöver täckas för Part 2 – A Minimal DI Container:
// TODO: Skapa en container-klass som kan hantera objektinstansiering. Done
//TODO: En metod som returnerar en instans av en given klass (Class<T>). Done
//TODO: Hämta konstruktorn för den begärda klassen. Done
//TODO: Identifiera om konstruktorn har parametrar (beroenden). Done
//TODO: Skapa parametrarna rekursivt genom att anropa containerns metod igen.
//TODO: Bygga instansen av klassen med dess beroenden via konstruktorn.
//TODO: Returnera den färdiga instansen till anroparen.
//TODO: Hantera endast en konstruktor per klass (som föreskrivs i uppgiften).
//TODO: Automatiskt bygga hela dependency graphen när man ber om en top-level klass.
//TODO: (Valfritt men bra)
//TODO: Möjlighet att mappa interfaces till konkreta implementationer, så att containern vet vilken klass som ska skapas när ett interface efterfrågas.