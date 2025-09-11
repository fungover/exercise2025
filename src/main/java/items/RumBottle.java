package items;

import entities.Item;
import entities.Player;

/**
 * Rom-flaska - läker spelaren (piraternas hälsodryck!)
 */
public class RumBottle extends Item {
    private int healingAmount;
    public RumBottle(String bottleType, int healingAmount) {
        super("Flaska med " + bottleType + " rom",
                "En flaska fylld med stark " + bottleType + " rom. Läker " + healingAmount + " hälsopoäng.",
                true, '!');
        this.healingAmount = healingAmount;
    }

    @Override
    public String use(Player player) {
        int oldHp = player.getCurrentHealth();
        player.heal(healingAmount);
        int newHp = player.getCurrentHealth();
        int actualHealing = newHp - oldHp;

        if (actualHealing > 0) {
            return "Du dricker den starka rommen och känner värmen sprida sig genom kroppen! " +
                    "Du återfår " + actualHealing + " hälsopoäng. Arrr!";
        } else {
            return "Du dricker rommen men känner ingen förbättring. Du är redan vid full styrka, kapten!";
        }
    }

    public int getHealingAmount() {
        return healingAmount;
    }
}