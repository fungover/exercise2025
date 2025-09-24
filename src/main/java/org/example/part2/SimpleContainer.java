package org.example.part2;

public class SimpleContainer {

    public <T> T getInstance(Class<T> clazz) {
        System.out.println("Trying to create instance of: " + clazz.getName());
        return null; // Temporary
    }
}
