# Development Notes
#### To get started with this project I began by setting up a file structure that felt logical and scalable.
I then started to break down the assignment into smaller requirements and mapped them to 
different parts of the codebase. 

# To do:
[x] Choose inheritance, or interface - NOT BOTH 

🧠 Some thoughts about it....

_*I initially mixed up abstract classes and interfaces, but TDD made the difference
between them obvious. I need all my enemies to run through the same loop. Therefore,
I want to test the logic in isolation. 
I want a contract to mock, not a shared base implementation. 
I suddenly realized the huge benefits of interfaces!!*_

[x] Know when to use javadoc-tool or just "///"

[x] test what I have built to this point and ask myself if I will embrace a TDD approach from here ??? 

[] add commands, to get the game moving. 

[x] create entities 

[] create a map

[] create items


### THE ASSIGNMENT / _ISSUE_

🧠 Objective
Design and implement a turn-based Dungeon Crawler game in Java using Object-Oriented Programming (OOP) principles. The game will run in the command-line interface (CLI), and user input will be processed line-by-line (i.e. after pressing Enter). Players explore a dungeon, encounter enemies, collect items, and manage their health and inventory.

✅ Core Requirements
📁 Project Structure
Organize your code into logical packages:

## Entities: Player, Enemy, Item, etc.

## map: Dungeon grid, rooms, tiles

## game: Game loop, input handling

## service: Game logic, combat, movement

## utils: Helpers, random generation

🧍 Entities
Implement the following core classes:

## Player: Attributes like name, health, position, inventory

## Enemy: Type, health, damage, position

## Item: Name, type (e.g. weapon, potion), effect

## Tile: Represents a cell in the dungeon (can be empty, wall, enemy, item)

Use encapsulation, inheritance, and polymorphism where appropriate. For example:

Create different enemy types.

🗺️ Dungeon Map
Represent the dungeon as a 2D grid of Tile objects or a linked graph.

Generate a simple map with walls, paths, and random item/enemy placement

Ensure the player starts at a valid position

🎮 Game Mechanics (Turn-Based)
Since real-time key detection isn't feasible in CLI, 
implement a turn-based command system:
* Accept commands like move north, attack, use potion, inventory, look, etc. 
* Parse user input using Scanner or similar 
* After each command, update the game state and display the result

Example turn:

> move east
You moved to a new tile. There's an enemy here!
> attack
You dealt 5 damage. Enemy HP: 10
> attack
Enemy defeated!
🧪 Testing
Use JUnit 5 to write unit tests for:

Movement logic
* Combat calculations
* Item effects 
* Map generation (basic validation)