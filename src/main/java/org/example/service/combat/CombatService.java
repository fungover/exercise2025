package org.example.service.combat;

import java.util.List;
import java.util.Random;
import org.example.entities.characters.Player;
import org.example.entities.enemies.Enemy;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.game.cli.CombatUI;

public final class CombatService {

    /**
     * Result of a single combat round.
     */
    public record RoundResult(
            int playerDamageDealt,
            int enemyDamageDealt,
            boolean enemyDead,
            boolean playerDead
    ) {}

    // Seedable RNG for flee so tests can be deterministic if needed.
    private final Random fleeRng;

    public CombatService() {
        this.fleeRng = new Random();
    }

    /**
     * Pure calculation for one combat exchange:
     * 1) Player attacks enemy.
     * 2) If the enemy survives, the enemy attacks the player.
     */
    public RoundResult attackOnce(Player player, Enemy enemy, Inventory inventory) {
        int playerDamageDealt = player.attackDamage();

        playerDamageDealt += inventory.takeNextAttackBonus();

        enemy.takeDamage(playerDamageDealt);

        int enemyDamageDealt = 0;
        if (!enemy.isDead()) {
            enemyDamageDealt = enemy.attackDamage();
            player.takeDamage(enemyDamageDealt);
        }

        return new RoundResult(
                playerDamageDealt,
                enemyDamageDealt,
                enemy.isDead(),
                player.isDead()
        );
    }

    /**
     * Orchestrator with UI loop. Uses attackOnce(...) for the actual math.
     * Keeps all I/O (printing, reading commands) in here, separate from the logic.
     */
    public void fight(Player player, Enemy enemy, Inventory inventory, CombatUI ui) {
        ui.showIntro(enemy.name(), player.getHealth(), player.getMaxHealth(), enemy.health(), enemy.maxHealth());

        while (!enemy.isDead() && !player.isDead()) {
            CombatUI.Action action = ui.readAction();

            switch (action.type()) {
                case ATTACK -> {
                    RoundResult round = attackOnce(player, enemy, inventory);
                    ui.playerAttackFeedback(enemy.name(), round.playerDamageDealt());
                    if (!round.enemyDead()) {
                        ui.enemyAttackFeedback(enemy.name(), round.enemyDamageDealt());
                    }
                }

                case USE_ITEM -> {
                    Integer selectedIndex = action.oneBasedItemIndex();
                    if (selectedIndex == null) {
                        ui.showMessage("Usage: use <number>");
                        break;
                    }

                    List<Item> items = inventory.items();
                    if (selectedIndex < 1 || selectedIndex > items.size()) {
                        ui.showMessage("No such item.");
                        break;
                    }

                    Item chosenItem = items.get(selectedIndex - 1);
                    boolean usedSuccessfully = inventory.use(chosenItem, player);
                    ui.usedItemFeedback(chosenItem.name(), usedSuccessfully);
                }

                case FLEE -> {
                    if (attemptFlee()) {
                        ui.showMessage("You flee successfully!");
                        return;
                    }
                    ui.showMessage("You fail to flee!");
                }

                case HELP -> ui.showMessage("a|attack, use <n>, f|flee, h|help");

                case UNKNOWN -> {
                    ui.showMessage("Unknown command. Type h for help.");
                    continue; // go read the next action
                }
            }

            // Show the current status after handling the action.
            ui.showStatus(
                    player.getHealth(),
                    player.getMaxHealth(),
                    enemy.health(),
                    enemy.maxHealth()
            );
        }

        if (player.isDead()) {
            ui.showMessage("You died… Game over.");
            System.exit(0);
        } else {
            ui.showMessage("You defeated the " + enemy.name() + "!");
        }
    }

    private boolean attemptFlee() {
        return fleeRng.nextBoolean(); // simple 50%
    }
}
