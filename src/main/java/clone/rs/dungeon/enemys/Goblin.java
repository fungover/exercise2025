package clone.rs.dungeon.enemys;

public class Goblin extends Enemy {
  private final double weapon;
  public Goblin(double weapon) {
    super();
    this.weapon = weapon;
  }

  @Override
  public String name() {
    return "Goblin";
  }

  @Override
  public int health() {
    return 10;
  }

  @Override
  public double damage() {
    return weapon;
  }

  @Override
  public double level() {
    return 3;
  }

  @Override
  public double location(int x, int y) {
    return x.y;
  }
}
