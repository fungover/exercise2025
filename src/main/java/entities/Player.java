package entities;

public class Player extends Character{

    public Player(int x, int y, int health, int damage) {
        super(x, y, health, damage);
    }

    @Override
    public String toString() {
        return "P";
    }
}
