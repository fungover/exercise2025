package entities;

public abstract class Item {
    private String name;
    private String type;
    private int effect;

    public Item(String name, String type, int effectValue) {
        this.name = name;
        this.type = type;
        this.effect = effectValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getEffect() {
        return effect;
    }

    public abstract void applyEffect(Player player);

}
