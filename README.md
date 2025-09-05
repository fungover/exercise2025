# Farmageddon – Java CLI Game

An adventure game built in Java using Object-Oriented Programming principles.

Players explore a Farmageddon barn, encounter enemies, collect items, and fight to survive — all through a command-line interface.

To win the game you have to find the manifesto and use it to save the day.

---

## Project Structure

The code is organized into logical packages for clarity and modularity:

## Project Structure

```plaintext
src/
└── main/
    └── java/
        └── org.example/
            ├── entities/      # Enemy, Player, Item, Weapon, etc.
            ├── game/          # Game and input handling
            ├── map/           # Farmageddon map and tile types
            ├── service/       # Game mechanics: combat, movement, etc.
            ├── utils/         # Random generators
            └── App.java       # Main class that starts the game

```
---

## Entities

### Player
- Attributes: name, health, maxHealth, position, and inventory.
- Can move, attack, and use items

### Enemy (Superclass)
- Subclasses like `DroolingDog`, `GiantHeadlessChicken`
- Abstract class with attributes: name, health, maxHealth, damage, and position.

### Hostile (Interface)
- Requires `getDamage()` and `getName()`
- Implemented by classes like `DroolingDog`, `GiantHeadlessChicken`

### Item (Superclass)
- Abstract class with name and position
- Subclasses like: `Pitchfork`, `Healing Milk`, 

### Weapon (Interface)
- Requires `getDamage()` and `getName()`
- Implemented by classes like `Pitchfork`, `TestWeapon`, `Manifesto`

---

## Dungeon Map

- Represented as a 2D array grid of `Tile` objects
- Each `Tile` has a `Type` enum that defines its role in the map:
    - An enemy
    - An item
    - A wall 
    - A path
    - A Player start: safe starting location
  
- Player navigates using directional commands

### Map Generation

- The map is randomly generated with a mix of `PATH` and `WALL` tiles.
- Enemies and items are placed on valid `PATH` tiles using helper methods like `placeEnemy()` and `placeItem()`.
- The player always starts at tile `(0, 0)`, which is explicitly set to `PLAYER_START`.

---

## Game Mechanics

The game is turn-based and runs in the terminal:

- Example Commands: `move north`, `health`, `use healingMilk`, `inventory`, `look`
- Input is handled line-by-line using Console.readLine(), allowing the player to enter commands interactively in the terminal.
- After each command, the game state updates and feedback is printed

### Example Gameplay

```plaintext
Good luck, brave farmer. The animals are waiting...
--------------------------------------------------

> move north
You can't move outside the farm!

> move south
You moved to (0, 1)
Nothing special here.

> move east
You moved to (1, 1)
You picked up: Rusty Pitchfork!
The item has been added to your inventory.
>

```
---

## Testing

JUnit 5 is used for unit testing key components:

- ✅ Movement logic
- ✅ Combat calculations
- ✅ Item effects
- ✅ Map generation and validation