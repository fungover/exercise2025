# Dungeon Crawler Game

A turn-based adventure game implemented in Java using Object-Oriented Programming principles. Players explore a pirate cave, collect treasures, and battle enemies through a command-line interface.

## Project Overview

This is a CLI-based dungeon crawler where players navigate through a 2D grid map, pick up items, manage inventory, and fight various enemy types. The architecture follows OOP principles with inheritance, polymorphism, interfaces, and separation of concerns.

## Project Structure

```
src/
├── main/java/
│   ├── entities/           # Core game entities
│   │   ├── Entity.java     # Abstract base class for Player and Enemy
│   │   ├── Player.java     # Player with inventory and combat abilities
│   │   ├── Enemy.java      # Abstract base class for all enemies
│   │   └── Item.java       # Abstract base class for all items
│   ├── enemies/            # Concrete enemy types
│   │   ├── Skeleton.java   # Undead skeleton with bone throw attack
│   │   ├── Spider.java     # Poison spider with venom bite
│   │   ├── Pirate.java     # Rival pirate with pistol
│   │   ├── Bat.java        # Vampire bat with blood drain
│   │   └── EnemyFactory.java # Factory for creating enemies
│   ├── items/              # Concrete item types
│   │   ├── GoldCoin.java   # Gold coins (display only)
│   │   ├── MagicKey.java   # Magic key for locks
│   │   ├── RumBottle.java  # Healing rum bottle
│   │   ├── PirateTreasure.java # Pirate treasures for display
│   │   ├── OldSword.java   # Weapons that provide damage bonus
│   │   └── PirateTreasureFactory.java # Factory for items
│   ├── map/                # Map management
│   │   ├── PirateCave.java # Main map with 2D grid
│   │   └── Tile.java       # Individual map tiles
│   ├── game/               # Game logic and user interaction
│   │   ├── GameManager.java    # Main game loop and control
│   │   └── InputHandler.java   # Command parsing and user input
│   ├── service/            # Business logic
│   │   ├── MovementService.java    # Movement logic and collision detection
│   │   ├── InventoryService.java   # Inventory management
│   │   └── CombatService.java      # Combat system and damage calculation
│   ├── interfaces/         # Interfaces for polymorphism
│   │   ├── Usable.java     # For items that can be used
│   │   ├── Combatable.java # For entities that can fight
│   │   ├── Displayable.java # For objects shown on map
│   │   ├── Equippable.java # For equipment that can be worn
│   │   └── Positionable.java # For objects with position
│   └── utils/              # Helper classes and constants
│       ├── RandomGenerator.java # Random number generation
│       └── Constants.java       # Game constants
└── test/java/              # JUnit 5 tests
    ├── service/            # Tests for business logic
    ├── items/              # Tests for item effects
    ├── map/                # Tests for map generation
    └── utils/              # Tests for utility classes
```

## OOP Principles

### Inheritance
- `Entity` → `Player`, `Enemy`
- `Enemy` → `Skeleton`, `Spider`, `Pirate`, `Bat`
- `Item` → `GoldCoin`, `MagicKey`, `RumBottle`, `PirateTreasure`, `OldSword`

### Polymorphism
- `Enemy.getSpecialAttack()` - different for each enemy type
- `Item.use()` - different effects for each item type
- `Enemy.getIdleMessage()` - unique descriptions per enemy

### Encapsulation
- Private attributes with public getter/setter methods
- Protected internal logic in service classes
- Controlled access through interfaces

### Abstraction
- Abstract methods in `Entity` and `Enemy`
- Interface implementations for common behaviors
- Factory pattern for object creation

### Interfaces
- `Usable` - implemented by items that can be used
- `Combatable` - implemented by entities that can fight
- `Displayable` - implemented by objects shown on map
- `Equippable` - implemented by equipment
- `Positionable` - implemented by objects with coordinates

## Quick Start for New Developers

### Adding a New Enemy Type

