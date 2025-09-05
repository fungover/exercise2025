package Enemy;

import Entities.Character;

public abstract class Enemy extends Character {
    public Enemy(String name, int health, int attack, int x, int y) {
        super(name, health, attack, x, y);
    }

    @Override
    public String toString() {
        return getName() + " (HP:" + getHealth() +
                ", ATK:" + getAttack() +
                ", Pos:[" + getX() + "," + getY() + "])";
    }
}
