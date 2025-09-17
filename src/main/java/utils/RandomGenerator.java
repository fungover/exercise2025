package utils;

public class RandomGenerator {

    public static boolean rollPercent(int chance) {
        return Math.random() * 100 < chance;
    }
}
