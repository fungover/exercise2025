package org.example.game;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.utils.CombatText;

import static org.example.utils.Colors.*;

public class Combat {
    public void startCombat(Player player, Enemy enemy) {
        // Special dialog for Thade
        if (enemy.getName().equals("Thade")) {
            System.out.printf(CombatText.THADE_FOUND + "%n", player.getName());
            System.out.println(CombatText.THADE_WRONG);
            System.out.println(CombatText.THADE_DARKNESS);
            System.out.println(CombatText.THADE_DESTROY);
        }

        // Print out information about the fight
        System.out.printf(CombatText.COMBAT_START + "%n", enemy.getName());

        // While-loop continues while both are alive
        while (player.isAlive() && enemy.isAlive()) {
            // Player attacks then enemy attacks
            int playerDamage = player.attack();
            enemy.takeDamage(playerDamage);
            System.out.printf(CombatText.PLAYER_ATTACK + "%n", playerDamage);
            System.out.printf(CombatText.ENEMY_HEALTH_LEFT + "%n", enemy.getName(), enemy.getHealth());

            if (enemy.isAlive()) {
                int enemyDamage = enemy.attack();
                player.takeDamage(enemyDamage);
                System.out.printf(CombatText.ENEMY_ATTACK + "%n", enemy.getName(), enemyDamage);
                System.out.printf(CombatText.PLAYER_HEALTH_LEFT + "%n", player.getHealth());
            }
        }

        // Check who won
        if (player.isAlive()) {
            System.out.println(CombatText.COMBAT_VICTORY);
            System.out.printf(CombatText.PLAYER_HEALTH_AFTER + "%n", player.getHealth());
            if (enemy.getName().equals("Thade")) {
                System.out.print(CombatText.THADE_WELL_PLAYED);
                System.out.print(cyan(player.getName()));
                System.out.print(purple(".."));
                System.out.println(CombatText.THADE_WELL_PLAYED_END);
                System.out.print(CombatText.THADE_DIES);
            }
        } else {
            if (enemy.getName().equals("Thade")) {
                System.out.print(CombatText.THADE_REVEAL_START);
                System.out.print(cyan(player.getName()));
                System.out.print(CombatText.THADE_REVEAL_QUESTION);
                System.out.print(CombatText.THADE_NOT_NAME);
                System.out.println(CombatText.THADE_REVEAL_SEQUENCE);
                System.out.println(CombatText.THADE_REVEAL_DEATH);
            }
            System.out.println(CombatText.COMBAT_DEFEAT);
        }
    }
}