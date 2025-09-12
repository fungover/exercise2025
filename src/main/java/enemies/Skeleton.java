package enemies;

import entities.Enemy;
import entities.Player;

/**
 * Skelett - en undead fiende som vaktar gamla piratgravar
 */
public class Skeleton extends Enemy {

    public Skeleton() {
        super("Skelett",
                40,   // hälsa
                8,    // skada
                'S',  // symbol
                "Skelettet klapprar mot dig med sina beniga fingrar!",
                "Skelettet kollapsar till en hög av gamla ben.");
    }

    @Override
    public String getSpecialAttack(Player player) {
        // Bone Throw - gör extra skada
        int boneDamage = getDamage() + 5;
        player.takeDamage(boneDamage);
        return "Skelettet kastar ett ben mot dig! Du tar " + boneDamage + " skada!";
    }

    @Override
    public String getIdleMessage() {
        String[] messages = {
                "Skelettet klapprar hotfullt med sina ben.",
                "Du hör klappret från beniga fingrar.",
                "Skelettet stirrar på dig med tomma ögonhålor.",
                "Undead energi flödar från skelettet."
        };
        return messages[(int)(Math.random() * messages.length)];
    }
}