package org.example.di;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimpleDi {
  private static final ThreadLocal<Set<Class<?>>> constructing = ThreadLocal.withInitial(HashSet::new); {
  }
  /// must have exactly one constructor
  public static <T> T resolve(Class<T> clazz) throws Exception {
    if(constructing.get().contains(clazz)) {
      throw new Exception("Class " + clazz.getName() + " already exists");
    }

    Constructor<?>[] constructors = clazz.getConstructors();
    if (constructors.length != 1) {
      throw new IllegalArgumentException(
              "Class " + clazz.getName() + " must have exactly one constructor"
      );
    }

    constructing.get().add(clazz);

    try {
      Constructor<?> constructor = clazz.getConstructors()[0];
      Class<?>[] paramTypes = constructor.getParameterTypes();
      Object[] params = Arrays.stream(paramTypes)
              .map(type -> {
                try {
                  return resolve(type);
                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              })
              .toArray();
      return clazz.cast(constructor.newInstance(params));
    } catch (Exception e) {
      throw new RuntimeException("Can't initialize: " + clazz.getName(), e);
    } finally {
      constructing.get().remove(clazz);
    }
  }
}
