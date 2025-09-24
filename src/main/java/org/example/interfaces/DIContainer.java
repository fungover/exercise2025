package org.example.interfaces;

public interface DIContainer {
    <T> void register(Class<T> interfaceType, Class <? extends T> implementationType);

    <T> T resolve(Class<T> interfaceType);
}
