package game;

import entities.Enemy;
import entities.Item;
import entities.Player;
import entities.Potion;
import map.Dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Dungeon dungeon = new Dungeon(10, 10);
    Player player = new Player(1, 1, 100, 50);
    List<Enemy> enemyList = new ArrayList<Enemy>();
    Scanner scanner = new Scanner(System.in);

    public Game() {

        mapSetup();

    }

    public void run() {
        while (true) {
            // Below method shows the map and all placements for testing purposes and visualisation.
            // dungeon.printDungeon(player, enemyList);
            System.out.println("\n*** Welcome to this Dungeon Crawler! ***\n " +
                    "Your are trapped i a dark cave where you can´t see anything.\n " +
                    "Your goal is to find you way to the south side to escape.\n" +
                    "You might have to fight some enemies to get there. But I´ve heard there is some\n" +
                    "potions laying around to heal your wounds if you need to. Good luck!\n");

            System.out.println("Move (W: go up, S: go down, A: go left, D: go right, Q: quit): ");
            String input = scanner.nextLine();

            if (input.equals("q")) break;

            handleInput(input);

        }
    }

    public void mapSetup() {
        Potion potion = new Potion("Potion", 50);
        dungeon.placeItem(4, 5, potion);
        dungeon.placeItem(5, 3, potion);
        dungeon.placeItem(5, 3, potion);
        dungeon.placeItem(7, 2, potion);
        dungeon.placeItem(5, 4, potion);
        dungeon.placeItem(4, 8, potion);

        enemyList.add(new Enemy(5, 5, 100, 30));
        enemyList.add(new Enemy(2, 4, 100, 30));
        enemyList.add(new Enemy(4, 2, 100, 30));
        enemyList.add(new Enemy(5, 3, 100, 30));
        enemyList.add(new Enemy(8, 7, 100, 30));
        enemyList.add(new Enemy(5, 8, 100, 30));
        enemyList.add(new Enemy(2, 8, 100, 50));
    }

    public void checkForEnemy() {
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            Enemy enemy = enemyList.get(i);

            if (player.getX() == enemy.getX() && player.getY() == enemy.getY()) {
                startBattle(enemy);

            }
        }
    }

    public void checkForItem() {
        Item item = dungeon.getItemAt(player.getX(), player.getY());
        if (item != null) {
            System.out.println("You found a " + item.getName() + "!");
            item.use(player);
            dungeon.removeItemAt(player.getX(), player.getY());
            scanner.nextLine();
            System.out.println("press ENTER to continue...");
        }
    }

    public void checkForWin() {
        if (player.getY() >= dungeon.getHeight() - 2 && dungeon.isWalkable(player.getX(), player.getY())) {
            System.out.println(" *** Yay! You managed to find your way through the dungeon and win the game! ***");

        }
    }

    public void startBattle(Enemy enemy) {
        System.out.println("Oh no. You have encountered an enemy! Lets fight!");
        System.out.println("press ENTER to continue...");
        scanner.nextLine();
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            System.out.println("You attack the enemy...");
            enemy.setHealth(enemy.getHealth() - player.getDamage());
            System.out.println("Enemy now has " + enemy.getHealth() + " hp");

            if (enemy.getHealth() <= 0)
                break;

            System.out.println("Press ENTER for the enemies round");
            scanner.nextLine();
            System.out.println("Enemy attacks you!");
            player.setHealth(player.getHealth() - enemy.getDamage());
            System.out.println("You now have " + player.getHealth() + " hp");

            if (enemy.getHealth() <= 0)
                break;

            System.out.println("Press ENTER for your round...");
            scanner.nextLine();
        }
        if (player.getHealth() >= 0 && enemy.getHealth() <= 0) {
            System.out.println("You killed the enemy! Lets continue through the dungeon.");
            enemyList.remove(enemy);
        } else if (enemy.getHealth() >= 0 && player.getHealth() <= 0) {
            System.out.println("Oh no! You died. Better luck next time. THE END!");
            scanner.nextLine();
            System.exit(0);
        }
    }

    public void handleInput(String input) {
        switch (input) {
            case "w":
                player.move(0, -1, dungeon);
                break;
            case "s":
                player.move(0, 1, dungeon);
                break;
            case "a":
                player.move(-1, 0, dungeon);
                break;
            case "d":
                player.move(1, 0, dungeon);
                break;
            default:
                System.out.println("No valid command!");
                break;
        }
        checkForItem();
        checkForEnemy();
        checkForWin();

    }

}
