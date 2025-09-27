package org.example.di;

import java.lang.reflect.Constructor;

public class DiContainer {
  public <T> T getInstance(Class<T> t) throws Exception {

    Constructor<?> constructor = t.getDeclaredConstructors()[0];
    constructor.setAccessible(true);

    Class<?>[] params = constructor.getParameterTypes();

    Object[] dependencies = new Object[params.length];
    for (int i = 0; i < params.length; i++) {
      dependencies[i] = getInstance(params[i]);
    }

    return (T) constructor.newInstance(dependencies);

  }
}
