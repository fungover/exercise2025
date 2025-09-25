## Project Structure

- **entities/**: Player, Enemy subclasses, Item hierarchy, Tile
- **map/**:  Dungeon grid, rooms, tiles — generation and display
- **game/**: Game loop and input handling
- **service/**: Combat and movement services
- **utils/**: Random number generation

## Key features

> - **Turn-based gameplay:** with command input.

> - **Procedurally generated dungeons:** with walls, enemies, and items.

> - **Combat system:** with different enemy types.

> - **Inventory management:** with potions and weapons.

> - **Visual map display:** showing your position and surroundings.

## How to play?

1. **Movement**: **north,south,east,west** (or **n,s,e,w**)
2. **Inventory**: **inventory** or **inv** to see items/backpack
3. **Use items**: **use 1** (use first item in inventory)
4. **Look around:** **look** to see what's around
5. **Help:** **help** shows command list

## Game Mechanics

- **Combat:** turn-based: you choose to Attack, Use item or Flee
- **items:** are picked up automatically when you enter a tile
- **Health potions**: restore HP and are consumed when used
- **Map symbols:** `@` = Player?, `E` = Enemy,`I`=Item,`█` (U+2588) = Wall , `.` =
  Empty

## Testing

### Use JUnit 5 to write unit tests for

- [x] Movement logic
- [x] Combat calculations
- [x] Item effects
- [x] Map generation (basic validation)

## How to use this code

### changing the dungeon size

- can be done inside class GameLoop inside method start()
    - dungeon = new DungeonGenerator(10, 8); //10x8 dungeon

### Change amount of enemy / items that spawns

- can be done inside class DungeonGenerator inside the methods
  addEnemies() / addItems() by changing
    - int numEnemies = RandomGenerator.nextInt(3, 6); // 3 to 6 Enemies
    - int numItems = RandomGenerator.nextInt(3, 6);// 3 to 6 Items

### Creating new enemies

- To create a new enemy, create a class in `org.example.entities`, e.g., `Goblin`.
- The new enemy must extend `Enemy` and provide a constructor like:
  `super("Goblin",30,5)`

- Inside `addEnemies()`, add a new case:
  `case 3 -> enemy = new NewEnemy();`
- Ensure `int roll = RandomGenerator.nextInt(0, 3);` uses an exclusive upper bound.
  If you have 3 enemies, valid cases are 0, 1, and 2.

### Creating new Items

- > To create new Items its mostly the same as with new enemies
  first add your new items type to ItemType inside class Item

  > - the new item need to extend Item and have a constructor as such
  > - super("itemName","description",ItemType.newItemType)
  > - make sure to fill out the use method on your item.

  
