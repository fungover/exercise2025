package utils;

public class RandomGenerator {

    /**
     * Kastar en tärning med specificerat antal sidor
     */
    public static int rollDice(int sides) {
        return (int)(Math.random() * sides) + 1;
    }

    /**
     * Procentuell chans - returnerar true om slumptal < chans
     */
    public static boolean rollPercent(int chance) {
        return Math.random() * 100 < chance;
    }

    /**
     * Slumpmässigt heltal mellan min och max (inklusive)
     */
    public static int randomBetween(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    /**
     * Väljer slumpmässigt element från array
     */
    public static <T> T randomChoice(T[] array) {
        if (array.length == 0) return null;
        return array[randomBetween(0, array.length - 1)];
    }

    /**
     * Slumpmässig boolean (50/50 chans)
     */
    public static boolean coinFlip() {
        return Math.random() < 0.5;
    }
}
