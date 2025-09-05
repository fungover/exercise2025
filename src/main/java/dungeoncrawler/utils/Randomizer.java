package dungeoncrawler.utils;

public class Randomizer {
    public Randomizer() {}
    public static int randomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
