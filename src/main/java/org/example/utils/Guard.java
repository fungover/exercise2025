package org.example.utils;

public final class Guard {
    private Guard() {}
    public static <T> T notNull(T value, String name) {
        return java.util.Objects.requireNonNull(value, name + " must not be null");
    }
}
