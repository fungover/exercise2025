package org.example.service;

import org.example.entities.Goblin;
import org.example.entities.MockPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatServiceTest {


    @Test void attackEnemy() {
        var player = new MockPlayer();
        var testEnemy = new Goblin();

        int startingHealth = testEnemy.getHealth();

        //player attacking, test to se if enemy take dmg
        CombatService.playerAttack(player, testEnemy);

        int afterAttackHealth = testEnemy.getHealth();

        //health should be less
        assertTrue(afterAttackHealth < startingHealth);

        //make sure a min of 1 dmg was done
        assertTrue(startingHealth - afterAttackHealth >= 1);

    }

    @Test void takeDamage() {
        var player = new MockPlayer();
        var testEnemy = new Goblin();

        int startingHealth = player.getHealth();

        //enemy attacking player
        CombatService.enemyAttack(player, testEnemy);

        int afterAttackHealth = player.getHealth();
        
        assertTrue(afterAttackHealth < startingHealth);
        assertTrue(startingHealth - afterAttackHealth >= 1);

    }
}