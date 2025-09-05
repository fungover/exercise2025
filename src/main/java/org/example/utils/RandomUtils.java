package org.example.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
    public static int getRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than or equal to max");
        }

        return (int) ThreadLocalRandom.current().nextLong(min, (long) max + 1);
    }
}
