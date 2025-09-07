//import game.GameEngine;

/**
 * Huvudklass för Dungeon Crawler-spelet
 * Startar spelapplikationen
 *
 * @author [Ditt namn]
 * @version 1.0
 */
public class Main {

    /**
     * Programmets startpunkt
     * @param args kommandoradsargument (används ej för närvarande)
     */
    public static void main(String[] args) {
        try {
            // Skapa och starta spelmotorn
            //GameEngine game = new GameEngine();
            //game.startGame();

        } catch (Exception e) {
            System.err.println("❌ Ett oväntat fel uppstod:");
            System.err.println(e.getMessage());
            e.printStackTrace();

            System.out.println("\n🔧 Spelet avslutades på grund av ett tekniskt problem.");
            System.out.println("Försök starta om spelet för att börja på nytt.");
        }
    }
}
