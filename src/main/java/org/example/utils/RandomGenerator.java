package org.example.utils;

import org.example.entities.Dragon;
import org.example.entities.Enemy;
import org.example.entities.Goblin;
import org.example.entities.Troll;

public class RandomGenerator {

    public int generateNumber(int minSize, int maxSize) {
        return (int) (Math.random() * (maxSize-minSize+1)) + minSize;
    }

    public Enemy randomEnemy(RandomGenerator rand) {
        int randInt = rand.generateNumber(1, 10);
        if  (randInt < 4) {
            return new Goblin();
        } else if (randInt < 7) {
            return new Troll();
        } else {
            return new Dragon();
        }
    }
}
