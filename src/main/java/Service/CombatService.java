package Service;

import Enemy.*;
import Entities.*;
import Map.*;

public class CombatService {
    private final DungeonMap map;

    public CombatService(DungeonMap map) { this.map = map; }

    public void attackCurrentTile(Player p) {
        Tile t = map.tile(p.getX(), p.getY());
        Enemy e = t.getEnemy();
        if (e == null) {
            System.out.println("There's nothing to attack here.");
            return;
        }

        // ✅ spelarens skada = base + weapon via getAttackPower()
        int playerDamage = p.getAttackPower();
        System.out.println(p.getName() + " attacks " + e.getName() + " for " + playerDamage + "!");
        e.takeDamage(playerDamage);

        if (!e.isAlive()) {
            System.out.println(e.getName() + " is defeated!");
            t.setEnemy(null);
            return;
        }

        // ✅ fiendens tur: använder Enemy.getAttackPower() (vanligen bara basattack)
        int enemyDamage = e.getAttackPower();
        System.out.println(e.getName() + " retaliates for " + enemyDamage + "!");
        p.takeDamage(enemyDamage);
    }
}
