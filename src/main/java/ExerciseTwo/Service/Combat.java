package ExerciseTwo.Service;

import ExerciseTwo.Entities.Enemy.Enemy;
import ExerciseTwo.Entities.Player;
import ExerciseTwo.Game.PlayerInput;
import ExerciseTwo.Utils.GenerateMonster;
import ExerciseTwo.Utils.PrintText;

import java.util.Scanner;

public class Combat {


    GenerateMonster monster = new GenerateMonster();
    Enemy enemy = monster.getEnemyToBattle();

    private final Player player;

    public Combat(Player player) {
        this.player = player;
    }

    public void description(){
        enemy.description();
    }

    public boolean makeAttack(Scanner sc){

        while (true) {
            System.out.println("""
                    Make your choice:
                        a - attack
                        r - run
                    """);

            String inputFromPlayer = sc.nextLine().toLowerCase();
            PlayerInput input = new PlayerInput();
            if(input.commandInput(inputFromPlayer)){
                continue;
            }

            switch (inputFromPlayer) {
                case "a":
                    return true;
                case "r":
                    return false;
                default:
                    System.out.println("Wrong commando");
            }

        }
    }

    public int playerAttack(){
        return player.getPlayerAttack();
    }

    public int enemyDamage(int attackOfPlayer){
        enemy.setHealth(attackOfPlayer);
        return enemy.getHealth();
    }

    public void enemyDefeated() {
        PrintText.printRed("You defeated the enemy!");
    }

    public int enemyAttack() {
        return enemy.getAttack();
    }

    public int playerDamage(int attackOfEnemy) {
        player.setHealth(attackOfEnemy);
        return player.getHealth();
    }

    public void gameOver() {
        System.out.println("You are dead"+Emojis.skull+" Game over!");
        System.exit(0);
    }

}
