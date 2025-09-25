package org.example.utils;

import java.util.List;
import java.util.Random;

public class Rng {
    private final Random random;

    public Rng() {
        this.random = new Random();
        System.out.println("Random generator initialized");
    }

    public Rng(long seed) {
        this.random = new Random(seed);
        System.out.println("Random generator initialized with seed: " + seed);
    }

    public int nextInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Invalid range: min (" + min + ") must be <= max (" + max + ")");
        }
        int result = random.nextInt(max - min + 1) + min;
        System.out.println("Generated random number: " + result);
        return result;
    }

    public <T> T nextElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Cannot select random element from empty list");
        }
        int index = random.nextInt(list.size());
        T result = list.get(index);
        System.out.println("Select random element: " + result + " from list: " + list.size());
        return result;
    }

    public <T> T nextElement(T[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Cannot select random element from empty array");
        }
        int index = random.nextInt(array.length);
        T result = array[index];
        System.out.println("Select random element: " + result + " from array of length " + array.length);
        return result;
    }
}
