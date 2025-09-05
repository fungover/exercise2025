package org.example.utils;

import java.util.Random;

public final class Rng {
    private Rng() {}

    /** Inclusive random integer in [min, max]. If max < min, returns min (safe on tiny ranges). */
    public static int between(Random random, int inclusiveMin, int inclusiveMax) {
        if (inclusiveMax < inclusiveMin) return inclusiveMin;
        return inclusiveMin + random.nextInt(inclusiveMax - inclusiveMin + 1);
    }
}
