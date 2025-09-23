package items;

import entities.Item;
import entities.Player;

/**
 * Old sword - can be used as a weapon
 */
public class OldSword extends Item {
    private int damageBonus;
    private boolean equipped;

    public OldSword(String swordName, int damageBonus) {
        super(swordName,
                "Ett gammalt men fortfarande vässat svärd. Ger +" + damageBonus + " i skada.",
                false, '†');
        this.damageBonus = damageBonus;
        this.equipped = false;
    }

    @Override
    public String use(Player player) {
        if (!equipped) {
            equipped = true;
            player.addDamageBonus(damageBonus);
            return "Du griper tag i " + getName() + " och känner dess vikt i handen. " +
                    "Du är nu beväpnad! (+" + damageBonus + " skada)";
        } else {
            equipped = false;
            player.removeDamageBonus(damageBonus);
            return "Du stoppar undan " + getName() + ". Du är inte längre beväpnad.";
        }
    }

    public boolean isEquipped() {
        return equipped;
    }
}