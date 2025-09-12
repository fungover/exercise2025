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
    Player player = new Player(1, 1, 100, 10);
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
                    break;
                case "s":
                    player.move(0, 1, dungeon);
                    checkForItem();
                    break;
                case "a":
                    player.move(-1, 0, dungeon);
                    checkForItem();
                    break;
                case "d":
                    player.move(1, 0, dungeon);
                    checkForItem();
                    break;
                default:
                    System.out.println("No valid command!");
                    break;
            }
        }

    }

    private void checkForItem() {
        Item item = dungeon.getItemAt(player.getX(), player.getY());
        if (item != null) {
            System.out.println("You found a " + item.getName() + "!");
            dungeon.removeItemAt(player.getX(), player.getY());
        }
    }

}
