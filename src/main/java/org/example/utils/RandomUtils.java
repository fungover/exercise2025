package org.example.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random rand = new Random();

    public static boolean chance(double probability) {
        return rand.nextDouble() < probability;
    }

    // For future implementation of random placement of items and enemies
    public static int randomInt(int min, int max) {
        return rand.nextInt(max - min) + min;
    }
}