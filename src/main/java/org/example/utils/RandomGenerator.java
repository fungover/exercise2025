package org.example.utils;

import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random();

    public static int nextInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
