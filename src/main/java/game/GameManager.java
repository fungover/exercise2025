package game;

import entities.Player;
import entities.Item;
import items.*;
import map.PirateCave;
import service.MovementService;
import service.InventoryService;

/**
 * GameManager är "chefen" som styr hela spelet
 *
 * @author Jörgen Lindström
 * @version 1.0
 */
public class GameManager {
    private PirateCave cave;
    private Player player;
    private InputHandler inputHandler;
    private MovementService movementService;
    private InventoryService inventoryService; // Här är den saknade variabeln!
    private boolean gameRunning;

    public GameManager() {
        cave = new PirateCave(5, 4);
        player = new Player("Kapten Morgan", 100, 15); // namn, maxHealth, damage
        inputHandler = new InputHandler();
        movementService = new MovementService();
        inventoryService = new InventoryService(); // Initialisera inventoryService
        gameRunning = true;

        System.out.println("🎮 GameManager startad!");
        System.out.println("📍 Piratgrotta skapad: 5x4 spelrutor med komplett rutnät");

        // Placera ut några föremål för testning
        placeInitialItems();
    }

    /**
     * Placerar ut några föremål på kartan för att testa systemet
     */
    private void placeInitialItems() {
        // Använd PirateTreasureFactory för att skapa föremål
        cave.placeItem(PirateTreasureFactory.createLargeGoldCoin(), 2, 1);
        cave.placeItem(PirateTreasureFactory.createMagicKey(), 4, 2);
        cave.placeItem(PirateTreasureFactory.createStrongRum(), 1, 3);
        cave.placeItem(PirateTreasureFactory.createJewelBox(), 3, 3);
        cave.placeItem(PirateTreasureFactory.createPirateSaber(), 4, 0);

        System.out.println("💎 Föremål utplacerade på kartan!");
    }

    public void startGame() {
        System.out.println("\n🏴‍☠️ Välkommen till Piratgrottans Äventyr! 🏴‍☠️");
        System.out.println("═".repeat(50));

        showPlayerInfo();
        showMap();

        System.out.println("\n🎮 Du kan nu styra din spelare och plocka upp föremål!");
        inputHandler.showHelp();

        // Starta spelloop
        gameLoop();

        // Stäng resurser
        inputHandler.close();
    }

    /**
     * Huvudspelloop - här händer magin!
     */
    private void gameLoop() {
        while (gameRunning) {
            String command = inputHandler.readCommand();

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
            else {
                InputHandler.Direction direction = inputHandler.parseMovementCommand(command);
                if (direction != null) {
                    handleMovement(direction);
                } else {
                    System.out.println("❓ Okänt kommando: '" + command + "'. Skriv 'help' för hjälp.");
                }
            }
        }
    }

    /**
     * Hanterar rörelse med hjälp av MovementService
     */
    private void handleMovement(InputHandler.Direction direction) {
        MovementService.MovementResult result = movementService.movePlayer(player, cave, direction);

        System.out.println(result.getMessage());

        if (result.isSuccess()) {
            // Visa uppdaterad karta
            showMap();

            // Kolla om det finns föremål här
            checkForItems();

            // Visa möjliga riktningar
            showAvailableDirections();
        }
    }

    /**
     * Kontrollerar om det finns föremål på spelarens position
     */
    private void checkForItems() {
        if (cave.hasItemAt(player.getX(), player.getY())) {
            Item item = cave.getItemAt(player.getX(), player.getY());
            System.out.println("✨ Du ser " + item.getName() + " här! Skriv 'pickup' för att plocka upp det.");
        }
    }

    /**
     * Hanterar upplockning av föremål
     */
    private void handlePickup() {
        Item item = cave.removeItemAt(player.getX(), player.getY());

        if (item == null) {
            System.out.println("❌ Det finns inget att plocka upp här.");
            return;
        }

        // Försök lägga till i inventory med InventoryService
        InventoryService.InventoryResult result = inventoryService.addItem(player, item);

        if (result.isSuccess()) {
            System.out.println("✅ Du plockar upp " + item.getName() + "!");
            System.out.println("📦 " + item.getDescription());

            // Visa uppdaterad karta (föremålet är nu borta)
            showMap();
        } else {
            // Inventory fullt - lägg tillbaka föremålet
            cave.placeItem(item, player.getX(), player.getY());
            System.out.println(result.getMessage());
            System.out.println("Föremålet lämnades kvar på marken.");
        }
    }

    /**
     * Hanterar inventory-kommando
     */
    private void handleInventory() {
        String inventoryDisplay = inventoryService.getInventoryDisplay(player);
        System.out.println("\n" + inventoryDisplay);
    }

    /**
     * Hanterar användning av föremål
     */
    private void handleUseItem(String itemName) {
        String result = player.useItem(itemName);
        System.out.println("\n" + result);
    }

    /**
     * Hanterar "look" kommando för att undersöka området
     */
    private void handleLook() {
        System.out.println("\n🔍 Du undersöker området...");

        // Beskrivning av nuvarande plats
        int displayX = player.getX() * 2 + 1;
        int displayY = player.getY() * 2 + 1;
        var tile = cave.getTile(displayX, displayY);

        if (tile != null) {
            System.out.println("📍 " + tile.getFullDescription());
        }

        // Lista föremål i närheten
        listNearbyItems();
    }

    /**
     * Listar föremål i närliggande rutor
     */
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
     * Konverterar dx, dy till riktningsbeskrivning
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

    /**
     * Visar vilka riktningar spelaren kan gå
     */
    private void showAvailableDirections() {
        var directions = movementService.getAvailableDirections(player, cave);

        if (directions.isEmpty()) {
            System.out.println("🚫 Du är instängd!");
        } else {
            System.out.print("🧭 Du kan gå: ");
            for (int i = 0; i < directions.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(directions.get(i).name().toLowerCase());
            }
            System.out.println();
        }
    }

    /**
     * Hanterar avslutning av spelet
     */
    private void handleQuit() {
        System.out.println("👋 Tack för att du spelade! Farväl, " + player.getName() + "!");
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