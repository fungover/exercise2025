package service;

import entities.Player;
import entities.Enemy;

/**
 * CombatService hanterar all strid mellan spelaren och fiender
 */
public class CombatService {

    /**
     * Initierar strid mellan spelare och fiende
     */
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

    /**
     * Spelaren attackerar fienden
     */
    public CombatResult playerAttack(Player player, Enemy enemy) {
        if (!enemy.isAlive()) {
            return new CombatResult(false, false,
                    enemy.getName() + " är redan besegrad och kan inte attackeras.", null);
        }

        StringBuilder result = new StringBuilder();

        // Spelaren attackerar
        int playerDamage = player.getTotalDamage();
        result.append("👊 Du attackerar ").append(enemy.getName()).append(" för ")
                .append(playerDamage).append(" skada!\n");

        String damageResult = enemy.takeDamageAndCheck(playerDamage);
        result.append(damageResult).append("\n");

        // Kolla om fienden dog
        if (!enemy.isAlive()) {
            result.append("\n🎉 Du besegrade ").append(enemy.getName()).append("!");
            return new CombatResult(true, true, result.toString(), enemy);
        }

        // Fienden slår tillbaka
        result.append("\n");
        String enemyAttackResult = enemy.attack(player);
        result.append(enemyAttackResult).append("\n");

        // Kolla om spelaren dog
        if (!player.isAlive()) {
            result.append("\n💀 Du blev besegrad! GAME OVER!");
            return new CombatResult(false, true, result.toString(), enemy);
        }

        // Visa status
        result.append("\n📊 Status:");
        result.append("\n   Din hälsa: ").append(player.getCurrentHealth())
                .append("/").append(player.getMaxHealth());
        result.append("\n   ").append(enemy.getName()).append(" hälsa: ")
                .append(enemy.getCurrentHealth()).append("/").append(enemy.getMaxHealth());

        return new CombatResult(true, false, result.toString(), enemy);
    }

    /**
     * Spelaren försöker fly från striden
     */
    public CombatResult attemptFlee(Player player, Enemy enemy) {
        // 70% chans att lyckas fly
        boolean success = Math.random() < 0.7;

        if (success) {
            return new CombatResult(false, false,
                    "🏃 Du lyckas fly från " + enemy.getName() + "!", null);
        } else {
            // Misslyckad flykt - fienden får attackera
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

    /**
     * Resultat av en stridhandling
     */
    public static class CombatResult {
        private final boolean inCombat;      // Är striden fortfarande aktiv?
        private final boolean gameEnded;     // Slutade spelet (spelare död)?
        private final String message;       // Meddelande att visa
        private final Enemy enemy;          // Fienden (null om striden är över)

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