package service;

import entities.Goblin;
import entities.Orc;
import entities.Player;
import entities.Skeleton;
import entities.Weapon;
import map.Tile;
import map.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatServiceTest {

    @Test
    void playerDealsBaseDamageWithoutWeapon() {
        Player p = new Player("Hero", 30, 0, 0);     // baseDamage = 5 per implementation
        Goblin g = new Goblin(0, 0);                 // HP 10
        Tile tile = new Tile(TileType.EMPTY);
        tile.setEnemy(g);

        int before = g.getHealth();
        CombatService.playerAttack(p, tile);

        assertEquals(before - p.getBaseDamage(), g.getHealth());
        assertNotNull(tile.getEnemy(), "Enemy should still be on tile if not dead");
    }

    @Test
    void playerDamageIncreasesWhenUsingWeapon() {
        Player p = new Player("Hero", 30, 0, 0);     // baseDamage = 5
        // Use a weapon that adds +3 damage (consumable)
        Weapon sword = new Weapon("Sword", 3);
        p.use(sword);                                // baseDamage now 8

        Goblin g = new Goblin(0, 0);                 // HP 10
        Tile tile = new Tile(TileType.EMPTY);
        tile.setEnemy(g);

        CombatService.playerAttack(p, tile);

        assertEquals(10 - 8, g.getHealth(), "Goblin should have 2 HP left after an 8-dmg hit");
    }

    @Test
    void enemyDiesAndIsRemovedFromTile() {
        Player p = new Player("Hero", 30, 0, 0);
        p.setBaseDamage(50);                         // ensure one-shot kill

        Orc orc = new Orc(0, 0);                     // HP 30
        Tile tile = new Tile(TileType.EMPTY);
        tile.setEnemy(orc);

        CombatService.playerAttack(p, tile);

        assertFalse(orc.isAlive(), "Enemy should be dead");
        assertNull(tile.getEnemy(), "Dead enemy should be removed from tile");
        assertNotEquals(TileType.ENEMY, tile.getType(), "Tile type should no longer be ENEMY after removal");
    }

    @Test
    void playerTakesDamageOnEnemyTurn() {
        Player p = new Player("Hero", 30, 0, 0);
        Skeleton s = new Skeleton(0, 0);             // damage 4

        int before = p.getHealth();
        CombatService.enemyAttack(s, p);

        assertEquals(before - s.getDamage(), p.getHealth(), "Player HP should be reduced by enemy damage");
    }
}
