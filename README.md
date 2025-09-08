# Dungeon Crawler (CLI, Java)

A simple turn-based dungeon crawler game implemented in Java 17+ using Object-Oriented Programming principles.  
The game runs in the command-line interface (CLI) and is fully tested with JUnit 5.

---

## 🎮 How to Play

1. Start the game:
   ```bash
   mvn exec:java
   ```

2. Enter your player name when prompted.

3. Use commands to explore the dungeon:
    - **move north/south/east/west** or **n/s/e/w** → Move around the dungeon
    - **attack** → Attack an enemy on your tile
    - **use <item>** → Use an item by name or index (e.g. `use potion` or `use 0`)
    - **inventory** → Show your items
    - **look** → Show info about current and nearby tiles
    - **help** → Show available commands
    - **quit** → Exit the game

4. **Win conditions**
    - Reach the **EXIT** tile, or
    - Defeat all enemies

5. **Lose condition**
    - Your health drops to `0` or below → *Game Over*.

---

## 🧪 Run Tests

All unit tests (JUnit 5) can be executed with:

```bash
mvn test
```

---

## 📦 Package Structure

```
src/main/java
├── entities    # Player, Enemy (Goblin, Skeleton, Orc), Item (Weapon, HealingPotion)
├── game        # Game loop, Main entry point
├── map         # Dungeon, Tile, TileType
├── service     # Game logic: CombatService, MovementService, InventoryService, MapGenerator
└── utils       # Helpers: Rng, FakeRng (for tests), Printer, InputParser
```

---

## 📄 Class Overview

- **entities.Player** → Player character (health, position, inventory, baseDamage)
- **entities.Enemy** → Abstract enemy with subclasses *Goblin*, *Skeleton*, *Orc*
- **entities.Item** → Abstract item with *Weapon* (damage boost) and *HealingPotion* (heal)
- **map.Dungeon** → 2D grid of `Tile`, manages enemies/items placement and walls
- **map.Tile** → Holds type (EMPTY, WALL, ITEM, ENEMY, START, EXIT) and references to item/enemy
- **service.CombatService** → Handles damage calculation, attacks, death/removal of enemies
- **service.MovementService** → Validates and updates player movement
- **service.InventoryService** → Handles adding, using, removing items
- **service.MapGenerator** → Creates a dungeon with walls, enemies, items, start/exit
- **utils.Rng** → Random number wrapper, *FakeRng* for deterministic tests
- **utils.InputParser** → Parses player commands
- **utils.Printer** → CLI output (status, inventory, help, tile info)
- **game.Game** → Main game loop (turn-based)
- **game.Main** → Program entry point

---

## 🚀 Requirements

- Java 17 or newer
- Maven 3.8+

---

## ✅ Example Run

```
Welcome to the Dungeon!
Enter your name: Hero
Type 'help' for available commands.

> look
You see a dark corridor. There is an enemy nearby.
> attack
You hit the Goblin for 5 damage. (Enemy HP: 5)
> attack
Goblin is defeated!
> move east
You move east.
```
