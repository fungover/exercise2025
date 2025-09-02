package clone.rs.dungeon;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.weapons.Hand;
import clone.rs.dungeon.weapons.Weapon;

public class Game {
  final private String name;

  public Game(String name) {
    this.name = name;
  }

  public void run() {
    System.out.printf("Welcome to %s%nEnter your name: ", this.name);
    String name = System.console().readLine();

    Player player1 = new Player(name, 10, 3);
    Enemy goblin = new Enemy("Goblin", 5, 3);

    player1.attack(goblin);
  }

  static void main(){
    Game game = new Game("RS clone dungeon");
    game.run();
  }
}
