package game;

import entities.Enemy;
import entities.Item;
import entities.Player;
import entities.Potion;
import map.Dungeon;

import java.awt.datatransfer.SystemFlavorMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Dungeon dungeon = new Dungeon(10, 10);
    Player player = new Player(1, 1, 100, 50);
    List<Enemy> enemyList = new ArrayList<Enemy>();
    Scanner scanner = new Scanner(System.in);

    public Game(){

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
        enemyList.add(new Enemy(2, 8, 100, 30));

        while(true){
            dungeon.printDungeon(player, enemyList);
            System.out.println("Move (W: go up, S: go down, A: go left, D: go right, Q: quit): ");
            String input = scanner.nextLine();

            if (input.equals("q")) break;

            switch (input) {
                case "w":
                    player.move(0, -1, dungeon);
                    checkForItem();
                    checkForEnemy();
                    break;
                case "s":
                    player.move(0, 1, dungeon);
                    checkForItem();
                    checkForEnemy();
                    break;
                case "a":
                    player.move(-1, 0, dungeon);
                    checkForItem();
                    checkForEnemy();
                    break;
                case "d":
                    player.move(1, 0, dungeon);
                    checkForItem();
                    checkForEnemy();
                    break;
                default:
                    System.out.println("No valid command!");
                    break;
            }
        }
    }

    private void checkForEnemy() {
        for (Enemy enemy : enemyList){
            if(enemy.getX() == player.getX() && enemy.getY() == player.getY());
            System.out.println("You encountered an enemy!");
            startBattle(enemy);
        }
    }

    public void startBattle(Enemy enemy){
        while(player.getHealth() > 0 && enemy.getHealth() > 0){
            System.out.println("You attack the enemy...");
            enemy.setHealth(enemy.getHealth() - player.getDamage());
            System.out.println("Enemy now has " + enemy.getHealth() + " hp");
            System.out.println("Press ENTER for the enemies round");
            scanner.nextLine();
            System.out.println("Enemy attacks you!");
            player.setHealth(player.getHealth() - enemy.getDamage());
            System.out.println("You now have " + player.getHealth());
            System.out.println("Press ENTER for your round.");
            scanner.nextLine();
        }
        if(player.getHealth() > 0 && enemy.getHealth() < 0){
            System.out.println("You killed the enemy! Lets continue.");
            enemyList.remove(enemy);
        }
        else if(enemy.getHealth() > 0 && player.getHealth() < 0){
            System.out.println("Oh no! You died. Better luck next time. THE END!");
            scanner.nextLine();
            System.exit(0);
        }
    }

    private void checkForItem() {
       Item item = dungeon.getItemAt(player.getX(), player.getY());
        if (item != null) {
            System.out.println("You found a " + item.getName() + "!");
            item.use(player);
            dungeon.removeItemAt(player.getX(), player.getY());
        }
    }


}
