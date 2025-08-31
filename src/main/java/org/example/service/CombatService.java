package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class CombatService {
    public void fight (Player player, Enemy enemy) {
        System.out.println("Combat starts: " + player.getName() + " vs " + enemy.getName());
        while (player.isAlive() && enemy.isAlive()) {
            player.attack(enemy);
            if (enemy.isAlive()) {
                enemy.attack(player);
            }
        }

        if (!player.isAlive()) {
            System.out.println(player.getName() + " is dead");
        }
        else {
            System.out.println(enemy.getName() + " is dead!");
        }
    }
}
