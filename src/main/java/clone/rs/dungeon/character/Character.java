package clone.rs.dungeon.character;

public class Character {
  protected String name;
  protected double health;
  protected double level;


  public Character(String name, double health, double level) {
    this.name = name;
    this.health = health;
    this.level = level;
  }

  public void attack(Character target) {
    System.out.printf("%s Hit %s with 1 damage %n", name, target.name);
  }
}
