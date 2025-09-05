package ExerciseTwo.Game;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Entities.Inventory.Coin;
import ExerciseTwo.Entities.Inventory.Inventory;
import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Inventory.Potion;
import ExerciseTwo.Map.Dungeon;
import ExerciseTwo.Service.*;

import java.util.Scanner;

public class GameLogic {

    public void runGame(Dungeon dungeon, Player player){

        Scanner sc = new Scanner(System.in);

        Inventory inventory = new Inventory();
        PlayerInput playerInput = new PlayerInput(inventory, player);
        HandleFinds handleItem = new HandleFinds();

        Item potion = new Potion();
        Item coin = new Coin();

        dungeon.printMap();
        dungeon.description();
        String[][] map = dungeon.getDungeonMap();

        Position position = new Position(map);
        Movement movement = new Movement(position);

        while (true) {
            String move = movement.move(sc, playerInput);
            String event = movement.checkMovement(map, move);

            if(event.equals("enemy")){

                Combat combat = new Combat(player);
                combat.description();
                boolean choice = combat.makeAttack(sc, playerInput);
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
                boolean answer = handleItem.addFind(sc, playerInput);
                if(answer){
                    inventory.addItem(potion);
                }
            }

            if(event.equals("coin")){
                coin.itemFound();
                boolean answer = handleItem.addFind(sc, playerInput);
                if(answer){
                    inventory.addItem(coin);
                }
            }

            if(event.equals("door")) break;
        }
    }

}
