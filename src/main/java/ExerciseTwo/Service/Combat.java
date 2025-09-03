package ExerciseTwo.Service;

import ExerciseTwo.Entities.Enemy;
import ExerciseTwo.Entities.Player;

import java.util.Scanner;

public class Combat {

    private final Enemy enemy;
    private final Player player;

    public Combat(Enemy enemy, Player player) {
        this.enemy = enemy;
        this.player = player;
    }

    public void description(){
        enemy.description();
    }

    public boolean makeAttack(Scanner sc){

        //move to handling input and return value
        while (true) {
            System.out.println("""
                    Make your choice:
                        a - attack
                        r - run
                    """);
            switch (sc.nextLine().toLowerCase()) {
                case "a":
                    return true;
                case "f":
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
        System.out.println("You defeated the enemy!");
    }

    public int enemyAttack() {
        return enemy.getAttack();
    }

    public int playerDamage(int attackOfEnemy) {
        player.setHealth(attackOfEnemy);
        System.out.println(player.getHealth());
        return player.getHealth();
    }

    public void gameOver() {
        System.out.println("Game over!");
        System.exit(0);
    }

}
