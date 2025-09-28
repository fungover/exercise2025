package org.example.di;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class SimpleDi {
  private static final Map<Class<?>, Class<?>> implementationMap = new HashMap<>();
  public static void register(Class<?> interfaceClass, Class<?> implementationClass) {
    implementationMap.put(interfaceClass, implementationClass);
  }

  private static final ScopedValue<Set<Class<?>>> constructing = ScopedValue.newInstance();

  public static <T> T resolve(Class<T> clazz) {

    Class<?> concreteClass = implementationMap.getOrDefault(clazz, clazz);

    Set<Class<?>> stack = constructing.get();
    if(stack.contains(concreteClass)) {
      try {
        throw new Exception("Class " + concreteClass.getName() + " already exists (Circular Dependency detected)");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    Constructor<?>[] constructors = concreteClass.getConstructors();
    if (constructors.length != 1) {
      throw new IllegalArgumentException(
              "Class " + concreteClass.getName() + " must have exactly one constructor"
      );
    }

    stack.add(concreteClass);

    try {
      Constructor<?> constructor = concreteClass.getConstructors()[0];

      Object[] params = Arrays.stream(constructor.getParameterTypes())
              .map(SimpleDi::resolve)
              .toArray();

      return clazz.cast(constructor.newInstance(params));
    } catch (Exception e) {
      throw new RuntimeException("Can't initialize: " + concreteClass.getName(), e);
    } finally {
      stack.remove(concreteClass);
    }
  }

  public static <T> T runWithScope(Class<T> rootClass){
    return ScopedValue.where(constructing, new HashSet<>()).call(() -> resolve(rootClass));
  }
}