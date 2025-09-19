package org.example;

import java.util.random.RandomGenerator;

public class Chance {

    private RandomGenerator generator;// = RandomGenerator.of("L64X128MixRandom");

    public Chance(RandomGenerator generator) {
        this.generator = generator;
    }

    /**
     * @param chance 0-100, specifies the chance of the method returning true
     * @return true or false
     */
    public boolean rollPercent(int chance) {
        return generator.nextDouble() * 100 < chance;
    }

    public static int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    public void setGenerator(RandomGenerator generator) {
        this.generator = generator;
    }
}