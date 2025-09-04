package org.example.service.combat;

import java.util.List;
import org.example.entities.characters.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.game.cli.CombatUI;

public final class CombatService {

    public void fight(Player player, Enemy enemy, Inventory inventory, CombatUI ui) {
        ui.showIntro(enemy.name(), player.getHealth(), player.getMaxHealth(), enemy.health(), enemy.maxHealth());

        while (!enemy.isDead() && !player.isDead()) {
            CombatUI.Action action = ui.readAction();
            switch (action.type()) {
                case ATTACK -> {
                    int damage = player.attackDamage();
                    enemy.takeDamage(damage);
                    ui.playerAttackFeedback(enemy.name(), damage);
                }
                case USE_ITEM -> {
                    Integer idx = action.oneBasedItemIndex();
                    if (idx == null) { ui.showMessage("Usage: use <number>"); break; }
                    List<Item> items = inventory.items();
                    if (idx < 1 || idx > items.size()) { ui.showMessage("No such item."); break; }
                    Item it = items.get(idx - 1);
                    boolean ok = inventory.use(it, player);
                    ui.usedItemFeedback(it.name(), ok);
                }
                case FLEE -> {
                    if (tryFlee()) { ui.showMessage("You flee successfully!"); return; }
                    ui.showMessage("You fail to flee!");
                }
                case HELP -> ui.showMessage("a|attack, use <n>, f|flee, h|help");
                case UNKNOWN -> { ui.showMessage("Unknown command. Type h for help."); continue; }
            }

            if (!enemy.isDead()) {
                int enemyDamage = enemy.attackDamage();
                player.takeDamage(enemyDamage);
                ui.enemyAttackFeedback(enemy.name(), enemyDamage);
            }
            ui.showStatus(player.getHealth(), player.getMaxHealth(), enemy.health(), enemy.maxHealth());
        }

        if (player.isDead()) {
            ui.showMessage("You died… Game over.");
            System.exit(0);
        } else {
            ui.showMessage("You defeated the " + enemy.name() + "!");
        }
    }

    private boolean tryFlee() {
        return new java.util.Random().nextBoolean(); // simple 50%
    }
}
