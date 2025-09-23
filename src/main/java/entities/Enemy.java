package entities;

public class Enemy extends Character{

    public Enemy(int x, int y, int health, int damage) {
        super(x, y, health, damage);
    }

    @Override
    public String toString() {
        return "E";
    }
}
