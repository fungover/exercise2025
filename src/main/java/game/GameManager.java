package game;

/**
 * @author Jörgen Lindström
 * @version 1.0
 */

import entities.Player;
import entities.Item;
import entities.Enemy;
import items.*;
import map.PirateCave;
import service.MovementService;
import service.InventoryService;
import service.CombatService;
import enemies.EnemyFactory;
import utils.Constants;

public class GameManager {
    private PirateCave cave;
    private Player player;
    private InputHandler inputHandler;
    private MovementService movementService;
    private InventoryService inventoryService;
    private CombatService combatService;
    private boolean gameRunning;
    private CombatService.CombatResult currentCombat; // Pågående strid

    public GameManager() {

        // Ask for the user's name
        String playerName = askForPlayerName();

        cave = new PirateCave(Constants.DEFAULT_MAP_WIDTH, Constants.DEFAULT_MAP_HEIGHT);
        player = new Player("Kapten " + playerName, Constants.PLAYER_STARTING_HEALTH, Constants.PLAYER_STARTING_DAMAGE);
        inputHandler = new InputHandler();
        movementService = new MovementService();
        inventoryService = new InventoryService();
        combatService = new CombatService();
        gameRunning = true;
        currentCombat = null;

        System.out.println("📍 En skattkarta är skapad med: " + Constants.DEFAULT_MAP_WIDTH + "x" + Constants.DEFAULT_MAP_HEIGHT + " spelrutor");

        // Place objects and enemies
        placeInitialItems();
        placeInitialEnemies();
    }


    private String askForPlayerName() {
        java.util.Scanner nameScanner = new java.util.Scanner(System.in);

        System.out.println("🏴‍☠️ Välkommen till Piratgrottans Äventyr! 🏴‍☠️");
        System.out.println("═".repeat(50));
        System.out.print("Skriv ditt förnamn för att börja ditt äventyr: ");

        String firstName = nameScanner.nextLine().trim();

        // Place objects and enemies
        while (firstName.isEmpty()) {
            System.out.print("Du måste ange ett namn. Försök igen: ");
            firstName = nameScanner.nextLine().trim();
        }

        // Capitalize the first letter
        firstName = firstName.substring(0, 1).toUpperCase() +
                firstName.substring(1).toLowerCase();

        System.out.println("\nVälkommen, Kapten " + firstName + "! Ditt äventyr börjar här och nu...");

        return firstName;
    }

    private void placeInitialItems() {
        // Use PirateTreasureFactory to create items
        cave.placeItem(PirateTreasureFactory.createLargeGoldCoin(), 2, 1);
        cave.placeItem(PirateTreasureFactory.createMagicKey(), 4, 2);
        cave.placeItem(PirateTreasureFactory.createStrongRum(), 1, 3);
        cave.placeItem(PirateTreasureFactory.createJewelBox(), 3, 3);
        cave.placeItem(PirateTreasureFactory.createPirateSaber(), 4, 0);

        System.out.println("Föremål har blivit utplacerade på kartan!");
    }


    private void placeInitialEnemies() {
        // Place some specific enemies
        cave.placeEnemy(EnemyFactory.createSkeleton(), 3, 1);
        cave.placeEnemy(EnemyFactory.createSpider(), 2, 3);
        cave.placeEnemy(EnemyFactory.createPirate(), 4, 3);

        // Randomly place additional enemies
        cave.populateWithEnemies(2);
    }

    public void startGame() {
        System.out.println("\n" + "═".repeat(50));

        showPlayerInfo();
        showMap();

        System.out.println("\n🎮 Du kan nu styra din spelare och plocka upp föremål!");
        inputHandler.showHelp();

        // Start game loop
        gameLoop();

        // Close resources
        inputHandler.close();
    }


