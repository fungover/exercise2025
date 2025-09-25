package entities;

public interface Enemy extends Creature {
    int getX();
    int getY();
    int getDamage();
    String getType();
}
