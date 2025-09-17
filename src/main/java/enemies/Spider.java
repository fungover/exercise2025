package enemies;

import entities.Enemy;
import entities.Player;

/**
 * Spider - fast and poisonous spider hiding in the dark corners of the cave
 */
public class Spider extends Enemy {
    private boolean hasPoison;

    public Spider() {
        super("Giftspindel",
                25,   // låg hälsa men snabb
                6,    // låg grundskada
                's',  // symbol (liten s)
                "Spindeln biter dig med sina giftiga huggtänder!",
                "Spindeln krullar ihop sig och dör.");
        this.hasPoison = true;
    }

    @Override
    public String getSpecialAttack(Player player) {
        if (hasPoison) {
            // Poison bite - less damage but does extra damage next turn
            int poisonDamage = getDamage() + 3;
            player.takeDamage(poisonDamage);
            return "Spindeln biter dig med gift! Du tar " + poisonDamage +
                    " skada och känner giftet sprida sig genom kroppen!";
        } else {
            // Web attack - normal damage but scary message
            player.takeDamage(getDamage());
            return "Spindeln kastar ett nät mot dig och biter! Du tar " +
                    getDamage() + " skada!";
        }
    }

    @Override
    public String getIdleMessage() {
        String[] messages = {
                "Spindeln kryper fram och tillbaka nervöst.",
                "Du ser spindeln vika sina långa ben.",
                "Spindeln gnisslar med sina käkar.",
                "Giftdroppar sipprar från spindelns huggtänder."
        };
        return messages[(int)(Math.random() * messages.length)];
    }

}