package service;

import entities.Player;
import entities.Enemy;
import utils.Constants;
import utils.RandomGenerator;

public class CombatService {


    public CombatResult startCombat(Player player, Enemy enemy) {
        if (!enemy.isAlive()) {
            return new CombatResult(false, false,
                    enemy.getName() + " är redan besegrad.", null);
        }

        StringBuilder combatLog = new StringBuilder();
        combatLog.append("⚔️ STRID BÖRJAR! ⚔️\n");
        combatLog.append(player.getName()).append(" möter ").append(enemy.getName()).append("!\n");
        combatLog.append(enemy.examine()).append("\n\n");

        return new CombatResult(true, false, combatLog.toString(), enemy);
    }

    public CombatResult playerAttack(Player player, Enemy enemy) {
        if (!enemy.isAlive()) {
            return new CombatResult(false, false,
                    enemy.getName() + " är redan besegrad och kan inte attackeras.", null);
        }

        StringBuilder result = new StringBuilder();

        // Player attacks
        int playerDamage = player.getTotalDamage();
        result.append("👊 Du attackerar ").append(enemy.getName()).append(" för ")
                .append(playerDamage).append(" skada!\n");

        String damageResult = enemy.takeDamageAndCheck(playerDamage);
        result.append(damageResult).append("\n");

        // Check if the enemy died
        if (!enemy.isAlive()) {
            result.append("\n🎉 Du besegrade ").append(enemy.getName()).append("!");
            return new CombatResult(false, true, result.toString(), enemy);
        }

        // The enemy strikes back.
        result.append("\n");
        String enemyAttackResult = enemy.attack(player);
        result.append(enemyAttackResult).append("\n");

        // Check if the player died
        if (!player.isAlive()) {
            result.append("\n💀 Du blev besegrad! GAME OVER!");
            return new CombatResult(false, true, result.toString(), enemy);
        }

        // Show status
        result.append("\n📊 Status:");
        result.append("\n   Din hälsa: ").append(player.getCurrentHealth())
                .append("/").append(player.getMaxHealth());
        result.append("\n   ").append(enemy.getName()).append(" hälsa: ")
                .append(enemy.getCurrentHealth()).append("/").append(enemy.getMaxHealth());

        return new CombatResult(true, false, result.toString(), enemy);
    }

    public CombatResult attemptFlee(Player player, Enemy enemy) {
        // Use constant for escape chance
        boolean success = RandomGenerator.rollPercent(Constants.FLEE_SUCCESS_CHANCE);

        if (success) {
            return new CombatResult(false, false,
                    "🏃 Du lyckas fly från " + enemy.getName() + "!", null);
        } else {
            // Failed escape - the enemy is allowed to attack
            StringBuilder result = new StringBuilder();
            result.append("❌ Du misslyckades att fly! ")
                    .append(enemy.getName()).append(" hinner ikapp dig!\n");

            String enemyAttackResult = enemy.attack(player);
            result.append(enemyAttackResult);

            if (!player.isAlive()) {
                result.append("\n💀 Du blev besegrad! GAME OVER!");
                return new CombatResult(false, true, result.toString(), enemy);
            }

            return new CombatResult(true, false, result.toString(), enemy);
        }
    }


    public static class CombatResult {
        private final boolean inCombat;
        private final boolean gameEnded;
        private final String message;
        private final Enemy enemy;

        public CombatResult(boolean inCombat, boolean gameEnded, String message, Enemy enemy) {
            this.inCombat = inCombat;
            this.gameEnded = gameEnded;
            this.message = message;
            this.enemy = enemy;
        }

        public boolean isInCombat() { return inCombat; }
        public boolean isGameEnded() { return gameEnded; }
        public String getMessage() { return message; }
        public Enemy getEnemy() { return enemy; }
    }
}