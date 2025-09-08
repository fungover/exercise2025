### Add additional rooms
Rooms can be added to the game by extending the abstract class “Dungeon".
Every room contains a String [][] with a map over the room and it contains.
Ex:
super(new String[][]{
{"#", "#", "#", "#", "#"},
{"#", " ", " ", "@", "#"},
{"#", " ", "G", " ", "#"},
{"#", " ", " ", "E", "#"},
{"#", "#", "#", "D", "#"}});
The map consists of different tiles:
"#": wall
"@": player
"D": door
"E": enemy
"G": coin
"P": potion
"S": sword
"T": treasure
“ “: path
The player must always be positioned on the map.
To change room, use “D”
If you want the player to encounter different items, you can add additional tiles.
Every tile is connected to an emoji that is printed for a more user-friendly experience.
If additional rooms are added they also must be passed into the game in the main file.

### Creating enemies:
If you want to create additional enemies, you extend the abstract class of “Enemy”. You also must add your enemy to the constructor for “Enemies” class. It created a list for all enemies in the game. The level of the game is never the same, it depends on which enemies the player till encounter, which is randomized.

### Creating item:
If you want to create additional item, you extend the abstract class of “Items”.
The item can later be added to the inventory when encountered in game.
In the game logic you have
Items can be added to “Inventory” or “Weapons”.
You also must add tiles and checks in game logic if further items are added.

### General functions:
The game consists of logic for game with game loop, movement and combat.
The game also includes logic to handle input from the player and inventory. 