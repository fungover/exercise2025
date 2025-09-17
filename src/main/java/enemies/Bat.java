package enemies;

import entities.Enemy;
import entities.Player;

/**
 * Bat - fast vampire bat that sucks blood and heals itself
 */
public class Bat extends Enemy {

    public Bat() {
        super("Vampyrfladdermus",
                20,   // mycket låg hälsa
                5,    // låg skada
                'B',  // symbol
                "Fladdermusen flyger mot ditt ansikte och biter dig i halsen!",
                "Fladdermusen faller till marken och dör.");
    }

    @Override
    public String getSpecialAttack(Player player) {
        // Blood drain - sucks blood and heals itself
        int drainDamage = getDamage() + 2;
        player.takeDamage(drainDamage);

        // Heal yourself for half the damage
        int healAmount = drainDamage / 2;
        heal(healAmount);

        return "Fladdermusen suger ditt blod! Du tar " + drainDamage +
                " skada och fladdermusen läker sig för " + healAmount + " hälsa!";
    }

    @Override
    public String getIdleMessage() {
        String[] messages = {
                "Fladdermusen flaxar nervöst med vingarna.",
                "Du hör fladdermusens skrik eka i grottan.",
                "Fladdermusen hänger upp och ner och iakttar dig.",
                "Små röda ögon glittrar i mörkret."
        };
        return messages[(int)(Math.random() * messages.length)];
    }
}