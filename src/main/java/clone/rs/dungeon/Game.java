package clone.rs.dungeon;

import clone.rs.dungeon.enemys.Enemy;
import clone.rs.dungeon.enemys.Goblin;
import clone.rs.dungeon.weapons.Hand;
import clone.rs.dungeon.weapons.Weapon;

public class Game {
  final private String name;

  public Game(String name) {
    this.name = name;
  }

  public void run() {
    System.out.printf("Welcome to %s%n", this.name);
    Weapon hand = new Hand();
    Enemy goblin = new Goblin(hand.damage());
    goblin.hit();
  }

  static void main(){
    Game game = new Game("RS clone dungeon");
    game.run();
  }
}
