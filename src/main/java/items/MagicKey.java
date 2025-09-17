package items;

import entities.Item;
import entities.Player;

/**
 * Magic key - can open magical doors and chests
 */
public class MagicKey extends Item {

    public MagicKey() {
        super("Magisk nyckel",
                "En mystisk nyckel som glöder med blå magi. Kan öppna magiska lås.",
                false, '♦');
    }

    @Override
    public String use(Player player) {
        return "Nyckeln pulserar med blå energi. Ha den i ditt inventarium så används den automatiskt vid magiska lås.";
    }

}