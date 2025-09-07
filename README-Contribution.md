# Dungeon Game

## Overview
A CLI-based dungeon game in Java with a 5x5 grid. Players move, fight enemies (`Goblin`, `Troll`), and collect items (`Potion`)Supports commands: `move <direction>`, `attack <x> <y>`, `pickup <x> <y>`, `status`, `quit`.

## Structure
- **entities**: `Player`, `Tile`, `TileType`, `enemies` (`Enemy`, `Goblin`, `Troll`), `items` (`Item`, `Potion`), `behaviors` (`CombatBehavior`, `BasicCombat`).
- **map**: `Dungeon` (game map), `MapGenerator`, `BasicMapGenerator` (places 1 enemy, 1 `Potion`).
- **service**: `MovementService`, `CombatService`, `ItemService`, `InputValidator`.
- **game**: `Game` (game loop), `CommandParser` (command parsing).
- **utils**: `RandomGenerator`.
- **App.java**: Entry point.
- **Tests**: JUnit 5 in `src/test/java/org/example`.

## Run
- **Build**: `mvn compile` or `./gradlew build`.
- **Production**: `java -cp target/classes org.example.App` (random enemy/potion).
- **Test**: Execute testfiles in `src/test/java/org.example` using appropriate IDE
- **Tests**: `mvn test` or `./gradlew test`.

## Adding Classes
- **New Enemy** (in `entities.enemies`):
  ```java
  public class NewEnemy implements Enemy {
      private int health = 15;
      private final int damage = 7;
      public String getName() { return "NewEnemy"; }
      public int getHealth() { return health; }
      public void setHealth(int health) { this.health = health; }
      public int getDamage() { return damage; }
      public boolean isAlive() { return health > 0; }
      public void takeDamage(int damage) { this.health -= damage; }
  }
  ```
  Update `BasicMapGenerator`: Add `NewEnemy` to `Enemy` array.
- **New Item** (in `entities.items`):
  ```java
  public class NewItem implements Item {
      private final String name;
      public NewItem(String name) { this.name = name; }
      public String getName() { return name; }
  }
  ```
  Update `BasicMapGenerator` and `ItemService`.
- **New Map Generator** (in `map`):
  ```java
  public class CustomMapGenerator implements MapGenerator {
      public Tile[][] generate(int width, int height) {
          // Custom logic
          return new Tile[height][width];
      }
  }
  ```
  Use in `App.java` or tests.
- **Guidelines**: Implement interfaces, use `InputValidator`, write JUnit tests.


