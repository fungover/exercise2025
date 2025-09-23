# 🎮 Climb to Victory Quest - Enhanced Edition

## ✨ New Features Added

### 📦 **Inventory System**

- **Item Collection**: Find items scattered throughout the world
- **Inventory Management**: Store and track collected items
- **Auto-Equip**: Weapons automatically equip when collected
- **Item Usage**: Use health potions to restore HP

### 🗡️ **Enhanced Combat**

- **Weapon System**: Weapons increase damage output
- **Combat Service**: Professional damage calculation with randomness
- **Balanced Combat**: Enemies and players have varied damage ranges

### 🎯 **Items Available**

- **🗡️ Village Sword** (Village): +10 damage weapon from blacksmith
- **🩸 Health Potion** (Village): Restores 30 health points
- **💎 Mountain Diamond** (Mountain): Valuable treasure (100 value)
- **⛺ Camping Supplies** (Forest): Useful camping gear (50 value)
- **🔑 Golden Key** (Dungeon): Drops from defeated Skeleton Guardian

### 🎮 **New Commands**

- **`inventory`** or **`inv`**: View your inventory
- **`collect`** or **`take`**: Pick up items in current area
- **`use`**: Use health potions when injured
- **`look`**: Examine current area for items and enemies

### 🏗️ **Technical Improvements**

#### **Entity Classes**

- **`Item.java`**: Complete item system with types (weapon, potion, key, treasure)
- **`Tile.java`**: Grid system for future dungeon exploration
- **Enhanced `Player.java`**: Full inventory management and item handling

#### **Service Layer**

- **`CombatService.java`**: Professional combat calculations
- **Damage Modifiers**: Weapons affect combat effectiveness
- **Potion System**: Health restoration mechanics

#### **Grid System**

- **Simple 3x3 grid** in `Maps.java` for dungeon exploration
- **Tile-based movement** ready for expansion
- **Visual grid display** with player position

#### **Comprehensive Testing**

- **`PlayerTest.java`**: Movement, combat, inventory tests
- **`ItemTest.java`**: Item creation and type checking
- **`CombatServiceTest.java`**: Combat mechanics and potion usage
- **13+ Unit Tests**: All passing ✅

## 🎯 **Movement System**

- **Hub-and-spoke pattern**: Movement restricted to center areas (Village/Bridge)
- **No diagonal movement**: Players must go through centers to reach side areas
- **Clear error messages**: Helpful feedback for blocked movements

## 🏆 **Game Flow**

1. **Start** at Home base
2. **Climb** to Village and collect health potion
3. **Explore** side areas (Dungeon, Lake) to fight enemies and find treasures
4. **Battle** Skeleton Guardian in Dungeon to get Golden Key
5. **Progress** through Bridge to Mountain/Forest for more treasures
6. **Reach** Castle to win the game

## 🛠️ **Assignment Requirements Completed**

- ✅ **Item entity class** - Complete with types and effects
- ✅ **Tile entity class** - Grid system foundation
- ✅ **Combat service** - Professional damage calculations
- ✅ **Inventory system** - Full item management
- ✅ **Enhanced commands** - inventory, collect, use, look
- ✅ **Grid system** - Simple 3x3 dungeon layout
- ✅ **JUnit tests** - Comprehensive test coverage

## 🎮 **How to Play**

```bash
# Run the game
mvn compile exec:java -Dexec.mainClass=org.example.App

# Run tests
mvn test

# Available commands in game:
up, down, left, right  # Movement
attack                 # Combat
inventory, inv         # View items
collect, take         # Pick up items
use                   # Use health potions
look                  # Examine area
quit                  # Exit game
```

The game now features a complete inventory system, enhanced combat mechanics, item collection, and a solid foundation
for future expansion! 🎉
