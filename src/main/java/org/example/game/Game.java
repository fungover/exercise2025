package org.example.game;

import org.example.entities.Item;
import org.example.entities.Manifesto;
import org.example.entities.Player;
import org.example.map.FarmageddonMap;
import org.example.service.MapService;
import org.example.service.CombatService;
import org.example.service.MovementService;
import org.example.service.InventoryService;

import java.util.Optional;

public class Game {
    private Player player;
    private final FarmageddonMap map;
    private final MovementService movementService;
    private final CombatService combatService;
    private final InventoryService inventoryService;
    private final MapService mapService;


    public Game() {
        map = new FarmageddonMap(10, 10);
        movementService = new MovementService();
        combatService = new CombatService();
        inventoryService = new InventoryService();
        mapService = new MapService();
    }

    public void start() {
        var console = System.console();
        if (console == null) {
            System.err.println("Console not available. Try running from terminal.");
            return;
        }

        System.out.println("WELCOME TO FARMAGEDDON");
        System.out.print("👤 What's your name, brave farmer? ");
        String name = console.readLine().trim();

        if (name.isEmpty()) {
            name = "Farmer Doe";
        }

        player = new Player(name, 100, 0, 0);

        System.out.println("Welcome, " + name + "! The animals have sensed your arrival...");
        System.out.println("You wake up in the middle of a chaotic barn where the animals have gone rogue.");
        System.out.println("Your goal: survive, explore, and uncover the mystery behind the madness.");
        System.out.println("To win you have to find and use the Manifesto of the Farmageddon.");
        System.out.println();
        System.out.println("HOW TO PLAY:");
        System.out.println("- The game is turn-based. You type one command per turn.");
        System.out.println("- You start at position (0, 0) on the map.");
        System.out.println("- Explore the farm, fight enemies, and collect useful items.");
        System.out.println();
        System.out.println("AVAILABLE COMMANDS:");
        System.out.println("• move north / move south / move east / move west → Move in that direction");
        System.out.println("• look → Describe your current tile (wall, enemy, item, etc.)");
        System.out.println("• attack → Attack an enemy if one is present");
        System.out.println("• inventory → View your current items");
        System.out.println("• use [item name] → Use an item from your inventory or use Manifesto to win");
        System.out.println("• health → Check your health status");
        System.out.println("• help → Show this help menu again");
        System.out.println();
        System.out.println("TIPS:");
        System.out.println("- Walls block movement. Try another direction.");
        System.out.println("- Enemies can hurt you. Be strategic with your attacks.");
        System.out.println("- Items may heal you or boost your abilities.");
        System.out.println("- You can only see one tile at a time. Use 'look' often.");
        System.out.println();
        System.out.println("Good luck, brave farmer. The animals are waiting...");
        System.out.println("--------------------------------------------------");


        while (player.isAlive()) {
            String input = console.readLine("> ").trim().toLowerCase();
            handleCommand(input);
        }

        System.out.println("Oh no! You died! Game over. Better luck next time " + name + "!");
    }

    private void useItem(String itemName) {
        Optional<Item> optionalItem = player.getInventory().stream()
                .filter(i -> i.getName().equalsIgnoreCase(itemName.trim()))
                .findFirst();

        optionalItem.ifPresentOrElse(item -> {
            item.use(player);
            player.removeItem(item);

            //win the and exit the game
            if (item instanceof Manifesto manifesto) {
                System.out.println("\n" + manifesto.getWinMessage());
                System.out.println("Congratulations, " + player.getName() + "! You win!");
                exit(0);
            }
        }, () -> System.out.println("You don't have an item called '" + itemName + "'."));
    }

    protected void exit(int code) {
        System.exit(code);
    }

    private void handleCommand(String input) {

        if (input.startsWith("use ")) {
            String itemName = input.substring(4).trim();
            useItem(itemName);
            return;
        }

        switch (input) {
            case "move north" -> movementService.move(player, map, 0, -1);
            case "move south" -> movementService.move(player, map, 0, 1);
            case "move east"  -> movementService.move(player, map, 1, 0);
            case "move west"  -> movementService.move(player, map, -1, 0);
            case "look" -> mapService.look(player, map);
            case "inventory" -> inventoryService.showInventory(player);
            case "attack" -> combatService.attack(player, map);
            case "health" -> System.out.println("Health: " + player.getHealth() + "/" + player.getMaxHealth());
            default -> System.out.println("Unknown command. Try again");
        }
    }
}
