package org.example.utils;

import static org.example.utils.Colors.*;

public class GameText {

    // Story Introduction
    public static final String GAME_TITLE = bold("\n=== Welcome to 'Escape The Dark Cave' ===");
    public static final String CAVE_INTRO = "\n* You find yourself waking up in a dark cave *" +
            "\nAn old crackly voice speaks to you: ";
    public static final String THADE_GREETING = purple("-Hello friend.. I thought you were dead..");
    public static final String SPINE_SHIVER = "\n* You feel a cold shiver running down your spine *";
    public static final String THADE_REASSURANCE = purple("\n-Don't worry friend, I will get you out of here.." +
            "\n..hehe..in one way or another...");
    public static final String THADE_INTRODUCTION = purple("\nBut, before I help you, let's get to know each other a little bit.." +
            "\n\nI'm Thade, I used to live here.. a long time ago.." +
            "\n(whispers) ..before the monsters came..");
    public static final String NAME_PROMPT = purple("\n-What is your name, friend? ");

    // Story Continuation
    public static final String LISTEN_CAREFULLY = purple("\n-Listen carefully now..");
    public static final String ATTENTION_CHECK = bold(purple("Are you PAYING ATTENTION?"));
    public static final String ATTENTION_RESPONSE_GOOD = purple("\nGood! Here is what you need to do, in order to get out of here..");
    public static final String ATTENTION_RESPONSE_WAIT = purple("Okay..It's like that huh? I can wait forever.. \nSay 'yes' when you want to continue..");

    // Game Instructions
    public static final String FIND_KEY_INSTRUCTION = purple("\nYou need to find the golden key that will unlock the door to the exit.." +
            "\nThe key is hidden somewhere in the cave..");
    public static final String DANGER_WARNING = purple("Be careful, there are dangerous monsters everywhere.. " +
            "\nYou can't get past them or run away..." +
            "\n.. you have to fight for your life first..");
    public static final String ITEMS_ADVICE = purple("There are weapons and healing potions spread around here..");
    public static final String FIND_ITEMS_FIRST = purple(".. I suggest you find them before picking fights..");
    public static final String TROLL_WARNING = purple("Oh.. and watch out for THE TROLL, he is guarding the exit..");
    public static final String GOOD_LUCK = purple(".. you'll need it!");
    public static final String HELP_PROMPT = "\nType 'help' to see available commands";

    // Name Responses
    public static final String NAME_INTERESTING = purple(".. interesting name..");
    public static final String NAME_OKAY = purple("\nOkay, friend.. I mean ");
    public static final String NAME_DOTS = purple("..");

    // Item Message
    public static final String ITEM_PICKED_UP = "You picked up: %s";

    // Error Messages
    public static final String NO_EXIT = "You can't go that way!";
    public static final String ENEMIES_BLOCKING = "You can't leave while enemies are blocking your way!";
    public static final String INVENTORY_FULL = "Your inventory is full!";
    public static final String NO_ENEMIES = "There are no enemies here to fight!";
    public static final String NO_ITEM_HERE = "There's no %s here!";
    public static final String ITEMS_IN_ROOM = "Items in room:";
    public static final String CANT_USE_ITEM = "You can't use that item!";
    public static final String CANT_EQUIP_ITEM = "You can't equip that!";
    public static final String UNKNOWN_COMMAND = "I don't understand that command. Type 'help' for available commands.";
    public static final String TYPE_COMMAND = "Type a command or 'help'.";

    // Victory Messages
    public static final String DOOR_UNLOCK_START = yellow("\n=== You insert the Golden Key into the massive door... ===");
    public static final String DOOR_UNLOCK_CLICK = "=== *CLICK* The door unlocks with a satisfying sound! ===";
    public static final String DOOR_OPENS = "\n=== The door swings open, revealing bright sunlight! ===" +
            "\n=== You step outside and breathe fresh air for the first time in ages. ===";
    public static final String VICTORY_CONGRATULATIONS = bold(green("\n=== 🎉 CONGRATULATIONS! YOU HAVE ESCAPED THE DARK CAVE! 🎉 ==="));
    public static final String VICTORY_TIME = bold(green("=== Game completed in: %d minutes and %d seconds ==="));
    public static final String THADE_FREED = "\n=== Thade's corruption is broken, and you are finally free! ===";
    public static final String THANKS_FOR_PLAYING = cyan("\n=== Thanks for playing %s! ===");

    // Door unlock conditions
    public static final String TROLL_STILL_ALIVE = "You can't unlock the door while the troll is still alive!";
    public static final String NEED_GOLDEN_KEY = "You need the Golden Key to unlock this door!";
    public static final String NO_DOOR_HERE = "There's no door to unlock here.";

    // Game state
    public static final String GAME_OVER = red(bold("\n*** GAME OVER! ***"));
    public static final String QUIT_INSTRUCTION = "To quit the game, type 'quit'" +
            "\nFor other commands, type 'help'";

    // Input prompts
    public static final String WHAT_TO_DO = "\n> What do you want to do?";
    public static final String GO_WHERE = "Go where? (north, south, east, west)";
    public static final String TAKE_WHAT = "Take what?";
    public static final String USE_WHAT = "Use what?";
    public static final String EQUIP_WHAT = "Equip what?";
    public static final String UNLOCK_WHAT = "Unlock what? Try 'unlock door'";

    // Help and Tips
    public static final String HELP_HEADER = yellow("\n=== Available Commands ===");
    public static final String HELP_GO = "go <direction> - Move (north, south, east, west)";
    public static final String HELP_LOOK = "look           - Look around the room";
    public static final String HELP_TAKE = "take <item>    - Pick up an item";
    public static final String HELP_INVENTORY = "inventory      - Show your items";
    public static final String HELP_STATS = "stats          - Show your health and damage";
    public static final String HELP_USE = "use <item>     - Use healing potion";
    public static final String HELP_EQUIP = "equip <item>   - Equip weapon";
    public static final String HELP_ATTACK = "attack         - Fight enemies in the room";
    public static final String HELP_UNLOCK = "unlock         - Unlock door";
    public static final String HELP_HELP = "help           - Show this help";
    public static final String HELP_HINT = "stuck or hint  - Get some tips..";
    public static final String HELP_QUIT = "quit           - Exit game";

    public static final String TIPS_HEADER = yellow("\nHere are a few tips:");
    public static final String TIP_PICKUP = "Items you see need to be picked up before you can use/equip them.";
    public static final String TIP_TAKE = "You can pick up items by typing 'take <item>'.";
    public static final String TIP_USE = "You can use items by typing 'use <item>'.";
    public static final String TIP_EQUIP = "You can equip items by typing 'equip <item>'.";
    public static final String TIP_COMBAT = "Once you see an enemy you can't leave the room without fighting first.";
    public static final String TIP_PREPARE = "You can take, use/equip items before fighting.";
    public static final String TIP_NAVIGATION = "There is no compass, you need to keep track of where you are..";
    public static final String TIP_HELP = "For available commands, type 'help'.";

}