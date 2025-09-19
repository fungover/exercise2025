package org.example.service;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.entities.Troll;
import org.example.entities.items.HealthPotion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CombatTest {
    Combat combat = new Combat();
    Troll troll = new Troll();
    Player player = new Player("CombatMaster", 45);

    @Test
    public void testDamageDealt() {
        int oldEnemyHealth = troll.getHealth();
        combat.damageDealt(troll, 20);
        assertThat(oldEnemyHealth).isNotEqualTo(troll.getHealth());
    }
    @Test
    public void testNoDamageDealt() {
        int oldEnemyHealth = troll.getHealth();
        combat.damageDealt(troll, 0);
        assertThat(oldEnemyHealth).isEqualTo(troll.getHealth());
    }

    @Test
    public void testDamageTaken() {
        int oldPlayerHealth = player.getHealth();
        combat.damageTaken(player, troll);
        assertThat(oldPlayerHealth).isNotEqualTo(player.getHealth());
    }
    @Test
    public void testNoDamageTaken() {
        FakeEnemy harold = new FakeEnemy();
        int oldPlayerHealth = player.getHealth();
        combat.damageTaken(player, harold);
        // Harold's damage input is 0...
        assertThat(oldPlayerHealth).isEqualTo(player.getHealth());
    }
    static class FakeEnemy extends Enemy {
        public String getName() {
            return "Harold";
        }
        public int getDamage() {
            return 0;
        }
    }

    @Test
    public void testHealthRestored() {
        HealthPotion healthPotion = new HealthPotion(1);
        int oldPlayerHealth = player.getHealth();
        player.increaseHealth(healthPotion.restoreHealth());
        assertThat(oldPlayerHealth).isLessThan(player.getHealth());
    }
}
