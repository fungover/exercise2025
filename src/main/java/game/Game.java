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
import service.CombatService;
import service.MovementService;

import java.util.Scanner;

public class Game {
    private Player player;
    private Dungeon dungeon;
    private final InputParser parser;
    private boolean isRunning = true;

    public Game() {
        this.parser = new InputParser();
    }

    public void run() {
        Printer.printWelcome();
        Scanner sc = new Scanner(System.in);

        // --- Start sequence: ask for player name ---
        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = "Hero";

        // --- Initialize player and map ---
        this.player = new Player(name, 30, 0, 0);
        Rng rng = new Rng(); // random seed
        this.dungeon = MapGenerator.createInitialWorld(10, 10, player, rng);

        // Show help once at the start
        Printer.printHelp();

        while (isRunning) {
            // --- 1) Show short status + tile info ---
            Printer.printStatus(player);
            Printer.printTileInfo(dungeon, player.getX(), player.getY());

            // --- 2) Read command ---
            System.out.print("> ");
            String input = sc.nextLine();
            Command cmd = parser.parse(input);

            // --- 3) Execute command ---
            handleCommand(cmd);

            // --- 6) Enemies take their turn (simple AI) ---
            enemyTurns();

            // --- 7) Check win/lose conditions ---
            checkEndConditions();
        }

        sc.close();
    }

    private void handleCommand(Command cmd) {
        switch (cmd.action()) {
            case "move":
                MovementService.move(player, dungeon, cmd.arg());
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
                Printer.info("Game exited.");
                break;
            default:
                Printer.error("Unknown command.");
        }
    }

    // --- Attack ---
    private void handleAttack() {
        Tile t = dungeon.getTile(player.getX(), player.getY());
        CombatService.playerAttack(player, t);
    }

    // --- Use item ---
    private void handleUse(String arg) {
        if (arg == null || arg.isEmpty()) {
            Printer.error("Specify which item to use.");
            return;
        }
        try {
            int idx = Integer.parseInt(arg);
            service.InventoryService.useItemByIndex(player, idx);
            return;
        } catch (NumberFormatException ignored) {
            // not a number -> try name
        }
        service.InventoryService.useItemByName(player, arg);
    }

    // --- Enemy turns ---
    private void enemyTurns() {
        for (int y = 0; y < dungeon.getHeight(); y++) {
            for (int x = 0; x < dungeon.getWidth(); x++) {
                Tile t = dungeon.getTile(x, y);
                Enemy e = t.getEnemy();
                if (e != null && e.isAlive() &&
                        e.getX() == player.getX() && e.getY() == player.getY()) {
                    CombatService.enemyAttack(e, player);
                }
            }
        }
    }

    // --- Check win/lose ---
    private void checkEndConditions() {
        if (!player.isAlive()) {
            Printer.info("You died! Game Over.");
            isRunning = false;
            return;
        }
        Tile t = dungeon.getTile(player.getX(), player.getY());
        if (TileType.EXIT.equals(t.getType())) {
            Printer.info("You found the exit! You win!");
            isRunning = false;
        }
    }
}
