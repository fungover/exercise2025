package org.example.di.container;

public interface Container {
    <A> void bind(Class<A> interfaceType, Class<? extends A> implType);
    <T> void bindInstance(Class<T> type, T instance);
    <T> T get(Class<T> type);
}