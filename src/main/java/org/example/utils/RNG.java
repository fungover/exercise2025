package org.example.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static int randomInt (int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean randomBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    public static <T> T randomCoice(T[] array) {
        int index = randomInt(0, array.length -1);
        return array[index];
    }
}
