# Classes and Structure
The project is organized into several classes that represent different parts of the game:

## Core Classes
* **App** – The entry point of the game. Handles the game loop, user input, and interactions between components.
* **Game** – Coordinates the main game logic. Initializes the map, player, items, and enemies, and runs the turn-based loop by processing commands.
* **DungeonMap** – Represents the game map as a 2D grid of tiles (walls and floors). Responsible for movement validation, wall placement, and rendering the map with players, enemies, and items.
* **Position** – Utility class for storing coordinates (x, y) on the map and calculating adjacent positions.

## Player and Entities
* **Player** – Represents the player, including name, health, damage, inventory, and position.
* **Enemy** – Base class for all enemies. Contains attributes like name, health, and damage.
* **Goblin**, **Skeleton** – Concrete enemy types that extend Enemy.
* **Inventory** – Manages the player’s items (swords, potions, etc.).

## Items
* **ItemObject** – Abstract class for all items.
* **Potion** – Restores the player’s health.
* **Sword** – Increases the player’s damage.

## Systems and Services
* **CombatService** – Handles turn-based combat between the player and enemies.
* **PickupService** – Manages item collection when the player moves onto a tile containing an item.
* **RandomGeneration** – Utility class for randomly placing items, walls, and enemies on the map. Ensures no overlap with the player’s start position or other objects.

## Utilities
* **CommandUtils** – Normalizes and interprets player commands (e.g., movement, attacking, using items).
