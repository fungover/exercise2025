package org.example.map;

import org.example.utils.Funcs;

public class Maps {
    public static void gameMap() {
        Funcs.spacer(2);
        Funcs.print("═══════════════════════════════════════════════════════════════════════");
        Funcs.print("                    🏰 CLIMB TO VICTORY QUEST 🏰                       ");
        Funcs.print("═══════════════════════════════════════════════════════════════════════");
        Funcs.print("");

        // LEVEL 4 - VICTORY! 🏆
        Funcs.print("                    ┌─────────┐  ");
        Funcs.print("                    │ CASTLE  │");
        Funcs.print("                    │   🏰    │  ← 🎯GOAL: Reach here to WIN!");
        Funcs.print("                    │   👑    │");
        Funcs.print("                    │   🛡️     │");
        Funcs.print("                    └────┬────┘");
        Funcs.print("                        💂 Guard");
        Funcs.print("                         ↑ ");
        Funcs.print("                         │");

        // LEVEL 3 - BRIDGE CHALLENGE
        Funcs.print("     ┌─────────┐    ┌────┴────┐    ┌─────────┐");
        Funcs.print("     │ MOUNTAIN│    │ BRIDGE  │    │ FOREST  │");
        Funcs.print("     │  ⛰️🏔️⛰️    │    │ 🌉🚪🌉  │    │  🌲🌲🌲 │");
        Funcs.print("     │  🏔️💎🏔️   ├────┤ 🚪🔒🚪  ├────│    ⛺️  │");
        Funcs.print("     │  ⛰️�️⛰️    │    │ 🌉🚪🌉  │    │  🌲🌲🌲 │");
        Funcs.print("     └─────────┘    └────┬────┘    └─────────┘");
        Funcs.print("                         ↑ LOCKED BRIDGE");
        Funcs.print("                         │");
        Funcs.print("                         │");

        // LEVEL 2 - VILLAGE & EXPLORATION
        Funcs.print("     ┌─────────┐    ┌────┴────┐    ┌─────────┐");
        Funcs.print("     │ DUNGEON │    │ VILLAGE │    │LAKE+HEAL│");
        Funcs.print("     │  ⚔️💀⚔️   │    │  🏘️🏘️🏘️    │    │ ⛰️ 🏔️ ⛰️   │");
        Funcs.print("     │💀 🔑 💀 ├────┤🏘️ 🗡️🗡️  🏘️  ├────│ ⛰️ 🩸⛰️   │");
        Funcs.print("     │  ⚔️💀⚔️   │    │  🏘️🏘️🏘️    │    │ ⛰️ 🏔️ ⛰️   │");
        Funcs.print("     └─────────┘    └────┬────┘    └─────────┘");
        Funcs.print("                         │");
        Funcs.print("                         │");
        Funcs.print("                         │");
        Funcs.print("                         │");
        Funcs.print("                         ↑ START CLIMBING");
        Funcs.print("                         │");
        Funcs.print("                         │");
        Funcs.print("                         │");
        Funcs.print("                         │");

        // LEVEL 1 - SAFE HOUSE (START)
        Funcs.print("                    ┌─────────┐");
        Funcs.print("                    │  HOME   │    ");
        Funcs.print("                    │   😎    │    ");
        Funcs.print("                    │  <) )   │    ");
        Funcs.print("                 ───┤   /\\    ├─── ");
        Funcs.print("                    │ 🏕️ 🔥🏕️   │     ");
        Funcs.print("                    └─────────┘    ");

        Funcs.print("═══════════════════════════════════════════════════════════════════════");
        Funcs.print("🎮 OBJECTIVE: Climb from SAFE HOUSE (🏕️🔥) to CASTLE (🏰) to WIN! 🏆");
        Funcs.print("⚔️ BEWARE: Guards and monsters block your path to victory!");
        Funcs.print("🗝️ COLLECT: Keys and treasures to help your journey upward!");
        Funcs.print("═══════════════════════════════════════════════════════════════════════");
        Funcs.spacer(2);
    }

    public static void inventory() {
        Funcs.print("    ┌─────┐    ┌─────┐    ┌─────┐");
        Funcs.print("    │ 💀  │    │ ⚔️  │    │ 💎  │");
        Funcs.print("    │  1  ├────┤  2  ├────┤  3  │");
        Funcs.print("    │     │    │     │    │     │");
        Funcs.print("    └──┬──┘    └─────┘    └──┬──┘");
        Funcs.print("       │                     │");
        Funcs.print("    ┌──┴──┐    ┌─────┐    ┌──┴──┐");
        Funcs.print("    │ 🗝️  │    │ 🕷️  │    │ 🛡️  │");
        Funcs.print("    │  4  ├────┤  5  ├────┤  6  │");
        Funcs.print("    │     │    │BOSS │    │     │");
        Funcs.print("    └─────┘    └─────┘    └─────┘");
        Funcs.print("");
        Funcs.print("═══════════════════════════════════════════════════════════════════════");
        Funcs.print("  ROOMS: 1=Skeleton  2=Guards  3=Treasure  4=Key  5=Boss  6=Shield");
        Funcs.print("═══════════════════════════════════════════════════════════════════════");
    }
}
