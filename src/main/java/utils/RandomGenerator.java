package utils;

public class RandomGenerator {

    public static boolean rollPercent(int chance) {
        return Math.random() * 100 < chance;
    }

    public static int randomInt(int min, int max) {
        return min + (int)(Math.random() * (max - min));
    }
}
