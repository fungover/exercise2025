package org.example.utils;

import java.util.Random;
import static org.example.utils.Guard.notNull;

public final class Rng {
    private Rng() {}

    /** Inclusive random integer in [min, max]. If max < min, returns min. */
    public static int between(Random random, int inclusiveMin, int inclusiveMax) {
        notNull(random, "random");
        if (inclusiveMax < inclusiveMin) return inclusiveMin;
        long bound  = (long) inclusiveMax + 1L;
        long range  = bound - (long) inclusiveMin;

        if (range <= Integer.MAX_VALUE) {
            return inclusiveMin + random.nextInt((int) range);
        }
        // Wide range: use long-backed API to avoid overflow.
        return (int) random.longs(1L, inclusiveMin, bound).findFirst().orElse(inclusiveMin);
    }
}
