// Game.java
package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.service.CombatService;
import org.SpinalGlitter.exercise2.utils.CommandUtils;
import org.SpinalGlitter.exercise2.utils.RandomGeneration;

import java.util.*;
import java.util.random.RandomGenerator;

import static org.SpinalGlitter.exercise2.service.PickupService.tryPickup;

public final class Game {
    private final Player player;
    private final DungeonMap map;
    private final int enemies;
    private final int potions = 5;
    private final RandomGeneration random;
    private final Scanner scanner = new Scanner(System.in);

    public Game(String name, String difficulty) {
        this.player = new Player(name);
        switch (difficulty) {
            case "medium":
                this.map = new DungeonMap(20, 20);
                this.enemies = 10;
                break;
            case "hard":
                this.map = new DungeonMap(20, 20);
                this.enemies = 20;
                break;
            default: // easy
                this.map = new DungeonMap(10, 10);
                this.enemies = 4;
                break;
        }
        this.random = new RandomGeneration(this.map);
    }

    public void startGame() {

        Player player = this.player;
        DungeonMap map = this.map;
        Random rng = new Random();

        // Giving items to inventory
        player.getInventory().addItem(new Potion(null));
        player.getInventory().addItem(new Potion(null));
        player.getInventory().printItems();


        Set<Position> occupied = new HashSet<>();
        occupied.add(player.getPosition());

        //Place potions and enemies and add to occupied
        Map<Position, Potion> potions = random.placePotions(/*map,*/ this.potions, player.getPosition(), rng);
        occupied.addAll(potions.keySet());
        Map<Position, Enemy> enemies = random.placeEnemies(/*map,*/ this.enemies, player.getPosition(), rng);
        occupied.addAll(enemies.keySet());

        //Place sword and add to occupied
        Map<Position, Sword> swords = random.placeSwords(/*map,*/ 1, player.getPosition(), rng, occupied);

        // add walls where no items are placed
        random.placeWalls(/*map,*/ occupied, 4, player.getPosition(), rng);

        // Logic to handle combat
        CombatService combatService = new CombatService(enemies, scanner);

        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("Welcome " + player.getName() + "!");
        System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
        CommandUtils.printHelp();

        while (player.isAlive()) {
            map.printMap(player.getPosition(), potions, enemies, swords);

            // add damage if the player has a weapon else remove extra damage
            if (player.haveWeapon()) {
                player.setDamage();

            } else if (player.getDamage() == 20 && !player.haveWeapon()) {
                player.setDamage();
            }

            System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP." + " " + player.getDamage() + "DMG");
            System.out.print("> ");
            String input = CommandUtils.normalize(scanner.nextLine().trim().toLowerCase());

            Position newPos = null;
            switch (input) {
                case "options" -> CommandUtils.printHelp();
                case "quit" -> {
                    System.out.println("Exiting the game. Goodbye!");
                    return;
                }
                case "north" -> newPos = player.getPosition().getAdjacent(0, 1);
                case "south" -> newPos = player.getPosition().getAdjacent(0, -1);
                case "east" -> newPos = player.getPosition().getAdjacent(1, 0);
                case "west" -> newPos = player.getPosition().getAdjacent(-1, 0);
                case "inventory" -> player.getInventory().printItems();
                case "heal" -> player.heal(20);
                case "throw" -> player.getInventory().removeWeapon();
                default -> System.out.println("Unknown command. Type 'help' for a list of commands.");
            }

            if (newPos == null) continue;

            // 3) don't allow player to walk in to an enemy or wall.
            if (!map.canMoveTo(newPos)) {
                System.out.println("You hit a wall!");
                continue;
            }
            if (enemies.containsKey(newPos)) {
                Enemy e = enemies.get(newPos);

                System.out.println("An enemy blocks your path: " + e.getName() + " at " + newPos + ".");

                // enter combat with either skeleton or goblin
                combatService.startCombat(player, e);
            }

            // 4) move player
            int dx = newPos.x() - player.getPosition().x();
            int dy = newPos.y() - player.getPosition().y();
            player.move(dx, dy);

            tryPickup(potions, player.getPosition(), player);
            tryPickup(swords, player.getPosition(), player);
        }
    }
}

