package org.example.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random rand = new Random();

    /**
     Returns true with the given probability in [0.0, 1.0].
     @throws IllegalArgumentException if probability is NaN or outside [0,1]
     **/
    public static boolean chance(double probability) {
        if (Double.isNaN(probability) || probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("probability must be in [0.0, 1.0]");
        }
        return rand.nextDouble() < probability;
    }

    // For future implementation of random placement of items and enemies
    public static int randomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("min must be < max");
        }
        return rand.nextInt(max - min) + min;
    }
}