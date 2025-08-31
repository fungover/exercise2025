package org.example.tests;

import org.example.entities.*;
import org.example.entities.Character;
import org.example.map.*;
import org.example.service.CombatService;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class GameTest {
    @Test
    void testGameEndsWhenDragonDefeatedAndTreasureCollected() {
        Dungeon dungeon = new Dungeon(5, 5);
        Position dragonPos = new Position(2, 2);

        Player hero = new Player("Hero", 50, 20, new Position(0, 0));

        Enemy dragon = new Enemy("Dragon", 30, 5, dragonPos) {
            @Override
            public String getType() { return "Dragon"; }
        };

        Item legendaryTreasure = new Item("Legendary Treasure") {
            @Override
            public void use(Character character) {
                System.out.println(character.getName() + " admires the Legendary Treasure. Victory!");
            }
        };

        dungeon.getTile(dragonPos).setEnemy(dragon);
        dungeon.getTile(dragonPos).setItem(legendaryTreasure);

        CombatService combatService = new CombatService();
        combatService.fight(hero, dragon);

        if (!dragon.isAlive()) {
            hero.getInventory().addItem(legendaryTreasure);
            dungeon.getTile(dragonPos).setItem(null);
        }

        assertFalse(dragon.isAlive(), "Dragon should be dead");
        assertEquals(1, hero.getInventory().getItems().size(), "Hero should have the treasure");
        assertEquals("Legendary Treasure", hero.getInventory().getItems().get(0).getName());

        boolean gameWon = hero.getInventory().getItems().stream()
                .anyMatch(item -> item.getName().equals("Legendary Treasure"));
        assertTrue(gameWon, "Game should be won when hero has the Legendary Treasure");
    }

}