package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.Item;
import org.example.map.Maps;
import org.example.utils.Funcs;
import org.example.service.CombatService;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private Player player;
    private Scanner scanner;
    private boolean running;
    private Enemy dungeonEnemy;
    private String lastActionMessage;
    private Map<String, Item> areaItems;
    private boolean forestUnlocked = false;
    private boolean mountainUnlocked = false;
    private boolean guardPaid = false;


    private static final String HOME_DESC = "🏕️  You are at your safe Home base. Rest and prepare for the journey ahead!";
    private static final String CASTLE_DESC = "🏰 Castle entrance guarded by a diamond-loving guard! 💂";

    // -- area names for each level and position --
    private String[][] areaNames = {
            { "Home", "Home", "Home" },
            { "Dungeon", "Village", "Lake" },
            { "Mountain", "Bridge", "Forest" },
            { "Castle", "Castle", "Castle" }
    };

    // -- area descriptions and actions --
    private String[][] areaDescriptions = {
            { HOME_DESC, HOME_DESC, HOME_DESC },
            { " Dark Dungeon with dangerous skeleton! Defeat it to get the golden key! 🔑",
                    "🏘️  Peaceful Village with friendly blacksmith!",
                    "💧  Healing Lake with magical waters. Restore your health here! 🩸" },
            { "⛰️  Treacherous Mountain peaks! Find precious diamond for the castle guard! 💎",
                    "🌉  Mysterious Bridge connecting the areas.",
                    "🌲  Enchanted Forest with hidden treasures. (Requires golden key to enter) ⛺" },
            { CASTLE_DESC, CASTLE_DESC, CASTLE_DESC }
    };

    // -- start at level 0, center position--
    public Game() {
        this.player = new Player(1, 0);
        this.scanner = new Scanner(System.in);
        this.running = true;
        this.dungeonEnemy = new Enemy("💀 Skeleton Guardian", 100, 20);
        this.lastActionMessage = "";
        this.areaItems = new HashMap<>();
        initializeAreaItems();
    }

    private void initializeAreaItems() {
        // Add sword to village
        areaItems.put("1,1", new Item("🗡️ Iron Sword", "weapon", 15, "Sharp sword from village blacksmith"));
        // Add diamond to mountain (locked until key is obtained)
        areaItems.put("2,0", new Item("💎 Precious Diamond", "treasure", 1, "Valuable diamond that guards love"));
        // lake with healing ability
    }

    public void start() {
        System.out.println("🎮 Welcome to Climb to Victory Quest!");
        System.out.println("Commands: up, down, left, right, attack, inventory, collect, look, give, quit");
        System.out.println();

        while (running) {
            // --show current map with player position--
            Maps.gameMap(player.getY(), player.getX());

            // show current location and description
            // String currentArea = areaNames[player.getY()][player.getX()];
            String description = areaDescriptions[player.getY()][player.getX()];

            Funcs.print("|----------------------------------------INFO----------------------------------------|");
            // --- Show last action ---------
            if (!lastActionMessage.isEmpty()) {
                System.out.println("| " + lastActionMessage);
                lastActionMessage = "";
            }

            if (!(player.getY() == 1 && player.getX() == 0)) {
                System.out.println("| " + description);
            }

            // Check if player is in dungeon with enemy
            if (player.getY() == 1 && player.getX() == 0 && dungeonEnemy.isAlive()) {
                System.out.println("| " + dungeonEnemy.getName() + " guards the Key🔑!");
                System.out.println("| 💀 Enemy HP: " + dungeonEnemy.getHealth() + "|" + renderHealthBar(dungeonEnemy.getHealth()) + "|");
            }

            // Check for items in current area
            String areaKey = player.getY() + "," + player.getX();
            if (areaItems.containsKey(areaKey)) {
                // Special case for mountain diamond
                if (areaKey.equals("2,0") && !mountainUnlocked) {
                    System.out.println("| 🔒 You see something valuable, but the area is locked!");
                } else {
                    System.out.println("| ✨ You see an item here: " + areaItems.get(areaKey).getName()
                            + " - use 'collect' to pick it up!");
                }
            }

            // Show guard status at castle
            if (player.getY() == 3 && !guardPaid) {
                System.out.println("| 💂 Castle Guard blocks your way! He wants a precious diamond!");
            }

            // Show status messages for locked areas
            if (player.getY() == 2 && player.getX() == 1) {
                if (!mountainUnlocked) {
                    System.out.println("| 🔒 Mountain is locked (Golden Key required)");
                }
                if (!forestUnlocked) {
                    System.out.println("| 🔒 Forest is locked (Golden Key required)");
                }
            }

            // -- show Player HP and inventory --
            System.out.println("| 😎 Player HP:" + player.getHealth() + "|" + renderHealthBar(player.getHealth()) + "| " + player.getInventoryDisplay());

            // check for victory
            if (player.getY() == 3 && guardPaid) {
                System.out.println("| 🏆 CONGRATULATIONS! You gave the diamond to the guard and entered the Castle!");
                System.out.println("| 👑 You are now the ruler of this realm! YOU WON! 👑");
                Funcs.print("|----------------------------------------INFO----------------------------------------|");
                Funcs.spacer(7);
                break;
            }

            // - get user input --
            Funcs.print("| 🎮 COMMANDS: up, down, left, right, attack, inventory, collect, look, give, quit   |");
            Funcs.print("|----------------------------------------INFO----------------------------------------|");
            System.out.print("|~~>: ");

            String command = scanner.nextLine().toLowerCase().trim();

            processCommand(command);
        }

        scanner.close();
    }

    private void processCommand(String command) {
        switch (command) {
            case "up":
            case "climb":
                if (isValidVerticalMove(player.getY() + 1)) {

                    if (player.getY() == 2 && (player.getY() + 1) == 3) {
                        if (!guardPaid) {
                            Item diamond = findDiamond();

                            if (diamond == null) {
                                lastActionMessage = "💂 Castle Guard: 'Halt! I need a precious diamond to let you pass!'";
                                break;
                            } else {
                                player.removeItem(diamond);
                                guardPaid = true;
                                lastActionMessage = "💂 Castle Guard: 'Ah, a beautiful diamond! You may enter the castle now!' 💎✨";
                                break;
                            }
                        }
                    }

                    player.moveDown();
                    String newArea = areaNames[player.getY()][player.getX()];
                    lastActionMessage = "🔺Climbed up to " + newArea + "!";
                } else {
                    if (player.getY() + 1 >= 4) {
                        lastActionMessage = "❌Can't climb higher! You reached the top!";
                    } else {
                        lastActionMessage = "❌Can't climb from here! You must be in the center to climb up/down!";
                    }
                }
                break;

            case "down":
            case "descend":
                if (isValidVerticalMove(player.getY() - 1)) {
                    player.moveUp();
                    String newArea = areaNames[player.getY()][player.getX()];
                    lastActionMessage = "🔻 Descended to " + newArea + "!";
                } else {

                    if (player.getY() - 1 < 0) {
                        lastActionMessage = "❌ Can't go lower! You are at the bottom!";
                    } else {
                        lastActionMessage = "❌Can't descend from here! You must be in the Village or Bridge center to climb up/down!";
                    }
                }
                break;

            case "left":
                if (isValidHorizontalMove(player.getX() - 1)) {

                    if (player.getY() == 2 && player.getX() == 1 && (player.getX() - 1) == 0) {
                        if (!mountainUnlocked && !player.hasKey()) {
                            lastActionMessage = "🔒 The Mountain is locked! You need the golden key to enter!";
                            break;
                        } else if (!mountainUnlocked && player.hasKey()) {
                            mountainUnlocked = true;
                            lastActionMessage = "🔓 You used the golden key to unlock the Mountain! ⛰️";
                            break;
                        }
                    }

                    player.moveLeft();
                    String newArea = areaNames[player.getY()][player.getX()];
                    lastActionMessage = "⬅️ Moved left to " + newArea + "!";
                } else {
                    if (player.getX() - 1 < 0) {
                        lastActionMessage = "❌ Can't go further left!";
                    } else {
                        lastActionMessage = "❌Can't move left from here! You must be in center";
                    }
                }
                break;

            case "right":
                if (isValidHorizontalMove(player.getX() + 1)) {

                    if (player.getY() == 2 && player.getX() == 1 && (player.getX() + 1) == 2) {
                        if (!forestUnlocked && !player.hasKey()) {
                            lastActionMessage = "🔒 The Forest is locked! You need the golden key to enter!";
                            break;
                        } else if (!forestUnlocked && player.hasKey()) {
                            forestUnlocked = true;
                            lastActionMessage = "🔓 You used the golden key to unlock the Forest! 🌲";
                            break;
                        }
                    }

                    player.moveRight();
                    String newArea = areaNames[player.getY()][player.getX()];
                    lastActionMessage = "➡️ Moved right to " + newArea + "!";
                } else {
                    if (player.getX() + 1 >= 3) {
                        lastActionMessage = "❌ Can't go further right!";
                    } else {
                        lastActionMessage = "❌Can't move right from here! You must be in center to move horizontally!";
                    }
                }
                break;

            case "quit":
                System.out.println("👋 Thanks for playing! Come back to continue your quest!");
                running = false;
                break;

            case "attack":
                handleCombat();
                break;

            case "inventory":
            case "inv":
                showInventory();
                break;

            case "collect":
            case "pick":
            case "take":
                collectItem();
                break;

            case "give":
            case "pay":
                giveDiamondToGuard();
                break;

            case "look":
                lookAround();
                break;

            default:
                lastActionMessage = "❓ Unknown command. Use: up, down, left, right, attack, inventory, collect, look, give, quit";
                break;
        }
    }

    private boolean isValidVerticalMove(int level) {

        if (level < 0 || level >= 4) {
            return false;
        }

        return player.getX() == 1;
    }

    private boolean isValidHorizontalMove(int position) {

        if (position < 0 || position >= 3) {
            return false;
        }

        return player.getY() == 1 || player.getY() == 2;
    }

    private void handleCombat() {
        // --Check if player is in dungeon with alive enemy--
        if (player.getY() == 1 && player.getX() == 0 && dungeonEnemy.isAlive()) {
            // Use CombatService for better combat
            String combatResult = CombatService.executeCombatRound(player, dungeonEnemy);

            if (!dungeonEnemy.isAlive()) {
                // Drop golden key when defeated
                Item goldenKey = new Item("🔑 Golden Key", "key", 1, "Opens the bridge gate");
                player.addItem(goldenKey);
                lastActionMessage = combatResult + "!";
                return;
            }

            lastActionMessage = combatResult;

            if (!player.isAlive()) {
                System.out.println("💀 GAME OVER! 💀");
                System.out.println("☠️ You have been defeated by the " + dungeonEnemy.getName() + "!");
                running = false;
            }
        } else {
            lastActionMessage = "❌ There's nothing to attack here!";
        }
    }

    private void showInventory() {
        if (player.getInventorySize() == 0) {
            lastActionMessage = "� Your inventory is empty!";
        } else {
            StringBuilder sb = new StringBuilder("🎒Inventory:\n");
            for (Item item : player.getInventory()) {
                sb.append("   - ").append(item.toString());
                sb.append("\n");
            }
            lastActionMessage = sb.toString();
        }
    }

    private void collectItem() {
        String areaKey = player.getY() + "," + player.getX();

        // ---healing-----
        if (areaKey.equals("1,2")) {
            if (player.getHealth() < 100) {
                int healAmount = 30;
                int oldHealth = player.getHealth();
                player.heal(healAmount);
                int actualHealed = player.getHealth() - oldHealth;
                lastActionMessage = "💧 You drink from the magical lake and restore " + actualHealed + " health! ✨";
            } else {
                lastActionMessage = "💧 The magical lake waters shimmer, but you're already at full health!";
            }
            return;
        }

        // -- Mountain diamond requires key to be unlocked first--
        if (areaKey.equals("2,0") && !mountainUnlocked) {
            lastActionMessage = "🔒 The Mountain is locked! Get the golden key first!";
            return;
        }

        if (areaItems.containsKey(areaKey)) {
            Item item = areaItems.get(areaKey);
            player.addItem(item);
            areaItems.remove(areaKey);
            lastActionMessage = "✨ You collected " + item.getName() + "!";
        } else {
            lastActionMessage = "❌ There's nothing to collect here!";
        }
    }

    private void giveDiamondToGuard() {
        // -- Check if player is at castle entrance
        if (player.getY() == 3) {
            Item diamond = findDiamond();

            if (diamond != null) {
                player.removeItem(diamond);
                guardPaid = true;
                lastActionMessage = "💎 You gave the diamond to the castle guard! He lets you pass! ✨";
            } else {
                lastActionMessage = "❌ You don't have a diamond to give to the guard!";
            }
        } else {
            lastActionMessage = "❌ There's no guard here to give a diamond to!";
        }
    }

    // Helper method to find diamond in inventory
    private Item findDiamond() {
        for (Item item : player.getInventory()) {
            if ("💎 Precious Diamond".equals(item.getName())) {
                return item;
            }
        }
        return null;
    }

    // Helper method to render health bar
    private String renderHealthBar(int health) {
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < health / 20; i++) {
            bar.append("🟥");
        }
        for (int i = 0; i < 5 - health / 20; i++) {
            bar.append("--");
        }
        return bar.toString();
    }

    private void lookAround() {
        String currentArea = areaNames[player.getY()][player.getX()];
        String description = areaDescriptions[player.getY()][player.getX()];

        StringBuilder sb = new StringBuilder();
        sb.append("🔍 Looking around ").append(currentArea).append(":\n");
        sb.append("   ").append(description).append("\n");

        // Check for items
        String areaKey = player.getY() + "," + player.getX();
        if (areaItems.containsKey(areaKey)) {
            sb.append("   ✨ You see: ").append(areaItems.get(areaKey).getName()).append("\n");
        }

        // Check for enemies
        if (player.getY() == 1 && player.getX() == 0 && dungeonEnemy.isAlive()) {
            sb.append("   ⚔️ Enemy: ").append(dungeonEnemy.getName()).append(" (HP: ").append(dungeonEnemy.getHealth())
                    .append(")\n");
        }

        lastActionMessage = sb.toString();
    }
}