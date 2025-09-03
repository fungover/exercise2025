## Project Structure

- **entities/:** Player, enemies,items,tile?
- **map/:**  Dungeon grid,room,tiles -Dungeon generation and display
- **game/:** loop and input handling
- **service/:** combat and movement
- **utils/:** random number generation (maybe?)

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

### Use JUnit 5 to write unit tests for:

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

- > To create a new enemy Create a class with the enemy name inside
  package entities such as ex Goblin
  >- The new enemy need to extend Enemy and have a constructor
     with super("enemyName",int health,int damage) ex. super("Goblin"
     ,30,5)

- > inside DungeonGenerator find method addEnemies()
- > Inside addEnemies() we can add our new Enemy to the list like this:
  > - case 3 -> enemy = new newEnemy();
- > Make sure that: int roll = RandomGenerator.nextInt(0, 3);
  > - 3 = the amount of
      enemy's you have, this will make it roll between 0 and 2 with 3
      enemys
      you should have 0 - 2 cases

### Creating new Items

- > To create new Items its mostly the same as with new enemies
  first add your new items type to ItemType inside class Item

  > - the new item need to extend Item and have a constructor as such
  > - super("itemName","description",ItemType.newItemType)
  > - make sure to fill out the use method on your item.

  
