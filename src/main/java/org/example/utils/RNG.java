package org.example.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static int randomInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        long bound = (long) max - (long) min + 1L;
        long r = ThreadLocalRandom.current().nextLong(bound);
        return (int) (r + min);
    }
}
