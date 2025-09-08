package utils;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * FakeRng: deterministic stub for unit tests.
 * Preload integers and booleans to control results.
 */
public class FakeRng extends Rng {
    private final Deque<Integer> ints = new ArrayDeque<>();
    private final Deque<Boolean> bools = new ArrayDeque<>();

    public FakeRng() {
        super(123L); // fallback seed (not actually used by overrides)
    }

    /** Queue integer values to be consumed by nextInt(bound). */
    public FakeRng pushInts(int... values) {
        for (int v : values) ints.add(v);
        return this;
    }

    /** Queue boolean values to be consumed by chance(percent). */
    public FakeRng pushBools(boolean... values) {
        for (boolean b : values) bools.add(b);
        return this;
    }

    @Override
    public int nextInt(int bound) {
        if (ints.isEmpty()) return 0;              // safe default
        int v = Math.abs(ints.removeFirst());
        return bound <= 0 ? 0 : (v % bound);
    }

    @Override
    public boolean chance(int percent) {
        if (!bools.isEmpty()) return bools.removeFirst();
        return false; // safe default
    }
}
