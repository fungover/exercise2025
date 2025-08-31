package clone.rs.dungeon.enemys;

public abstract class Enemy {
  public abstract String name();

  public abstract int health();

  public abstract double damage();

  public abstract double level();

  public abstract double location(int x, int y);

  public void hit() {
    System.out.printf("%s hit! %s%n", name(), damage());
  }
}
