package ExerciseTwo.Entities.Inventory;

public abstract class Item {
    protected String type;
    protected int effect;

    public Item(String type, int effect) {
        this.type = type;
        this.effect = effect;
    }

    public String getType(){
        return type;
    }

    public int getEffect() {
        return effect;
    }

    public abstract void itemFound();

}
