package org.example.container;

import jakarta.inject.Inject;
import org.example.InjectionPoint;
import org.example.computer.CreateGamingSetup;
import org.example.computer.GamingSetup;
import org.example.computer.builders.BuildGamingPC;
import org.example.computer.builders.Builder;
import org.example.computer.builders.BuildComputer;
import org.example.computer.builders.PCBuilder;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Container {

	private static final Map<Class<?>, Class<?>> interfaceMap = new HashMap<>();

	static {
		interfaceMap.put(GamingSetup.class, CreateGamingSetup.class);
		interfaceMap.put(Builder.class, PCBuilder.class);
		interfaceMap.put(BuildComputer.class, BuildGamingPC.class);
	}

	public static <T> T resolve(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("Class can not be null");
		}

		if (clazz.isInterface()) {
			Class<?> impl = interfaceMap.get(clazz);
			if (impl == null) {
				throw new IllegalArgumentException("No implementation for " + clazz);
			}
			clazz = (Class<T>) impl;
		}

		try {
			Constructor<?> constructor = getConstructor(clazz);

			Class<?>[] paramTypes = constructor.getParameterTypes();
			Object[] params = Arrays.stream(paramTypes)
							.map(Container::resolve)
							.toArray();

			return clazz.cast(constructor.newInstance(params));
		} catch  (Exception e) {
			throw new RuntimeException("Failed to resolve " + clazz, e);
		}

	}

	private static Constructor<?> getConstructor(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getConstructors();
		Constructor<?> constructor = null;
		if (constructors.length == 0) {
			throw new IllegalArgumentException("No constructor for " + clazz);
		}
		if  (constructors.length == 1) {
			return constructors[0];
		}
		for (Constructor<?> ctor : constructors) {
			if (ctor.isAnnotationPresent(Inject.class)) {
				return ctor;
			}
		}
		for (Constructor<?> ctor : constructors) {
			if (ctor.getParameterCount() == 0) {
				return ctor;
			}
		}
		return constructors[0];
	}
}
