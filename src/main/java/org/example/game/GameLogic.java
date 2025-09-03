package org.example.game;

import org.example.entities.characters.Player;
import org.example.entities.enemies.Goblin;
import org.example.entities.enemies.GoblinKing;
import org.example.entities.items.Item;
import org.example.map.DungeonGrid;
import org.example.map.Tile;
import org.example.service.*;
import org.example.utils.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogic {
    private static final Logger LOGGER = Logger.getLogger(GameLogic.class.getName());

    private final InputService inputService = new InputService();
    private final ConfigService configService = new ConfigService();
    private final SpawnService spawnService = new SpawnService();
    private final MovementService movementService = new MovementService();
    private final GameState state = new GameState();

    public void initializeGame() {
        System.out.println("=== Welcome to the dungeon!===");
        Utils.newRow();

        System.out.println("Please enter a name for your player");
        state.name = inputService.readLine();
        System.out.println("Your name is " + state.name);

        Utils.newRow();
        chooseDifficulty();

        Utils.newRow();
        chooseMapSize();

        Utils.newRow();
        startGame();
    }

    private void chooseDifficulty() {
        boolean validChoice = false;
        System.out.println("Please choose a difficulty level:");

        do {
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");

            try {
                String input = inputService.readLine();

                if (!input.equals("1")  && !input.equals("2")  && !input.equals("3") ) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                int choice = Integer.parseInt(input);
                DifficultyConfig config = configService.chooseDifficulty(choice);

                if (config == null) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                state.difficulty = config.difficulty;
                state.health = config.health;
                state.maxHealth = config.maxHealth;
                state.weapon = config.startingWeapon;
                state.potion = config.startingPotion;

                validChoice = true;
                System.out.println("You have chosen the " + state.difficulty + " difficulty.");

            } catch (NumberFormatException ex) {
                LOGGER.log(Level.SEVERE, "Error choosing a difficulty", ex);
            }

        } while (!validChoice);
    }

    private void chooseMapSize() {
        boolean validChoice = false;
        System.out.println("Please choose a map size:");

        do {
            System.out.println("1. Small (10x10)");
            System.out.println("2. Medium (15x15)");
            System.out.println("3. Large (20x20)");

            try {
                String input = inputService.readLine();

                if (!input.equals("1")  && !input.equals("2")  && !input.equals("3") ) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                int choice = Integer.parseInt(input);
                DungeonGrid grid = configService.chooseMapSize(choice);

                if (grid == null) {
                    System.out.println("Invalid input. Please try again.");
                    continue;
                }

                state.grid = grid;
                validChoice = true;
                System.out.println("You have chosen a " + grid.getWidth() + "x" + grid.getHeight() + " map.");

            } catch (NumberFormatException ex) {
                LOGGER.log(Level.SEVERE, "Error choosing a map", ex);
            }

        } while (!validChoice);
    }

    public void startGame() {
        state.grid = DungeonGrid.createDungeonGrid(state.grid.getWidth(), state.grid.getHeight());
        int[] optimalStartPosition = state.grid.getOptimalStartPosition();

        spawnService.spawnEnemies(optimalStartPosition, state.grid, 10, () -> new Goblin());
        spawnService.spawnEnemies(optimalStartPosition, state.grid, 1, () -> new GoblinKing());
        spawnService.spawnWeapons(optimalStartPosition, state.grid, 10);
        spawnService.spawnPotions(optimalStartPosition, state.grid, 10);

        Player player = new Player(state.name, state.health, state.maxHealth,
                optimalStartPosition[0], optimalStartPosition[1], state.weapon);

        player.getInventory().addItem((state.potion));

        while (!state.gameOver) {
            movementService.availableMoves(player, state.grid);
            String input = inputService.readLine();

            switch (input.toUpperCase()) {
                case "N":
                    state.gameOver = movementService.movePlayer(player, state.grid, 0, -1);
                    break;
                case "E":
                    state.gameOver = movementService.movePlayer(player, state.grid, 1, 0);
                    break;
                case "S":
                    state.gameOver = movementService.movePlayer(player, state.grid, 0, 1);
                    break;
                case "W":
                    state.gameOver = movementService.movePlayer(player, state.grid, -1, 0);
                    break;
                case "I":
                ItemService itemService = new ItemService();
                itemService.openInventoryMenu(player, inputService);
                break;

                case "T":
                    //Print out the equipped weapon and it's damage
                    System.out.println(player.getEquippedWeapon().getName() + "Damage " + player.getDamage());
                    break;

                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }

            int x = player.getX();
            int y = player.getY();
            Tile currentTile = state.grid.getTiles()[x][y];

            if (currentTile.getEnemy() != null) {
                CombatService combatService = new CombatService();
                System.out.println("Boo, there is an enemy here");
                combatService.battleEnemy(player, currentTile.getEnemy());

                if (player.getHealth() <= 0) {
                    System.out.println("You have been defeated!");
                    state.gameOver = true;
                }

                if (currentTile.getEnemy().getHealth() <= 0) {
                    currentTile.removeEnemy();
                }
            }

            if (currentTile.getItems().size() > 0) {
                ItemService itemService = new ItemService();
                itemService.interactWithItems(player, currentTile.getItems(), inputService);
                currentTile.getItems().clear();
            }

        }

        endGame();
    }

    public void endGame() {
        System.out.println("=== Game Over ===");
    }
}
