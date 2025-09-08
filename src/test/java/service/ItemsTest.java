package service;

import entities.Goblin;
import entities.HealingPotion;
import entities.Player;
import entities.Weapon;
import map.Tile;
import map.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemsTest {

    @Test
    void healingPotionIncreasesHpButNotAboveMax() {
        Player p = new Player("Hero", 30, 0, 0);
        // Skada spelaren först
        p.takeDamage(15); // HP = 15/30
        assertEquals(15, p.getHealth());

        // Lägg till potion och använd via index
        HealingPotion pot = new HealingPotion(20); // skulle ge 35 men ska cap:a till 30
        InventoryService.addItem(p, pot, InventoryService.DEFAULT_MAX_SLOTS);

        // Hitta index (sista itemet)
        int idx = p.getInventory().size() - 1;
        InventoryService.useItemByIndex(p, idx);

        assertEquals(30, p.getHealth(), "HP should be capped at maxHealth");
        // Engångs: ska vara borttagen från inventory
        assertTrue(p.getInventory().isEmpty(), "Potion should be consumed and removed");
    }

    @Test
    void usingNonExistentItemIsGracefullyRejected() {
        Player p = new Player("Hero", 30, 0, 0);
        int beforeHp = p.getHealth();
        int beforeInv = p.getInventory().size();

        // Försök använda något som inte finns
        InventoryService.useItemByName(p, "potion-does-not-exist");

        // Inga ändringar på HP eller inventory
        assertEquals(beforeHp, p.getHealth());
        assertEquals(beforeInv, p.getInventory().size());
    }

    @Test
    void weaponModifierAffectsAttackDamage() {
        Player p = new Player("Hero", 30, 0, 0);
        int base = p.getBaseDamage();     // enligt Player-impl: 5
        Weapon sword = new Weapon("Sword", 3);

        // Lägg i inventory och använd via namn
        InventoryService.addItem(p, sword, InventoryService.DEFAULT_MAX_SLOTS);
        InventoryService.useItemByName(p, "sword");

        // Base damage ska ha ökat
        assertEquals(base + 3, p.getBaseDamage(), "Weapon should increase base damage");
        // Vapnet är engångs → ska vara borta
        assertTrue(p.getInventory().isEmpty());

        // Verifiera faktisk skada i strid
        Goblin g = new Goblin(0, 0); // 10 HP
        Tile tile = new Tile(TileType.EMPTY);
        tile.setEnemy(g);

        CombatService.playerAttack(p, tile);
        // Goblin HP ska ha minskat med (base + 3)
        assertEquals(10 - (base + 3), g.getHealth());
    }
}
