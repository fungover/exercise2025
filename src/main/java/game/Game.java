package game;

import entities.Enemy;
import entities.Player;
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

        enemyList.add(new Enemy(5, 5, 50, 10));
        enemyList.add(new Enemy(2, 4, 100, 30));
        enemyList.add(new Enemy(4, 2, 20, 20));
        enemyList.add(new Enemy(5, 3, 50, 5));
        enemyList.add(new Enemy(8, 7, 50, 5));

        while(true){
            dungeon.printDungeon(player, enemyList);
            System.out.println("Move (W: go up, S: go down, A: go left, D: go right, Q: quit): ");
            String input = scanner.nextLine();

            if (input.equals("q")) break;

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
        }
    }

}
