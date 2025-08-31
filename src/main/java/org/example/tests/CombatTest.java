package org.example.tests;

import org.example.entities.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CombatTest {

    @Test
    public void testPlayerAttacksEnemy() {
        Position start = new Position(0, 0);
        Player hero = new Player ("Hero", 20, 5, start);
        Enemy wizard = new Wizard(new Position(1, 1));

        assertEquals(14, wizard.getHp());

        hero.attack(wizard);

        assertEquals(9, wizard.getHp());
    }

    @Test
    public void testEnemyTakesDamageAndDies() {
        Position pos = new Position(0,0);
        Enemy troll = new Troll(pos);

        assertEquals(18, troll.getHp());

        troll.takeDamage(25);

        assertEquals(0, troll.getHp());
        assertEquals(false, troll.isAlive());
    }

    @Test
    public void testHealing() {
        Position pos = new Position(0, 0);
        Player hero = new Player("Hero", 20, 5, pos);

        hero.takeDamage(10);
        assertEquals(10, hero.getHp());

        hero.heal(5);
        assertEquals(15, hero.getHp());

        hero.heal(10);
        assertEquals(20, hero.getHp());
    }
}
