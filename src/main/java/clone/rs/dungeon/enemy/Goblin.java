package clone.rs.dungeon.enemy;

public class Goblin extends Enemy {
  @Override
  public String name() {
    return "Goblin";
  }

  @Override
  public int health() {
    return 10;
  }

  @Override
  public int damage() {
    return 2;
  }

  @Override
  public int level() {
    return 3;
  }
}
