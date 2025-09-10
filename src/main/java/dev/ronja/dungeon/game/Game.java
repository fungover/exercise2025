package dev.ronja.dungeon.game;

import dev.ronja.dungeon.entities.*;
import dev.ronja.dungeon.map.Position;

import java.util.Random;
import java.util.Scanner;

/**
 * The {@code Game} class represents the main game loop for my dungeon crawler
 **/
public class Game {
    private final Scanner in = new Scanner(System.in);
    private final Player player;
    private Enemy enemy;
    private final Random rng = new Random();
    private final dev.ronja.dungeon.map.Dungeon dungeon = new dev.ronja.dungeon.map.Dungeon(20, 10);
    private Position playerPos;
    private Position enemyPos;
    private final dev.ronja.dungeon.service.MovementService mover = new dev.ronja.dungeon.service.MovementService(dungeon);

    /**
     * Creates a new {@code Game} instance.
     * <p>
     * A default player named "Ronja" is created when the game starts.
     * </p>
     **/
    public Game() {
        this.player = new Player("Ronja");
        this.enemy = null;
        this.playerPos = dungeon.anyOpenFloor();
        this.enemyPos = dungeon.anyFloor();
    }

    /**
     * Runs the main loop of the game
     **/
    public void run() {
        System.out.println("Welcome to the Dungeon of mysteries, " + player.getName() + " ! ");
        printHelp();

        while (player.isAlive()) {
            System.out.print("> ");
            String line;
            try {
                line = in.nextLine();
            } catch (java.util.NoSuchElementException e) {
                System.out.println("Goodbye!");
                break;
            }
            line = line.trim().toLowerCase();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0];

            switch (cmd) {

                // Visual grid
                case "map" -> printMap();

                // Movement
                case "w" -> movePlayer(0, -1);
                case "s" -> movePlayer(0, 1);
                case "a" -> movePlayer(-1, 0);
                case "d" -> movePlayer(1, 0);

                // Info
                case "help" -> printHelp();
                case "status" -> printStatus();
                case "who" -> showEnemy();

                // Healers
                case "potion" -> {
                    int amount = parseAmount(parts, 30);
                    Healer h = new Potion(amount);
                    h.heal(player);
                    printStatus();
                }
                case "mother" -> {
                    int amount = parseAmount(parts, 20);
                    Healer h = new Mother(amount);
                    h.heal(player);
                    printStatus();
                }

                // Enemies rendered
                case "spawn" -> {
                    if (parts.length < 2) {
                        enemy = spawnRandomEnemy();
                    } else {
                        String nameArg = String.join(" ",
                                java.util.Arrays.copyOfRange(parts, 1, parts.length));
                        enemy = spawnByName(nameArg);
                    }
                    showEnemy();
                }
                case "next" -> {
                    enemy = spawnRandomEnemy();
                    showEnemy();
                }

                // Combat between player and enemy
                case "attack" -> {
                    attack();
                }

                case "damage" -> {
                    player.takeDamage(10);
                    printStatus();
                }
                case "heal" -> {
                    player.heal(5);
                    printStatus();
                }
                case "quit" -> {
                    System.out.println("Goodbye ");
                    return;
                }

                default -> System.out.println("Unknown command. Try: 'status', 'attack' or 'help'! ");
            }
        }
        System.out.println("GAME OVER! You will now be buried in the darkest depths of the dungeon ");
    }

    private void attack() {
        ensureEnemy();

        if (!player.isAlive()) {
            System.out.println("You are too wounded for a combat... ");
            return;

        }
        // Ensure weapon, fallback if null
        var weapon = player.getWeapon();
        int strike = (weapon != null) ? weapon.getDamage() : 1;
        String weaponName = (weapon != null) ? weapon.getName() : "bare hands";

        // Ensure enemy + position close to player
        if (enemy == null) {
            System.out.println("No enemy to attack ");
            return;
        }

        if (enemyPos == null) {
            System.out.println("Enemy position unknown, Try 'next' or 'spawn' again. ");
            return;
        }

        // Enemy + player NOT on the same position
        if (!playerPos.equals(enemyPos)) {
            System.out.println("Too far away. Move closer to the enemy to attack! ");
            printMap();
            return;
        }

        // Players hit on the enemy
        enemy.takeDamage(strike);
        System.out.println(player.getName() + " strikes with " + weaponName + " for " + strike + " damage.");

        if (!enemy.isAlive()) {
            System.out.println("You defeated the " + enemy.getName() + " use 'next or 'spawn to continue ");
            enemy = null;
            printStatus();
            printMap();
            return;
        }

        enemy.attack(player);
        if (!player.isAlive()) {
            System.out.println("☠️ You got defeated by the " + enemy.getName() + "...");
            return;
        }
        printStatus();
    }


    // Helpers, a list of cmd`s
    private void printHelp() {
        System.out.println("""
                Commands:
                map - Show dungeon
                w/a/s/d - Move (up, left, down, right)
                next / spawn <type|random> - Enemies
                attack - Combat
                status - Show player + enemy health status
                who - Show your current enemy
                potion / mother - Heals
                help - Show the commands of this Game
                quit - QUIT the Game
                """);
    }

    private void printStatus() {
        System.out.println(player + (enemy == null ? "" : " | Enemy: " + enemy));
    }

    private void showEnemy() {
        if (enemy == null) {
            System.out.println("No enemy present. Use command: 'next' or 'spawn' ");
        } else {
            System.out.println("You face: " + enemy);
        }
    }

    private void ensureEnemy() {
        if (enemy == null) {
            enemy = spawnRandomEnemy();
            System.out.println("A wild " + enemy.getName() + " appears! ");
        }
    }

    private Enemy spawnRandomEnemy() {
        Enemy e = switch (rng.nextInt(3)) {
            case 0 -> new Siren();
            case 1 -> new MotherInLaw();
            default -> new DarkWitch();
        };
        placeEnemyOnMap();
        printMap();
        return e;
    }

    private Enemy spawnByName(String name) {
        String n = name.toLowerCase();
        switch (n) {
            case "siren" -> enemy = new Siren();
            case "mother in law", "mother-in-law", "motherinlaw", "inlaw" -> enemy = new MotherInLaw();
            case "dark witch", "darkwitch", "witch" -> enemy = new DarkWitch();
            case "random" -> {
                return spawnRandomEnemy();
            }
            default -> {
                System.out.println("Unknown enemy type. Spawning random! ");
                return spawnRandomEnemy();
            }
        }
        placeEnemyOnMap();
        printMap();
        return enemy;
    }

    private void placeEnemyOnMap() {
        for (int tries = 0; tries < 500; tries++) {
            Position p = dungeon.anyFloor();
            if (p.equals(playerPos)) continue;
            if (!isReachable(playerPos, p)) continue;
            if (!hasOpenNeighborPos(p)) continue;
            enemyPos = p;
            return;
        }

        /** Fallback
         * placing enemy close or ON to the player **/
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            Position cand = playerPos.add(d[0], d[1]);
            if (dungeon.isWalkable(cand)) {
                enemyPos = cand;
                return;
            }
        }
        enemyPos = playerPos;
    }

    private boolean hasOpenNeighborPos(Position p) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            if (dungeon.isWalkable(p.add(d[0], d[1]))) return true;
        }
        return false;
    }


    private boolean isReachable(Position start, Position goal) {
        java.util.ArrayDeque<Position> q = new java.util.ArrayDeque<>();
        java.util.HashSet<Position> seen = new java.util.HashSet<>();
        q.add(start);
        seen.add(start);

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!q.isEmpty()) {
            var cur = q.poll();
            for (int[] d : dirs) {
                var nxt = cur.add(d[0], d[1]);

                if (!dungeon.isWalkable(nxt)) continue;
                if (!seen.add(nxt)) continue;
                if (nxt.equals(goal)) return true;
                q.add(nxt);
            }
        }
        return false;
    }

    private static int parseAmount(String[] parts, int fallback) {
        if (parts.length < 2) return fallback;
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Not a number. Using default: " + fallback);
            return fallback;
        }
    }

    private void printMap() {
        Position ep = (enemy == null) ? null : enemyPos;
        System.out.println(dungeon.render(playerPos, ep));
    }

    private void movePlayer(int dx, int dy) {
        if (!dungeon.hasOpenNeighbor(playerPos)) {
            System.out.println("You are trapped. Passage opens for you!");
            dungeon.carveExitAround(playerPos);
        }
        var next = mover.tryMove(playerPos, dx, dy);
        if (playerPos.equals(next)) {
            System.out.println("You bumped into a wall!");
        } else {
            playerPos = next;
        }
        if (enemy != null && playerPos.equals(enemyPos)) {
            System.out.println("You encounter " + enemy.getName() + "!");
            enemyPos = playerPos;
            attack();
        }
        printMap();
    }
}

