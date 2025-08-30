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
4. **Look around:** **look** to see whats around
5. **Help:** **help** shows command list

## Game Mechanics

- **Combat:** automatic? maybe
- **items**:are automatically picked up? maybe
- **Health potions**: restore xx HP and are consumed when used
- **Map symbols**: `@` = Player?, `E` = Enemy,`I`=Item,`█` = Wall (uniCode
  U+2588), `.` = Empty

## Testing

### Use JUnit 5 to write unit tests for:

- [ ] Movement logic
- [ ] Combat calculations
- [ ] Item effects
- [ ] Map generation (basic validation)

