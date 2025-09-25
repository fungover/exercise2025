package org.example.utils;

import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random();

    public static int nextInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException(
              "min must be < max (exclusive upper bound). Got: min=" + min +
                ", max=" + max);
        }
        //[min,max)
        return random.nextInt(max - min) + min;
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
