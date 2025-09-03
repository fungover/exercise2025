package ExerciseTwo;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Game.InputHandling;
import ExerciseTwo.Map.Dungeon;
import ExerciseTwo.Map.Hallway;
import ExerciseTwo.Service.Combat;
import ExerciseTwo.Service.HandleFinds;
import ExerciseTwo.Service.Movement;
import ExerciseTwo.Service.Position;

import java.util.Scanner;

public class Main {

    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        InputHandling inputHandling = new InputHandling();
        String name = inputHandling.enterName(sc);
        Player player = new Player(name);
        int health = player.getHealth();
        player.presentPlayer();

        InputHandling.commands();

        Inventory inventory = new Inventory();
        Item potion = new Potion();
        Item coin = new Coin();

        Enemy monster = new Monster();

        Dungeon dungeon = new Hallway();
        dungeon.printMap();
        dungeon.description();
        String[][] map = dungeon.getDungeonMap();

        Position position = new Position(map);
        Movement movement = new Movement(position);

        while (true) {
            String move = movement.move(sc);
            String event = movement.checkMovement(map, move);

            if(event.equals("enemy")){

                Combat combat = new Combat(monster, player);
                combat.description();
                boolean choice = combat.makeAttack(sc);
                if(choice){
                    while (true){
                        int attackOfPlayer = combat.playerAttack();
                        int enemyDamage = combat.enemyDamage(attackOfPlayer);
                        if(enemyDamage < 0){
                            combat.enemyDefeated();
                            break;
                        }
                        int attackOfEnemy = combat.enemyAttack();
                        int playerDamage = combat.playerDamage(attackOfEnemy);
                        if (playerDamage == 0) combat.gameOver();
                    }
                }
            }

            if(event.equals("potion")){
                potion.itemFound();
                HandleFinds handleItem = new HandleFinds(potion);
                boolean answer = handleItem.addFind(sc);
                if(answer){
                    inventory.addItem(potion);
                }
            }

            if(event.equals("gift")){
                coin.itemFound();
                HandleFinds handleItem = new HandleFinds(coin);
                boolean answer = handleItem.addFind(sc);
                if(answer){
                    inventory.addItem(potion);
                }
            }

            if(event.equals("door")) break;
        }


    }

}
