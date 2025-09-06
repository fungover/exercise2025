package org.example.utils;

import java.util.Objects;

public final class Guard {
    private Guard() {}

    /** Null-check that returns the same value if OK (bra för method-chaining). */
    public static <T> T notNull(T value, String name) {
        return Objects.requireNonNull(value, name + " must not be null");
    }

    /** Null + blank-check for strings */
    public static String notBlank(String value, String name) {
        Objects.requireNonNull(value, name + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
