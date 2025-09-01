package org.example.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RNG {

    public static int randomInt (int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

}
