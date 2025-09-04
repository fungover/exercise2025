# Classes and Structure
The project is organized into several classes that represent different parts of the game:

## Core Classes
* **App** – The entry point of the game. Handles the game loop, user input, and interactions between components.
* **DungeonMap** – Represents the game map with floors, walls, and the placement of objects and enemies.
* **Position** – Utility class for storing coordinates (x, y) on the map.

## Player and Entities
* Player – Represents the player, including name, health, damage, inventory, and position.
* Enemy – Base class for all enemies. Contains attributes like name, health, and damage.
* Goblin, Skeleton – Concrete enemy types that extend Enemy.
* Inventory – Manages the player’s items (swords, potions, etc.).

## Items
* ItemObject – Abstract class for all items.
* Potion – Restores the player’s health.
* Sword – Increases the player’s damage.

## Systems and Services
* CombatService – Handles turn-based combat between the player and enemies.
* RandomGeneration – Utility class for randomly placing items, walls, and enemies on the map.

## Utilities
* CommandUtils – Normalizes and interprets player commands.