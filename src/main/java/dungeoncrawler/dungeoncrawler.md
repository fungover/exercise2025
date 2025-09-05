## DungeonCrawler ##
This simple CLI game invites the player to explore a dungeon
made of an array of Tiles. The array of Tiles is created on creation of a new dungeon.
Each tile has a type that indicates
what is in it, eg a wall, enemy, weapon or potion. An empty tile is simply not initialised.

There are two types of weapons, daggers and swords. They are both of the class
`Weapon` where the String type indicates what sort of weapon it is. There is one type
of potion that adds hp to the player. Both Weapons and Potions extend the class Entity to make sure
they have a position.

So far there are 2 types of enemies, `Trolls` and `Giants`. They are subclasses
to `Enemy` where the specifics regarding the species, like their strength and hp,
is set in the constructor. To create a new monster just create a class that extends
Enemy and decide on the new monsters parameters. The class `Enemy` is a subclass to `Entity`, as 
is `Player` since they also need a position.

The game starts in the `Game` class, which creates a new dungeon, a `Player`, and a `GameLogic`. All enemies and items
(weapons and potions) are generated and placed. `Game.start()` calls `GameLogic.runGame()`,
which drives the rest of the game.
All movement happens in the `Movement` class, and all enemy interaction happens in `Combat`.