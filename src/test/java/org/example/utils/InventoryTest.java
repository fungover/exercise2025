package org.example.utils;

import org.example.entities.Healing;
import org.example.entities.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    @Test
    void testInventoryManagement() {
     Player player = new Player("TestPlayer");
     Healing potion = new Healing("TestPotion", "Heals 20 HP", "Healing",1,20);

     // Empty inventory
     assertEquals(0, player.getInventory().getSize());


     // Add one potion (item) to the inventory
     assertTrue(player.addItem(potion));
     assertEquals(1, player.getInventory().getSize());
     assertNotNull(player.findItem("TestPotion"));

     // Remove potion (item) from the inventory
     assertTrue(player.removeItem(potion));
     assertEquals(0, player.getInventory().getSize());
     assertNull(player.findItem("TestPotion"));

    }
}
