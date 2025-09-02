package clone.rs.dungeon.character;

public class Enemy extends Character {


  public Enemy(String name, double health, double level) {
    super(name, health, level);
  }

  public void hit() {
    System.out.printf("%s hit! %n", name);
  }
}
