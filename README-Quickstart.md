# Dungeon Crawler (Java) - Quick Start

**Goal:** This short guide lets a new developer understand the codebase quickly and start adding **new classes** (items, enemies, player archetypes, commands) with minimal friction.

---

## Project layout (what lives where)

- `entities/` – domain:
    - `characters/` → `Player` (abstract) + concrete archetypes (e.g., `Adventurer`)
    - `enemies/` → `Enemy` (abstract), `Goblin`, `Orc`, `Boss`
    - `items/` → `Item` (abstract), `Inventory`, capability interfaces:
        - `Equippable` (with `Slot { WEAPON, ARMOR }`)
        - `Consumable`
        - `equippables/` → `Weapon`/`Armor` (abstract), tiers `WeaponTier`/`ArmorTier`, concrete `Sword`, `Chainmail`, etc.
        - `consumables/` → `Potion` (abstract), `HealthPotion`, `DamagePotion`, `PotionTier`
- `map/` – world model & generation: `DungeonMap`, `Tile`, `Room`, `MapGenerator`
- `service/` – game logic:
    - `loot/` → `LootService`, `LootTable` (interface), `DefaultLootTable`
    - `enemy/` → `EnemyService`, `EnemyTable` (interface), `DefaultEnemyTable`
- `game/` – orchestration & CLI loop: `GameEngine`, `GameController`, `GameContext`, `PlayerFactory`
- `utils/` – helpers: `WeightedPicker`, `GridMath`, `Rng`, `Position`, `Guard`

---

## Build & Run

```bash
java -cp target/classes org.example.App            # random seed (new layout each run)
java -cp target/classes org.example.App 123456789  # fixed seed (reproducible)
```

---

## How items & inventory work (key mental model)

- `Inventory.use(item, player)` delegates to `item.onUse(player, inventory)`.
- **Potions** (`Potion` implements `Consumable`): `onUse` → `consume(player)` then **remove** the item.
- **Weapons/Armor** (`Weapon`/`Armor` implement `Equippable`): `onUse` → `inventory.equip(this, player)`; item **stays** in the backpack; hooks `onEquip/onUnequip` exist for passives.
- Balance lives in enums: `PotionTier`, `WeaponTier`, `ArmorTier`.

---

## Loot & enemies (what to tweak when adding stuff)

### Loot pipeline
- **Table:** `DefaultLootTable` chooses a top-level **Category** (NOTHING, HEALTH_POTION, DAMAGE_POTION, WEAPON, ARMOR) with `WeightedPicker`.
- **Tiers:** separate pickers for `PotionTier`, `WeaponTier`, `ArmorTier`.
- **Identities:** weapon/armor identity pickers return `Supplier<Item>` (e.g., `Sword`, `Axe`, `LeatherArmor`, `Chainmail`) and pull a tier using the shared RNG.
- **Placement:** `LootService.scatter(...)` drops **1–2 items per room** (random floor tiles, never on SPAWN).

> **When you add a new item** you usually must:
> 1) Create the class (e.g., `SpeedPotion`, `Mace`, `PlateArmor`).
> 2) Register it in `DefaultLootTable`’s **identity picker**.
> 3) (If needed) Adjust **weights** in `categoryPicker` and tier pickers.

### Enemy pipeline
- **Table:** `DefaultEnemyTable` uses a `WeightedPicker<Supplier<Enemy>>` (e.g., `Goblin` 65%, `Orc` 35%).
- **Scatter:** `EnemyService.scatter(...)` places **0–2** enemies per room, avoiding SPAWN and occupied tiles.
- **Boss:** `EnemyService.placeBoss2x2(...)` finds the room farthest from SPAWN (Manhattan distance) and places a 2×2 `Boss` footprint.

> **When you add a new enemy**:
> 1) Create the class under `entities/enemies`.
> 2) Register it in `DefaultEnemyTable` with a weight.
> 3) (Optional) Rebalance weights.

### `WeightedPicker` quick tip
- Build with `.add(value, weight)`; higher weight = higher chance.
- `pick(random)` returns one value based on the weights.
- Keep weights positive; the class guards against invalid input.

---

## Where to look first (onboarding path)
1. `org.example.App` → entry & seed handling
2. `game/GameEngine` → wiring services & start flow
3. `game/GameController` → command parsing & turn loop
4. `map/MapGenerator` → rooms/corridors/SPAWN
5. `entities/items/Inventory` + `Potion`/`Weapon`/`Armor` → use/equip rules
6. `service/loot/DefaultLootTable` & `LootService` → how loot appears
7. `service/enemy/DefaultEnemyTable` & `EnemyService` → how enemies appear

---