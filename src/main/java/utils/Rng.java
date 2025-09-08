package utils;

import java.util.Random;

public class Rng {
    private final Random random;

    public Rng() {
        this.random = new Random();
    }

    public Rng(long seed) {
        this.random = new Random(seed);
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public boolean chance(int percent) {
        if (percent <= 0) return false;
        if (percent >= 100) return true;
        return random.nextInt(100) < percent;
    }
}
