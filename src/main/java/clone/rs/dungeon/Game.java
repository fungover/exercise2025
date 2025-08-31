package clone.rs.dungeon;

import clone.rs.dungeon.enemy.Enemy;
import clone.rs.dungeon.enemy.Goblin;

public class Game {
  final private String name;

  public Game(String name) {
    this.name = name;
  }

  public void run() {
    System.out.printf("Welcome to %s%n", this.name);
    Enemy goblin = new Goblin();
    goblin.hit();
  }

  static void main(){
    Game game = new Game("RS clone dungeon");
    game.run();
  }
}
