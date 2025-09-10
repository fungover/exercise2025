package org.example;

import java.util.Random;
import java.util.random.RandomGenerator;

import static org.example.Enemy.*;


public class Testable {
    private final RandomGenerator random;

    public Testable(){
        random = new Random();
    }

    public Testable(RandomGenerator random) {
        this.random = random;
    }

    public Enemy createRandomEnemyAt(int x, int y) {
        int roll = random.nextInt(2);
        return switch (roll) {
            case 0 -> new Dragon();
            case 1 -> new Spider();
            default -> throw new IllegalStateException();
        };
    }
}

sealed interface Enemy permits Dragon, Spider {
    record Dragon() implements Enemy {
    }

    record Spider() implements Enemy {
    }
}