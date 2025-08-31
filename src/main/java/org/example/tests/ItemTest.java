package org.example.tests;

import org.example.entities.*;
import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class ItemTest {

    @Test
    public void testPotionHeals(){
        Player hero = new Player("Hero", 20, 5, new Position(0,0));
        hero.takeDamage(10);

        Potion potion = new Potion("Small Potion", 7);
        potion.use(hero);

        assertEquals(17, hero.getHp());
    }
    @Test
    public void testWeaponIncreasesDamage(){
        Player hero = new Player("Hero", 20, 5, new Position(0,0));
        Weapon sword = new Weapon("Iron Sword", 3);

        sword.use(hero);
        assertEquals(8, hero.getBaseDamage());
    }

    @Test
    public void testGetRandomItem() {
        for (int i = 0; i < 10; i++) {
            Item item = Item.getRandomItem(); // if the method is inside Item
            assertNotNull(item);
            assertTrue(item instanceof Potion || item instanceof Weapon);
        }
    }
}
