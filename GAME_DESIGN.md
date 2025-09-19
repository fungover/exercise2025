# 🎮 Simple Adventure Game - Final Design

## 🎯 **Exact Game Flow (As Requested)**

### **Step 1: Home → Village → Get Sword**

- Player starts at **Home**
- Move **up** to **Village**
- **collect** the **🗡️ Iron Sword** (automatically equipped)

### **Step 2: Village → Dungeon → Fight Skeleton → Get Golden Key**

- Move **left** to **Dungeon**
- **attack** the **💀 Skeleton Guardian** (use sword for extra damage)
- When defeated, automatically get **🔑 Golden Key**

### **Step 3: Use Key to Access Mountain → Get Diamond**

- Move **right** back to **Village**
- Move **up** to **Bridge**
- Move **left** to **Mountain** (Golden Key automatically unlocks it)
- **collect** the **💎 Precious Diamond**

### **Step 4: Castle → Give Diamond to Guard → WIN!**

- Move **right** back to **Bridge**
- Move **up** toward **Castle**
- **give** diamond to the Castle Guard
- Enter the Castle and **WIN THE GAME!** 👑

## 🔧 **Key Game Mechanics**

### **Inventory System**

- **🗡️ Iron Sword**: +15 damage (auto-equipped when collected)
- **🔑 Golden Key**: Unlocks Mountain and Forest areas
- **💎 Precious Diamond**: Required to pass the Castle Guard
- **🩸 Health Potion**: Available at Lake for healing

### **Progression Gates**

- **🔒 Mountain/Forest**: Requires Golden Key (obtained from defeating skeleton)
- **🏰 Castle**: Requires Diamond payment to guard (obtained from Mountain)

### **Combat System**

- Enhanced damage with equipped weapons
- Randomized damage for realistic combat
- Health system with healing potions

### **Movement System**

- Hub-and-spoke pattern (only move from center areas)
- Clear error messages for blocked movement
- Lock status indicators for restricted areas

## 🎮 **Commands**

- **Movement**: `up`, `down`, `left`, `right`
- **Combat**: `attack`
- **Inventory**: `inventory` or `inv`
- **Items**: `collect`, `use`, `give`
- **Information**: `look`
- **Exit**: `quit`

## 🏆 **Victory Condition**

Pay the diamond to the Castle Guard and enter the Castle to become the ruler!

## 🎯 **Game Design Philosophy**

Simple, linear progression with clear objectives:

1. **Gear up** (get sword)
2. **Fight** (defeat skeleton)
3. **Unlock** (use key for areas)
4. **Pay** (give diamond to guard)
5. **WIN!** (enter castle)

The game is designed to be straightforward and easy to follow, with each step building naturally to the next!
