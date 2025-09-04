package ExerciseTwo.Service;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Map.Dungeon;

import java.util.Scanner;

public class GameLogic {

    public void runGame(Dungeon dungeon, Player player){

        Scanner sc = new Scanner(System.in);

        Inventory inventory = new Inventory();
        Item potion = new Potion();
        Item coin = new Coin();

        dungeon.printMap();
        dungeon.description();
        String[][] map = dungeon.getDungeonMap();

        Position position = new Position(map);
        Movement movement = new Movement(position);

        while (true) {
            String move = movement.move(sc);
            String event = movement.checkMovement(map, move);

            if(event.equals("enemy")){

                Combat combat = new Combat(player);
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
                HandleFinds handleItem = new HandleFinds();
                boolean answer = handleItem.addFind(sc);
                if(answer){
                    inventory.addItem(potion);
                    sc.nextLine();
                }
            }

            if(event.equals("coin")){
                coin.itemFound();
                HandleFinds handleItem = new HandleFinds();
                boolean answer = handleItem.addFind(sc);
                if(answer){
                    inventory.addItem(potion);
                    sc.nextLine();
                }
            }

            if(event.equals("door")) break;
        }
    }

}
