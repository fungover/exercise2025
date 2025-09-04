package org.example.game.core;

import java.util.List;
import java.util.Scanner;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.game.cli.CliCombatUI;
import org.example.game.cli.CombatUI;
import org.example.game.cli.ConsoleMapPrinter;
import org.example.game.cli.ExploreParser;
import org.example.game.cli.ExploreParser.ExploreCommand;
import org.example.map.Tile;
import org.example.service.combat.CombatService;
import org.example.service.movement.MovementService;

public final class GameController {
    private final MovementService movementService = new MovementService();
    private final String[] startupLines;
    private final CombatService combatService = new CombatService();


    public GameController(String... startupLines) {
        this.startupLines = (startupLines == null) ? new String[0] : startupLines;
    }

    public void run(GameContext context, Scanner scanner) {
        // Initial render
        ConsoleMapPrinter.print(context.map(), context.player().getX(), context.player().getY());
        for (String s : startupLines) {
            if (s != null && !s.isBlank()) System.out.println(s);
        }

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();

            ExploreCommand exploreCommand = ExploreParser.parse(line);

            switch (exploreCommand.type()) {
                case QUIT -> {
                    System.out.println("Goodbye!");
                    return;
                }

                case HELP -> {
                    printHelp();
                }

                case MAP -> {
                    // Just reprint the current map view
                    ConsoleMapPrinter.print(context.map(), context.player().getX(), context.player().getY());
                }

                case UNKNOWN -> {
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
                }

                case INVENTORY -> {
                    printInventory(context.player().getInventory());
                }

                case LOOT -> {
                    listGroundItems(context);
                }

                case TAKE -> {
                    handleTake(context, exploreCommand);
                }

                case USE -> {
                    handleUse(context, exploreCommand.oneBasedIndex());
                }

                case MOVE -> {
                    var result = movementService.tryMove(context.player(), context.map(), exploreCommand.direction());
                    // Map re-render after a move attempt (so the @ position stays accurate)
                    ConsoleMapPrinter.print(context.map(), context.player().getX(), context.player().getY());

                    switch (result) {
                        case MOVED -> {
                            System.out.println("You move " + exploreCommand.direction().name().toLowerCase() + ".");

                            // Check the tile we just entered
                            var currentTile = context.map().tileAt(context.player().getX(), context.player().getY());

                            if (currentTile.hasEnemy()) {
                                var enemy = currentTile.enemy();

                                // Run combat via the UI abstraction
                                CombatUI ui = new CliCombatUI(scanner);
                                combatService.fight(context.player(), enemy, context.player().getInventory(), ui);

                                // If enemy died, clear it from the tile
                                if (enemy.isDead()) {
                                    currentTile.removeEnemy();
                                }

                                System.out.println("That was scarry, let's continue...");
                                promptLootIfPresent(context);

                            } else {
                                // No enemy here, keep the old behavior
                                promptLootIfPresent(context);
                            }
                        }

                        case BLOCKED_WALL -> System.out.println("A wall blocks your way.");
                        case BLOCKED_OUT_OF_BOUNDS -> System.out.println("You can't go further.");
                    }
                }
            }
        }
    }

    private void printHelp() {
        System.out.println("""
        Symbols:
          @ = you     # = wall     . = floor
          S = spawn   B = boss     + = door
          ! = item(s) on the ground here

        Commands:
          w/a/s/d  |  north/south/east/west Move
          m | map                           Reprint the map
          i | inventory                     Show inventory
          l | loot                          List ground items here
          take <n>  |  take all             Pick up item(s) from ground
          use <n>                           Use/equip an item from inventory
          h | help                          Show this help
          q | quit                          Exit the game
        """);
    }

    private void promptLootIfPresent(GameContext context) {
        var tile = context.map().tileAt(context.player().getX(), context.player().getY());
        if (!tile.hasItems()) return;

        System.out.println("There is loot here (!).");
        listGroundItems(context);
    }


    private void listGroundItems(GameContext context) {
        Tile tile = context.map().tileAt(context.player().getX(), context.player().getY());
        if (!tile.hasItems()) {
            System.out.println("There’s nothing on the ground.");
            return;
        }
        List<Item> ground = tile.items();
        System.out.println("== On the ground ==");
        for (int itemIndex = 0; itemIndex < ground.size(); itemIndex++) {
            Item item = ground.get(itemIndex);
            System.out.printf("%2d) %s — %s%n", itemIndex + 1, item.name(), item.description());
        }
        System.out.println("Pick up with: take <number>  or  take all");
    }

    private void handleTake(GameContext context, ExploreCommand exploreCommand) {
        Tile tile = context.map().tileAt(context.player().getX(), context.player().getY());
        Inventory inventory = context.player().getInventory();

        if (exploreCommand.takeAllFlag()) {
            if (!tile.hasItems()) {
                System.out.println("Nothing to take.");
                return;
            }
            for (Item groundItem : tile.takeAll()) {
                inventory.add(groundItem);
            }
            // Re-render to remove '!' if the tile is now empty
            ConsoleMapPrinter.print(context.map(), context.player().getX(), context.player().getY());
            System.out.println("You picked up everything.");
            return;
        }

        Integer oneBasedIndex = exploreCommand.oneBasedIndex();
        if (oneBasedIndex == null) {
            System.out.println("Usage: take <number>  or  take all");
            return;
        }

        int zeroBasedIndex = oneBasedIndex - 1;
        Item picked = tile.takeAt(zeroBasedIndex);
        if (picked == null) {
            System.out.println("No such item number.");
            return;
        }

        inventory.add(picked);
        ConsoleMapPrinter.print(context.map(), context.player().getX(), context.player().getY()); // '!' may disappear
        System.out.println("You picked up: " + picked.name());
    }

    private void handleUse(GameContext context, Integer oneBasedIndex) {
        if (oneBasedIndex == null) {
            System.out.println("Usage: use <number>  (see 'inventory' for numbers)");
            return;
        }
        Inventory inventory = context.player().getInventory();
        List<Item> items = inventory.items();
        if (oneBasedIndex < 1 || oneBasedIndex > items.size()) {
            System.out.println("No such item number.");
            return;
        }

        Item item = items.get(oneBasedIndex - 1);
        boolean used = inventory.use(item, context.player()); // equippable/consumable polymorphism
        System.out.println(used ? "Used: " + item.name() : "That item can't be used right now.");
    }

    private void printInventory(Inventory inventory) {
        System.out.println("== Inventory ==");
        System.out.println("Weapon: " + inventory.equippedWeaponName() + "   |   Armor: " + inventory.equippedArmorName());

        List<Item> items = inventory.items();
        if (items.isEmpty()) {
            System.out.println("(empty)");
            return;
        }
        for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
            Item it = items.get(itemIndex);
            System.out.printf("%2d) %s — %s%n", itemIndex + 1, it.name(), it.description());
        }
        System.out.println("Use with: use <number>");
    }
}
