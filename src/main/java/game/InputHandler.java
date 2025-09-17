package game;

import java.util.Scanner;

/**
 * InputHandler hanterar spelarens kommandon
 */
public class InputHandler {
    private Scanner scanner;

    public enum Direction {
        NORTH(0, -1, "norrut"),
        SOUTH(0, 1, "söderut"),
        EAST(1, 0, "österut"),
        WEST(-1, 0, "västerut");

        private final int deltaX;
        private final int deltaY;
        private final String description;

        Direction(int deltaX, int deltaY, String description) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.description = description;
        }

        public int getDeltaX() { return deltaX; }
        public int getDeltaY() { return deltaY; }
        public String getDescription() { return description; }
    }

    public InputHandler() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.print("\n> ");
        return scanner.nextLine().trim().toLowerCase();
    }

    public boolean isQuitCommand(String command) {
        return command.equals("quit") || command.equals("exit") || command.equals("q");
    }

    public boolean isHelpCommand(String command) {
        return command.equals("help") || command.equals("h") || command.equals("?");
    }


    public Direction parseMovementCommand(String command) {
        switch (command) {
            case "north":
            case "n":
            case "upp":
                return Direction.NORTH;
            case "south":
            case "s":
            case "ner":
                return Direction.SOUTH;
            case "east":
            case "e":
            case "höger":
                return Direction.EAST;
            case "west":
            case "w":
            case "vänster":
                return Direction.WEST;
            default:
                return null;
        }
    }

    public void showHelp() {
        System.out.println("\n🎯 === KOMMANDON ===");
        System.out.println("📍 Rörelse:");
        System.out.println("   north/n/upp    - Gå norrut");
        System.out.println("   south/s/ner    - Gå söderut");
        System.out.println("   east/e/höger   - Gå österut");
        System.out.println("   west/w/vänster - Gå västerut");

        System.out.println("\n📦 Föremål:");
        System.out.println("   pickup/take/get - Plocka upp föremål");
        System.out.println("   inventory/inv   - Visa ditt inventarium");
        System.out.println("   use [föremål]   - Använd ett föremål");
        System.out.println("                     Exempel: 'use rom', 'use svärd'");

        System.out.println("\n⚔️ Strid:");
        System.out.println("   attack/fight    - Attackera en fiende");
        System.out.println("   flee/run        - Fly från strid");

        System.out.println("\n🔍 Information:");
        System.out.println("   look/examine    - Undersök området");

        System.out.println("\n⚙️ System:");
        System.out.println("   help/h/?        - Visa denna hjälp");
        System.out.println("   quit/exit/q     - Avsluta spelet");

        System.out.println("\n💡 Tips:");
        System.out.println("   - Drick rom för att läka dig!");
        System.out.println("   - Använd svärd för att öka din skada!");
        System.out.println("   - Magiska nycklar öppnar speciella lås!");
        System.out.println("   - Undersök piratskatter för att se deras värde!");
    }

    public void close() {
        scanner.close();
    }
}