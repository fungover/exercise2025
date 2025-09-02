package ExerciseTwo.Entities;

public class Item {
    private final String type;
    private final int effect;

    public Item(String type, int effect) {
        this.type = type;
        this.effect = effect;
    }

    public String getType() {
        return type;
    }

    public int getEffect() {
        return effect;
    }
}
