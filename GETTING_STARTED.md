## Escape the Dark Cave - Text Adventure Game

### Overview
- A text-based adventure game implemented in Java using Object Oriented Programming principles.

#### Core Classes: 
Player, Enemy, Room, Item _(abstract)_
#### Subclasses:
Weapon, Healing, Key _(Inherit from Item)_

### Method Override
Each Item subclass implements displayInfo() differently:
 - Weapon: displays damage value.
 - Healing: displays healing amount.
 - Key: displays description.

### Interface
Usable interface for consumable items.

### Collections
- ArrayList< Items> in Inventory class
- Map<String, Room> for room exits
- List< Enemy> in Room class

### Text Management
Centralized text handling using utility classes:
- GameText.java: Story, UI messages, and general game text
- CombatText.java: Combat-specific messages and dialog
- Colors.java: Console color formatting

### Adding New Classes
#### Addning New Item Type:
1. Create class extending Item.
2. Implement displayInfo() method.
3. Optionally implement Usable interface if consumable.
4. Add to WorldBuilder for placement in game world.

#### Adding New Enemy:
1. Create new Enemy instance with different stats in WorldBuilder.
2. No code changes required in existing classes.

#### Adding New Room:
1. Create new Room instance in WorldBuilder.createWorld()
2. Connect using addExit() method.

### Testing
JUnit 5 tests included for:
- Movement logic between rooms
- Combat calculations and damage
- Item effects and healing
- Inventory management
- World creation validation