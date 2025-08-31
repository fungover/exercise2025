package org.example.utils;

import java.util.Random;

public class RandomUtils {
    public static int getRandomNumber(int minDamage, int maxDamage) {
        Random random = new Random();
        return random.nextInt(maxDamage - minDamage + 1) + minDamage;
    }
}
