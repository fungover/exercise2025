package org.example.menu;

import java.util.List;

public class Menu {
    private final List<MenuAction> actions;

    public Menu(List<MenuAction> actions) {
        this.actions = actions;
    }

    public void display() {
        System.out.println("Menu:");
        for (int i = 0; i < actions.size(); i++) {
            System.out.println(i + ". " + actions.get(i).getDescription());
        }
        System.out.println("Enter choice (1-"+actions.size()+"): ");
    }

    public MenuAction getAction(int choice) {
        if (choice >= 1 && choice <= actions.size()) {
            return actions.get(choice - 1);
        } else {
            return null;
        }
    }
}
