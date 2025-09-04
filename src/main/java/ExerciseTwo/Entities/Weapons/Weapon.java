package ExerciseTwo.Entities.Weapons;

public abstract class Weapon {

    protected String description;
    protected int damage;

    public Weapon(String description, int damage) {
        this.description = description;
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public int getDamage() {
        return damage;
    }

}