/*package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.*;
        import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.service.CombatService;
import org.SpinalGlitter.exercise2.utils.CommandUtils;
import org.SpinalGlitter.exercise2.utils.RandomGeneration;

import java.util.*;

        import static org.SpinalGlitter.exercise2.service.PickupService.tryPickup;

public final class Game {
    private final Player player;
    private final DungeonMap map;
    private final int enemies;
    private final int potions = 5;
    private final Scanner scanner = new Scanner(System.in);
    private final Random rng = new Random();
    private final RandomGeneration generator;

    // TODO :Denna är ändrad, ser ju lovande ut :)
    public Game(String name, String difficulty) {
        this.player = new Player(name);

        switch (difficulty) {
            case "medium" -> {
                this.map = new DungeonMap(20, 20);
                this.enemies = 10;
            }
            case "hard" -> {
                this.map = new DungeonMap(20, 20);
                this.enemies = 20;
            }
            default -> { // easy
                this.map = new DungeonMap(10, 10);
                this.enemies = 4;
            }
        }

        this.generator = new RandomGeneration(this.map, this.rng);
    }

    public void startGame() {
        Player player = this.player;

        // Give starter items
        player.getInventory().addItem(new Potion(null));
        player.getInventory().addItem(new Potion(null));
        player.getInventory().printItems();

        Set<Position> occupied = new HashSet<>();
        occupied.add(player.getPosition());

        // Place potions and enemies
        Map<Position, Potion> potions = generator.placePotions(this.potions, player.getPosition(), occupied);

        Map<Position, Enemy> enemies = generator.placeEnemies(this.enemies, player.getPosition(), occupied);


        // Place sword
        Map<Position, Sword> swords = generator.placeSwords(1, player.getPosition(), occupied);

        // Place walls
        System.out.println("Enemies and potions placed..." + enemies.keySet());
        generator.placeWalls(4, player.getPosition(), occupied);

        // Combat service
        CombatService combatService = new CombatService(enemies, scanner);

        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("Welcome " + player.getName() + "!");
        System.out.println("You are at " + player.getPosition() +
                " with health " + player.getCurrentHealth() + "HP.");
        CommandUtils.printHelp();

        while (player.isAlive()) {
            map.printMap(player.getPosition(), potions, enemies, swords);

            // update damage based on weapon
            player.setDamage();

            System.out.println("You are at " + player.getPosition() +
                    " with health " + player.getCurrentHealth() + "HP. " +
                    player.getDamage() + "DMG");
            System.out.print("> ");
            String input = CommandUtils.normalize(scanner.nextLine().trim().toLowerCase());

            Position newPos = null;
            switch (input) {
                case "options" -> CommandUtils.printHelp();
                case "quit" -> {
                    System.out.println("Exiting the game. Goodbye!");
                    return;
                }
                case "north" -> newPos = player.getPosition().getAdjacent(0, 1);
                case "south" -> newPos = player.getPosition().getAdjacent(0, -1);
                case "east" -> newPos = player.getPosition().getAdjacent(1, 0);
                case "west" -> newPos = player.getPosition().getAdjacent(-1, 0);
                case "inventory" -> player.getInventory().printItems();
                case "heal" -> player.heal(20);
                case "throw" -> player.getInventory().removeWeapon();
                default -> System.out.println("Unknown command. Type 'help' for a list of commands.");
            }

            if (newPos == null) continue;

            // can't walk into wall
            if (!map.canMoveTo(newPos)) {
                System.out.println("You hit a wall!");
                continue;
            }

            // combat check
            if (enemies.containsKey(newPos)) {
                Enemy e = enemies.get(newPos);
                System.out.println("An enemy blocks your path: " + e.getName() + " at " + newPos + ".");
                combatService.startCombat(player, e);
            }

            // move player
            int dx = newPos.x() - player.getPosition().x();
            int dy = newPos.y() - player.getPosition().y();
            player.move(dx, dy);

            // pickups
            tryPickup(potions, player.getPosition(), player);
            tryPickup(swords, player.getPosition(), player);
        }
    }
}*/
