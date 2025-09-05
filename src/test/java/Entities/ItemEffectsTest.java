package Entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class ItemEffectsTest {

    @Test
    void usingPotionHealsAndConsumes() {
        Player p = new Player("Hero", 20, 3, 0, 0);
        p.addItem(new Item("Small Potion", ItemType.POTION, 10));

        p.useFirstPotion();
        assertEquals(30, p.getHealth(), "Potion should heal 10");

        p.useFirstPotion();
        assertEquals(30, p.getHealth(), "No extra potion -> health unchanged");
    }
}
