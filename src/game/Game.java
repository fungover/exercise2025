package game;

import entities.Player;
import map.Room;
import org.w3c.dom.ls.LSOutput;
import javax.swing.event.CaretListener;
import java.util.Scanner;
import entities.Enemy;

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Want to play Dungeon Crawler?");
        System.out.println("What's your name?");

        String playerName = scanner.nextLine();
        Player player = new Player(playerName);

        System.out.println("Welcome " + player.getName() + "! Lets start this adventure!");
        System.out.println("Starting health is: " + player.getHealth());


        //Rum
        Room start = new Room("Choose between two doors.");
        Room treasureRoom = new Room("You found your gold!");
        Room monsterRoom = new Room("Attacked by a monster!");
        monsterRoom.setEnemy(new Enemy("Monster", 10, 2));

        start.addExit(1, treasureRoom);
        start.addExit(2, monsterRoom);

        Room currentRoom = start;

        //CLI loop
        while (true) {
            System.out.println(currentRoom.getDescription());

            if (currentRoom.getEnemy() != null && currentRoom.getEnemy().isAlive()) {
                Enemy enemy = currentRoom.getEnemy();
                System.out.println("Enemy encountered: " + enemy.getName() + " (HP: " + enemy.getHealth() + ")");

                while (enemy.isAlive() && player.getHealth() > 0) {
                    System.out.print("Attack (1) or run (2)? ");
                    String action = scanner.nextLine();

                    if (action.equals("1")){
                        int dmg = 5;
                        enemy.takeDamage(dmg);
                        System.out.println("You attack the " + enemy.getName() + " for " + dmg + " damage!");
                        System.out.println(enemy.getName() + " HP" + enemy.getHealth());


                        if (enemy.isAlive()) {
                            player.setHealth(player.getHealth() - enemy.getDamage());
                            System.out.println("The " + enemy.getName() + " hits you for " + enemy.getDamage() + " damage!" );
                            System.out.println("Your HP: " + player.getHealth());
                        }
                    } else if (action.equals("2")) {
                        System.out.println("Running back to previous room");
                        currentRoom = start;
                        break;
                    }

                    if (player.getHealth() <= 0) {
                        System.out.println("You have lost - game over.");
                        return;
                    }
                }

                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                }
            }

            if (currentRoom.getExits().isEmpty()) {
                System.out.println("No more doors, the adventure ends here.");
                break;
            }

            System.out.print("Pick your door: ");
            for (Integer option : currentRoom.getExits().keySet()) {
                System.out.println(" - " + option);
            }

            int choice = Integer.parseInt(scanner.nextLine());
            Room nextRoom = currentRoom.getExit(choice);

            if (nextRoom == null) {
                System.out.println("Invalid choice, try again.");
            } else {
                currentRoom = nextRoom;
            }
        }
    }
}