1. Create a class in `enemies/` package extending `Enemy`
2. Implement required abstract methods
3. Add to `EnemyFactory`

```java
public class NewEnemy extends Enemy {
    public NewEnemy() {
        super("Enemy Name", health, damage, 'X');
    }
    
    @Override
    public String getSpecialAttack(Player player) {
        // Implement special attack logic
        return "Special attack description";
    }
    
    @Override
    public String getIdleMessage() {
        return "Enemy idle behavior description";
    }
}
```

### Adding a New Item Type

1. Create a class in `items/` package extending `Item`
2. Implement the `use()` method
3. Add to `PirateTreasureFactory`

```java
public class NewItem extends Item {
    public NewItem() {
        super("Item Name", "Description", consumable, 'X');
    }
    
    @Override
    public String use(Player player) {
        // Implement item effect
        return "You used the item!";
    }
}
```

### Adding a New Service

1. Create a class in `service/` package
2. Implement business logic methods
3. Use in `GameManager` or other services
4. Create corresponding test class

```java
public class NewService {
    public ServiceResult performAction(Player player, /* parameters */) {
        // Implement business logic
        return new ServiceResult(success, message);
    }
}
```

## Game Commands

### Movement
- `north/n` - Move north
- `south/s` - Move south
- `east/e` - Move east
- `west/w` - Move west

### Items
- `pickup/take/get` - Pick up item
- `inventory/inv` - Show inventory
- `use [item]` - Use an item (e.g., "use rum", "use sword")

### Combat
- `attack/fight` - Attack enemy
- `flee/run` - Flee from combat

### Information
- `look/examine` - Examine area
- `help` - Show help

## Game Mechanics

### Combat System
- Turn-based combat
- Player attacks first, enemy counter-attacks
- 30% chance for special attacks
- 70% chance to successfully flee

### Item Types
- **Rum Bottles**: Heal player
- **Swords**: Provide damage bonus when equipped
- **Keys**: Open magical locks
- **Treasures**: Can be examined for value
- **Gold Coins**: Display value only

### Enemy Types
- **Skeleton**: Medium strength, bone throw attack
- **Spider**: Fast, poison bite attack
- **Pirate**: Strong, pistol shot (one-time use)
- **Bat**: Self-healing blood drain attack

## Testing

The project includes comprehensive JUnit 5 tests covering:

- **Movement logic** - Collision detection, boundary validation
- **Combat calculations** - Damage, death conditions, flee mechanics
- **Item effects** - Healing, equipment bonuses, consumables
- **Map generation** - Dimension validation, object placement

Run tests with: `mvn test`

## Technical Details

- **Java 11+** compatible
- **No external dependencies** for main game
- **JUnit 5** for testing
- **Text-based CLI interface**
- **Scanner for input handling**
- **2D array for map representation**

## Architecture Patterns

- **Factory Pattern**: `EnemyFactory`, `PirateTreasureFactory`
- **Service Layer Pattern**: Business logic separation
- **Command Pattern**: Input handling and game commands
- **Strategy Pattern**: Different enemy behaviors via polymorphism

## Constants and Configuration

Key game parameters are centralized in `utils.Constants`:
- Player starting stats
- Combat percentages
- Map dimensions
- Display symbols

## Future Extensibility

The system is designed for easy expansion:
- New enemy types via inheritance
- New item types via factory pattern
- New maps through `PirateCave` constructor
- New mechanics via service classes
- Additional game modes through `GameManager` variations

## Getting Started

1. Clone the repository
2. Compile: `mvn compile`
3. Run tests: `mvn test`
4. Run game: Create a main method that instantiates `GameManager` and calls `startGame()`

The game will prompt for your first name and automatically add "Captain" prefix (e.g., "John" becomes "Captain John").

## Contributing

When adding new features:
1. Follow existing package structure
2. Implement appropriate interfaces
3. Add comprehensive tests
4. Update this README if adding new concepts
5. Use constants instead of magic numbers
6. Follow Java naming conventions