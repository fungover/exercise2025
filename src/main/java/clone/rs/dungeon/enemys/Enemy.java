package clone.rs.dungeon.enemys;

public abstract class Enemy {
  public abstract String name();
  public abstract int health();
  public abstract double damage();
  // level/10 = damage
  public abstract double level();

  public void hit(){
    System.out.printf("%s hit! with %s%n", name(), damage());
  }

}
