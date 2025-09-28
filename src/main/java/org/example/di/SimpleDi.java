package org.example.di;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleDi {
  private static final ScopedValue<Set<Class<?>>> constructing = ScopedValue.newInstance();
  /// must have exactly one constructor
  public static <T> T resolve(Class<T> clazz) {
    Set<Class<?>> stack = constructing.get();
    if(stack.contains(clazz)) {
      try {
        throw new Exception("Class " + clazz.getName() + " already exists");
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    Constructor<?>[] constructors = clazz.getConstructors();
    if (constructors.length != 1) {
      throw new IllegalArgumentException(
              "Class " + clazz.getName() + " must have exactly one constructor"
      );
    }

    stack.add(clazz);

    try {
      Constructor<?> constructor = clazz.getConstructors()[0];
      Object[] params = Arrays.stream(constructor.getParameterTypes())
              .map(SimpleDi::resolve)
              .toArray();
      return clazz.cast(constructor.newInstance(params));
    } catch (Exception e) {
      throw new RuntimeException("Can't initialize: " + clazz.getName(), e);
    } finally {
      stack.remove(clazz);
    }
  }

  public static <T> T runWithScope(Class<T> rootClass){
    return ScopedValue.where(constructing, new HashSet<>()).call(() -> resolve(rootClass));
  }
}
