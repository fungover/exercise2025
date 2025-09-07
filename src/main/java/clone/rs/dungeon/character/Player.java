package clone.rs.dungeon.character;

import clone.rs.dungeon.weapons.Hand;
import clone.rs.dungeon.weapons.Weapon;

public class Player extends Character {
  private Weapon weapon;

  public Player() {
    super();
    this.weapon = new Hand() {
    };
  }

  public Player(String name, double health, double level, Weapon weapon) {
    super(name, health, level);
    this.weapon = weapon;
  }

  public double hit() {
    return weapon.damage();
  }

  public String getWeapon() {
    return weapon.name();
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }
}
