package ExerciseTwo.Game;

import ExerciseTwo.Entities.*;
import ExerciseTwo.Entities.Inventory.Coin;
import ExerciseTwo.Entities.Inventory.Inventory;
import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Entities.Inventory.Potion;
import ExerciseTwo.Entities.Weapons.DiamondSword;
import ExerciseTwo.Entities.Weapons.Weapon;
import ExerciseTwo.Map.Dungeon;
import ExerciseTwo.Service.*;

import java.util.Scanner;

public class GameLogic {

    Weapon weapon;
    Inventory inventory;
    Player player;

    public GameLogic(Player player) {
        this.weapon = new Weapon();
        this.inventory = new Inventory();
        this.player = player;
    }

    public void gameLoop(Dungeon dungeon) {

        Scanner sc = new Scanner(System.in);

        PlayerInput playerInput = new PlayerInput(inventory, player, weapon, sc);
        HandleFinds handleItem = new HandleFinds();

        dungeon.printMap();
        dungeon.description();
        String[][] map = dungeon.getDungeonMap();

        Position position = new Position(map);
        Movement movement = new Movement(position);

        while (true) {
            String move = movement.move(sc, playerInput);
            String event = movement.checkMovement(map, move);

            if (event.equals("enemy")) {

                Combat combat = new Combat(player);
                combat.description();
                boolean choice = combat.makeAttack(sc, playerInput);
                if (choice) {
                    while (true) {
                        int attackOfPlayer = combat.playerAttack();
                        int enemyDamage = combat.enemyDamage(attackOfPlayer);
                        if (enemyDamage <= 0) {
                            combat.enemyDefeated();
                            break;
                        }
                        int attackOfEnemy = combat.enemyAttack();
                        int playerDamage = combat.playerDamage(attackOfEnemy);
                        if (playerDamage == 0) combat.gameOver();
                    }
                }
            }

            if (event.equals("potion")) {
                Item potion = new Potion();
                potion.itemDescription();
                boolean answer = handleItem.addFind(sc, playerInput);
                if (answer) {
                    inventory.addItem(potion);
                }
            }

            if (event.equals("coin")) {
                Item coin = new Coin();
                coin.itemDescription();
                boolean answer = handleItem.addFind(sc, playerInput);
                if (answer) {
                    inventory.addItem(coin);
                }
            }

            if(event.equals("sword")){
                Item diamondSword = new DiamondSword();
                diamondSword.itemDescription();
                weapon.setWeapon(diamondSword);
            }

            if (event.equals("door")) break;

            if (event.equals("treasure")) {
                System.exit(0);
            }
        }
    }
}