    private void gameLoop() {
        while (gameRunning) {
            String command = inputHandler.readCommand();

            // If the player is in combat, handle combat commands
            if (currentCombat != null && currentCombat.isInCombat()) {
                handleCombatCommand(command);
            } else {
                // Normal game loop
                if (inputHandler.isQuitCommand(command)) {
                    handleQuit();
                }
                else if (inputHandler.isHelpCommand(command)) {
                    inputHandler.showHelp();
                }
                else if (command.equals("pickup") || command.equals("take") || command.equals("get")) {
                    handlePickup();
                }
                else if (command.equals("inventory") || command.equals("inv")) {
                    handleInventory();
                }
                else if (command.startsWith("use ")) {
                    String itemName = command.substring(4); // Ta bort "use "
                    handleUseItem(itemName);
                }
                else if (command.equals("look") || command.equals("examine")) {
                    handleLook();
                }
                else if (command.equals("attack") || command.equals("fight")) {
                    handleAttackCommand();
                }
                else {
                    InputHandler.Direction direction = inputHandler.parseMovementCommand(command);
                    if (direction != null) {
                        handleMovement(direction);
                    } else {
                        System.out.println("Okänt kommando: '" + command + "'. Skriv 'help' för hjälp.");
                    }
                }
            }
        }
    }


    private void handleMovement(InputHandler.Direction direction) {
        MovementService.MovementResult result = movementService.movePlayer(player, cave, direction);

        System.out.println(result.getMessage());

        if (result.isSuccess()) {
            //Show updated map
            showMap();

            // Check if there are items here
            checkForItems();

            // Check if there are enemies here.
            checkForEnemies();

            // Show possible directions
            showAvailableDirections();
        }
    }


    private void checkForItems() {
        if (cave.hasItemAt(player.getX(), player.getY())) {
            Item item = cave.getItemAt(player.getX(), player.getY());
            System.out.println("✨ Du ser " + item.getName() + " här! Skriv 'pickup' för att plocka upp det.");
        }
    }


    private void checkForEnemies() {
        if (cave.hasEnemyAt(player.getX(), player.getY())) {
            Enemy enemy = cave.getEnemyAt(player.getX(), player.getY());
            System.out.println("⚔️ " + enemy.getName() + " blockerar din väg! Skriv 'attack' för att strida eller försök gå runt.");
        }
    }


    private void handleAttackCommand() {
        Enemy enemy = cave.getEnemyAt(player.getX(), player.getY());

        if (enemy == null) {
            System.out.println("Det finns ingen fiende här att attackera.");
            return;
        }

        // Start battle
        currentCombat = combatService.startCombat(player, enemy);
        System.out.println(currentCombat.getMessage());

        if (currentCombat.isInCombat()) {
            System.out.println("⚔️ STRIDKOMMANDON:");
            System.out.println("   'attack' - Attackera fienden");
            System.out.println("   'flee'   - Försök fly från striden");
        }
    }


    private void handleCombatCommand(String command) {
        if (currentCombat == null || !currentCombat.isInCombat()) {
            return;
        }

        Enemy enemy = currentCombat.getEnemy();

        if (command.equals("attack") || command.equals("fight")) {
            currentCombat = combatService.playerAttack(player, enemy);
            System.out.println(currentCombat.getMessage());

            if (currentCombat.isGameEnded()) {
                if (player.isAlive()) {
                    // Enemy died - remove from map
                    cave.removeEnemyAt(enemy.getX(), enemy.getY());
                    System.out.println("💀 " + enemy.getName() + " försvinner från kartan.");
                    currentCombat = null;
                } else {
                    // Player died - end the game
                    gameRunning = false;
                }
            } else if (!currentCombat.isInCombat()) {
                // The battle ended for another reason
                currentCombat = null;
            }
        }
        else if (command.equals("flee") || command.equals("run")) {
            currentCombat = combatService.attemptFlee(player, enemy);
            System.out.println(currentCombat.getMessage());

            if (currentCombat.isGameEnded()) {
                gameRunning = false;
            } else if (!currentCombat.isInCombat()) {
                currentCombat = null;
            }
        }
        else if (command.startsWith("use ")) {
            // Låt spelaren använda föremål under strid
            String itemName = command.substring(4);
            String result = player.useItem(itemName);
            System.out.println(result);
            System.out.println("Fienden väntar medan du använder ditt föremål...");
        }
        else if (inputHandler.isHelpCommand(command)) {
            System.out.println("⚔️ STRIDKOMMANDON:");
            System.out.println("   'attack' - Attackera fienden");
            System.out.println("   'flee'   - Försök fly från striden");
            System.out.println("   'use [föremål]' - Använd föremål (t.ex. 'use rom')");
        }
        else {
            System.out.println("Ogiltigt stridskomando. Skriv 'help' för hjälp.");
        }
    }

