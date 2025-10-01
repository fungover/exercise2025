package org.example.container;

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

	private static Map<Class<?>, Class<?>> interfaceMap = new HashMap<Class<?>, Class<?>>();

	static {
		interfaceMap.put(GamingSetup.class, CreateGamingSetup.class);
		interfaceMap.put(Builder.class, PCBuilder.class);
		interfaceMap.put(BuildComputer.class, BuildGamingPC.class);
	}

	public static <T> T resolve(Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("Class can not be null");
		}

		try {
			if (clazz.isInterface()) {
				clazz = (Class<T>) interfaceMap.get(clazz);
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Constructor<?> constructor = clazz.getConstructors()[0];

			Class<?>[] paramTypes = constructor.getParameterTypes();
			Object[] params = Arrays.stream(paramTypes)
							.map(Container::resolve)
							.toArray();

			return clazz.cast(constructor.newInstance(params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
