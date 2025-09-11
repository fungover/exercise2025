package items;

import entities.Item;
import entities.Player;

/**
 * Magisk nyckel - kan öppna magiska dörrar och skrin
 */
public class MagicKey extends Item {

    public MagicKey() {
        super("Magisk nyckel",
                "En mystisk nyckel som glöder med blå magi. Kan öppna magiska lås.",
                false, '♦');
    }

    @Override
    public String use(Player player) {
        return "Du håller upp den magiska nyckeln. Den pulserar med blå energi. " +
                "Använd 'unlock' kommandot framför magiska dörrar eller skrin.";
    }

    public boolean canUnlockMagic() {
        return true;
    }
}