package items;

import entities.Item;
import entities.Player;

/**
 * Gold coins - valuable but cannot be used directly
 */
public class GoldCoin extends Item {
    private int value;

    public GoldCoin(int value) {
        super("Guldmynt", "beskrivning", false, '$');
        this.value = value;
    }

    @Override
    public String use(Player player) {
        return "Du håller upp " + getName() + " och betraktar det i ljuset. " +
                "Det är värt " + value + " guld, men du kan inte använda det här.";
    }
}
