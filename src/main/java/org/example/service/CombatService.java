package org.example.service;
import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Tile;

public class CombatService {

    public static void fightRound(Player player, Tile[][] tiles){

        Tile currentTile = tiles[player.getX()][player.getY()];
        Enemy enemy = currentTile.getEnemy();

        //Check if enemy exists and attack is possible:
        if (enemy == null){
            System.out.println("\nThere is nothing to attack here.");
            return;
        }

        //Player attack(and removal of enemy in case of victory):
        enemy.setHealth(enemy.getHealth() - player.getAttack());
        System.out.println("\n" + player.getName() + " hits " + enemy.getName() + " for " + player.getAttack() + " HP\n");

        if (enemy.getHealth() <= 0) {
            System.out.println("\n" + enemy.getName() + " is defeated\n");
            currentTile.setEnemy(null);
            return;
        }

        //Enemy attack:
        player.setHealth(player.getHealth() - enemy.getDamage());
        System.out.println(enemy.getName() + " hits back for " + enemy.getDamage() + " damage!");

        if (player.getHealth() <= 0) {
            System.out.println(player.getName() + " is defeated! Game Over");
        }
    }
}
