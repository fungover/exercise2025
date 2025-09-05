package service;

import entities.Enemy;
import entities.Player;
import map.Tile;
import utils.Printer;

/**
 * CombatService hanterar strider mellan spelare och fiender.
 */
public class CombatService {

    /**
     * Spelaren attackerar fienden på en given tile.
     */
    public static void playerAttack(Player player, Tile tile) {
        Enemy enemy = tile.getEnemy();
        if (enemy == null || !enemy.isAlive()) {
            Printer.error("Ingen fiende att attackera här.");
            return;
        }

        int damage = player.getBaseDamage(); // enkel formel: bara basdamage
        enemy.takeDamage(damage);

        Printer.info("Du attackerar " + enemy.getType() + " för " + damage +
                " skada. (HP kvar: " + enemy.getHealth() + ")");

        if (!enemy.isAlive()) {
            Printer.info(enemy.getType() + " är besegrad!");
            tile.removeEnemy();
        }
    }

    /**
     * Fienden attackerar spelaren.
     */
    public static void enemyAttack(Enemy enemy, Player player) {
        if (enemy == null || !enemy.isAlive() || player == null || !player.isAlive()) {
            return;
        }

        int dmg = enemy.getDamage();
        player.takeDamage(dmg);

        Printer.info(enemy.getType() + " attackerar dig för " + dmg +
                " skada! (Din HP: " + player.getHealth() + ")");
    }
}
