# Dungeon Game

## Description
This is a text-based RPG inspired by RuneScape.  
Players can:
- Create a character
- Move between different locations (Lumbridge, Varrock, Dwarven Mine)
- Fight enemies
- Collect and use items (weapons, food)
- Complete quests (e.g., Dragon Slayer)

## Project Structure
- `clone.rs.dungeon.character` – classes for Player and Enemy
- `clone.rs.dungeon.weapons` – weapons and items
- `clone.rs.dungeon.locations` – different locations
- `clone.rs.dungeon.controls` – game logic, menus, and quests
- `clone.rs.dungeon.Items` – item and inventory management
- `SaveLoad.java` – save/load player data using YAML

## Keynotes for Developers
- **Enemies and items are created within each location**. Each location defines its own `enemy()` and `item()` methods to randomly spawn them.
- **Weapon parameters** (damage, name, etc.) are currently defined in the `Weapon` class based on the item name. This is not the ideal design but works for now. Consider refactoring in the future.
- Combat logic is centralized in `Combat.java`. Avoid duplicating code.
- Player inventory is handled with the `Item` class; items have properties like `wearable`, `food`, and `effect`.  