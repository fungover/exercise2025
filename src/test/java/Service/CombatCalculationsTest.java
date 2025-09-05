package Service;

import Entities.Player;
import Enemy.Spider;
import Enemy.Enemy;
import Weapons.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class CombatCalculationsTest {

    @Test
    void playerAttackPowerIsBasePlusWeapon() {
        Player hero = new Player("Hero", 30, 5, 0, 0);
        Weapon sword = new Weapon("Sword", 7);
        hero.addWeapon(sword);

        assertEquals(12, hero.getAttackPower());
    }

    @Test
    void attackReducesEnemyHealthByAttackPower() {
        Player hero = new Player("Hero", 30, 5, 0, 0);
        hero.addWeapon(new Weapon("Sword", 7));

        Enemy spider = new Spider(1, 1);

        hero.attack(spider);
        assertTrue(spider.getHealth() <= 0, "Spider should be dead or 0 HP after 12 dmg if it had 10 HP");
    }

    @Test
    void enemyUsesBaseAttackOnly() {
        Player hero = new Player("Hero", 30, 5, 0, 0);
        Enemy spider = new Spider(1, 1);

        int hpBefore = hero.getHealth();
        spider.attack(hero);

        assertEquals(hpBefore - spider.getAttackPower(), hero.getHealth());

    }
}
