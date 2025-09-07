package clone.rs.dungeon.character;

import clone.rs.dungeon.weapons.Hand;
import clone.rs.dungeon.weapons.Weapon;

public class Player extends Character {
  private Weapon weapon;
  private int experience = 0;

  public Player() {
    super();
    this.weapon = new Hand();
  }

  public Player(String name, double health, double level, Weapon weapon) {
    super(name, health, level);
    this.weapon = weapon;
  }

  public void checkLevel() {
    int expForNextLevel = (int) (level * level * 10);

    while (experience >= expForNextLevel) {
      experience -= expForNextLevel;
      level++;
      System.out.println("***Level up! You are now level " + level + "***");

      expForNextLevel = (int) (level * level * 10);
      health = getMaxHp();
    }
  }

  public void addExperience(int experience) {
    this.experience += experience;
    checkLevel();
  }

  public int getExp() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public double hit() {
    return weapon.damage();
  }

  public String getWeapon() {
    return weapon.name();
  }

  public int getMaxHp() {
    int lvl = (int) getLevel();
    return 10 + (lvl - 3);
  }

  public void rest() {

  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }
}
