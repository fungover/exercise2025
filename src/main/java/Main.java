import game.GameManager;

/**
 * @author Jörgen Lindström
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("🚀 Startar Piratgrotta-spelet...");

        GameManager game = new GameManager();
        game.startGame();
    }
}