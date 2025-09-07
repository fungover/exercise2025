package clone.rs.dungeon.character;

public class Character {
  protected String name;
  protected double health;
  protected double level;

  public Character() {} //SnakeYAML
  public Character(String name, double health, double level) {
    this.name = name;
    this.health = health;
    this.level = level;
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public double getHealth() { return health; }
  public void setHealth(double health) { this.health = health; }

  public double getLevel() { return level; }
  public void setLevel(double level) { this.level = level; }

  public void attack(Character target) {
    System.out.printf("%s Hit %s with 1 damage %n", name, target.name);
  }
}
