package org.game.utils;

import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random();

    public static boolean chance(int percent) {
        return random.nextInt(100) < percent;
    }

    public static int randomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

}
