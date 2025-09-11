package items;

import entities.Item;
import entities.Player;

/**
 * Piratskatt - värdefull men mystisk
 */
public class PirateTreasure extends Item {
    private String treasureType;
    private int value;

    public PirateTreasure(String treasureType, String description, int value) {
        super("Piratskatt: " + treasureType, description, false, '☆');
        this.treasureType = treasureType;
        this.value = value;
    }

    @Override
    public String use(Player player) {
        return "Du undersöker " + treasureType + " noggrant. " + getDescription() +
                " Den verkar vara värd omkring " + value + " guld, men du bör spara den för senare.";
    }

    public String getTreasureType() {
        return treasureType;
    }

    public int getValue() {
        return value;
    }
}