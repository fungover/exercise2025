package enemies;

import entities.Enemy;
import entities.Player;

/**
 * Pirate - en rival pirat med svärd och pistol
 */
public class Pirate extends Enemy {
    private boolean hasPistol;

    public Pirate() {
        super("Rival Pirat",
                60,   // hög hälsa
                12,   // hög skada
                'P',  // symbol
                "Piraten hugger mot dig med sin sabel!",
                "Piraten faller med ett sista 'Arrr!' och ger upp andan.");
        this.hasPistol = true;
    }

    @Override
    public String getSpecialAttack(Player player) {
        if (hasPistol) {
            // Pistol shot - mycket hög skada men pistolen blir tom
            int pistolDamage = getDamage() + 8;
            player.takeDamage(pistolDamage);
            hasPistol = false;
            return "Piraten skjuter med sin pistol! BANG! Du tar " + pistolDamage +
                    " skada! (Pistolen är nu tom)";
        } else {
            // Desperate slash - hög skada när han är desperat
            int slashDamage = getDamage() + 4;
            player.takeDamage(slashDamage);
            return "Piraten gör en desperat hugg med sabeln! Du tar " + slashDamage + " skada!";
        }
    }

    @Override
    public String getIdleMessage() {
        String[] messages = {
                "Piraten svänger hotfullt sin sabel.",
                "Du hör piraten muttrar 'Arrr, matey!'",
                "Piraten justerar sitt bandana och stirrar stint på dig.",
                "Piraten snurrar på sin mustasch medan han planerar sin attack."
        };
        return messages[(int)(Math.random() * messages.length)];
    }

    public boolean hasPistol() {
        return hasPistol;
    }
}