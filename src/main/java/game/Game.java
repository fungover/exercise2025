package game;

import entities.Player;
import entities.Enemy;
import map.Dungeon;
import map.Tile;
import map.TileType;
import service.MapGenerator;
import utils.InputParser;
import utils.InputParser.Command;
import utils.Printer;
import utils.Rng;

import java.util.Scanner;

public class Game {
    private final Player player;
    private final Dungeon dungeon;
    private final InputParser parser;
    private boolean isRunning = true;

    public Game() {
        // Initiera spelare, dungeon, helpers
        this.player = new Player("Hero", 30, 0, 0);
        Rng rng = new Rng(); // slumpmässig seed
        this.dungeon = MapGenerator.createInitialWorld(10, 10, player, rng);
        this.parser = new InputParser();
    }

    public void run() {
        Printer.printWelcome();
        Scanner sc = new Scanner(System.in);

        while (isRunning) {
            // --- 1) Visa kort status ---
            Printer.printStatus(player);
            Printer.printTileInfo(dungeon, player.getX(), player.getY());

            // --- 2) Läs kommando ---
            System.out.print("> ");
            String input = sc.nextLine();
            Command cmd = parser.parse(input);

            // --- 3) Kör handling ---
            handleCommand(cmd);

            // --- 6) Fiendernas tur (enkel AI) ---
            enemyTurns();

            // --- 7) Kontrollera vinst/förlust ---
            checkEndConditions();
        }

        sc.close();
    }

    private void handleCommand(Command cmd) {
        switch (cmd.action()) {
            case "move":
                handleMove(cmd.arg());
                break;
            case "attack":
                handleAttack();
                break;
            case "use":
                handleUse(cmd.arg());
                break;
            case "inventory":
                Printer.printInventory(player);
                break;
            case "look":
                Printer.printMiniMap(dungeon, player, 2);
                break;
            case "help":
                Printer.printHelp();
                break;
            case "quit":
                isRunning = false;
                Printer.info("Spelet avslutas...");
                break;
            default:
                Printer.error("Okänt kommando.");
        }
    }

    // --- Rörelse ---
    private void handleMove(String dir) {
        int dx = 0, dy = 0;
        switch (dir) {
            case "north": dy = -1; break;
            case "south": dy =  1; break;
            case "east":  dx =  1; break;
            case "west":  dx = -1; break;
            default:
                Printer.error("Ogiltig riktning.");
                return;
        }
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        if (dungeon.isWalkable(newX, newY)) {
            player.move(dx, dy);
            Tile t = dungeon.getTile(newX, newY);
            // --- 4) Plocka upp item ---
            if (TileType.ITEM.equals(t.getType()) && t.getItem() != null) {
                player.addItem(t.pickUpItem());
                Printer.info("Du plockade upp ett föremål!");
            }
        } else {
            Printer.error("Du kan inte gå dit.");
        }
    }

    // --- Attack ---
    private void handleAttack() {
        Tile t = dungeon.getTile(player.getX(), player.getY());
        if (t.getEnemy() != null && t.getEnemy().isAlive()) {
            player.attack(t.getEnemy());
            Printer.info("Du attackerar " + t.getEnemy().getType() + " (HP kvar: " + t.getEnemy().getHealth() + ")");
            if (!t.getEnemy().isAlive()) {
                Printer.info("Fienden är besegrad!");
                t.removeEnemy();
            }
        } else {
            Printer.error("Ingen fiende att attackera här.");
        }
    }

    // --- Use item ---
    private void handleUse(String arg) {
        if (arg == null || arg.isEmpty()) {
            Printer.error("Ange vilket item du vill använda.");
            return;
        }
        try {
            int idx = Integer.parseInt(arg);
            if (idx >= 0 && idx < player.getInventory().size()) {
                player.use(player.getInventory().get(idx));
                player.removeItem(player.getInventory().get(idx));
                Printer.info("Du använde ett item från inventariet.");
                return;
            }
        } catch (NumberFormatException ignored) {
            // Om arg inte är ett nummer, försök matcha namn
        }

        // Matcha namn
        for (entities.Item item : player.getInventory()) {
            if (item.getName().toLowerCase().contains(arg.toLowerCase())) {
                player.use(item);
                player.removeItem(item);
                Printer.info("Du använde " + item.getName());
                return;
            }
        }
        Printer.error("Item hittades inte: " + arg);
    }

    // --- Fiendernas tur ---
    private void enemyTurns() {
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                Tile t = dungeon.getTile(x, y);
                Enemy e = t.getEnemy();
                if (e != null && e.isAlive()) {
                    e.takeTurn(player); // Enkel AI: attack om på samma ruta
                }
            }
        }
    }

    // --- Kontrollera game over eller vinst ---
    private void checkEndConditions() {
        if (!player.isAlive()) {
            Printer.info("Du dog! Game Over.");
            isRunning = false;
            return;
        }
        Tile t = dungeon.getTile(player.getX(), player.getY());
        if (TileType.EXIT.equals(t.getType())) {
            Printer.info("Du hittade utgången! Du vann!");
            isRunning = false;
        }
    }
}
