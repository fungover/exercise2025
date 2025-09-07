package clone.rs.dungeon.character;

import clone.rs.dungeon.weapons.Weapon;

public class Enemy extends Character {
  private final double damage;

  public Enemy(String name, double health, double level, double damage) {
    super(name, health, level);
    this.damage = damage;
  }

  public double hit() {
    return damage;
  }
}
