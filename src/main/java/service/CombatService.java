package service;

import entities.Enemy;
import entities.Player;
import map.Tile;
import utils.Printer;

public class CombatService {

    public static void playerAttack(Player player, Tile tile) {
        if (player == null) { Printer.error("No player present."); return; }
        if (tile == null)   { Printer.error("There is nothing to attack here."); return; }

        Enemy enemy = tile.getEnemy();
        if (enemy == null || !enemy.isAlive()) {
            Printer.error("There is no enemy to attack here.");
            return;
        }

        int damage = player.getBaseDamage();
        enemy.takeDamage(damage);
        Printer.info("You hit the " + enemy.getType() + " for " + damage +
                " damage. (Enemy HP: " + enemy.getHealth() + ")");

        if (!enemy.isAlive()) {
            Printer.info(enemy.getType() + " is defeated!");
            tile.removeEnemy();
        }
    }

    public static void enemyAttack(Enemy enemy, Player player) {
        if (enemy == null || !enemy.isAlive() || player == null || !player.isAlive()) return;
        int dmg = enemy.getDamage();
        player.takeDamage(dmg);
        Printer.info(enemy.getType() + " hits you for " + dmg +
                " damage! (Your HP: " + player.getHealth() + ")");
    }
}