    private void handlePickup() {
        Item item = cave.removeItemAt(player.getX(), player.getY());

        if (item == null) {
            System.out.println("❌ Det finns inget att plocka upp här.");
            return;
        }

        // Try adding to inventory with InventoryService
        InventoryService.InventoryResult result = inventoryService.addItem(player, item);

        if (result.isSuccess()) {
            System.out.println("Du plockar upp " + item.getName() + "!");
            System.out.println(item.getDescription());

            // Show updated map (the item is now gone)
            showMap();
        } else {
            // Inventory full - put the item back
            cave.placeItem(item, player.getX(), player.getY());
            System.out.println(result.getMessage());
            System.out.println("Föremålet lämnades kvar på marken.");
        }
    }

    private void handleInventory() {
        String inventoryDisplay = inventoryService.getInventoryDisplay(player);
        System.out.println("\n" + inventoryDisplay);
    }


    private void handleUseItem(String itemName) {
        String result = player.useItem(itemName);
        System.out.println("\n" + result);
    }

    /**
     *  Handles "look" command to examine the area
     */
    private void handleLook() {
        System.out.println("\nDu undersöker området...");

        // Beskrivning av nuvarande plats
        int displayX = player.getX() * 2 + 1;
        int displayY = player.getY() * 2 + 1;
        var tile = cave.getTile(displayX, displayY);

        if (tile != null) {
            System.out.println("📍 " + tile.getFullDescription());
        }

        // List nearby objects
        listNearbyItems();
    }


    private void listNearbyItems() {
        System.out.println("\n👀 Föremål i närheten:");
        boolean foundAny = false;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int checkX = player.getX() + dx;
                int checkY = player.getY() + dy;

                if (cave.hasItemAt(checkX, checkY)) {
                    Item item = cave.getItemAt(checkX, checkY);
                    String direction = getDirectionDescription(dx, dy);
                    System.out.println("  " + direction + ": " + item.getName());
                    foundAny = true;
                }
            }
        }

        if (!foundAny) {
            System.out.println("  Inga föremål i närheten.");
        }
    }

    /**
     * Converts dx, dy to direction description
     */
    private String getDirectionDescription(int dx, int dy) {
        if (dx == 0 && dy == 0) return "Här";
        if (dx == 0 && dy == -1) return "Norr";
        if (dx == 0 && dy == 1) return "Söder";
        if (dx == -1 && dy == 0) return "Väster";
        if (dx == 1 && dy == 0) return "Öster";
        if (dx == -1 && dy == -1) return "Nordväst";
        if (dx == 1 && dy == -1) return "Nordöst";
        if (dx == -1 && dy == 1) return "Sydväst";
        if (dx == 1 && dy == 1) return "Sydöst";
        return "Okänd riktning";
    }


    private void showAvailableDirections() {
        var directions = movementService.getAvailableDirections(player, cave);

        if (directions.isEmpty()) {
            System.out.println("Tyvärr! Du är instängd!");
        } else {
            System.out.print("Du kan gå: ");
            for (int i = 0; i < directions.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(directions.get(i).name().toLowerCase());
            }
            System.out.println();
        }
    }

    /**
     * Handles game termination
     */
    private void handleQuit() {
        System.out.println("👋 Tack för att du spelade! På återseende, " + player.getName() + "!");
        gameRunning = false;
    }

    public void showPlayerInfo() {
        System.out.println("\n👤 === SPELARINFORMATION ===");
        System.out.println(player.getDescription());
        System.out.println("Position: (" + player.getX() + ", " + player.getY() + ")");
    }

    public void showMap() {
        cave.displayMap(player);

        System.out.println("\n📋 Kartförklaring:");
        System.out.println("   @ = Du (spelaren)");
        System.out.println("   $ = Guldmynt, ♦ = Magisk nyckel, ! = Rom");
        System.out.println("   ☆ = Piratskatt, † = Svärd");
        System.out.println("   S = Skelett, s = Spindel, P = Pirat, B = Fladdermus");
        System.out.println("   x = Besegrad fiende");
        System.out.println("   ┌─┬─┐ = Rutnätlinjer");
        System.out.println("       = Tomma spelrutor");
    }

    public Player getPlayer() {
        return player;
    }

    public PirateCave getCave() {
        return cave;
    }
}