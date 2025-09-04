package ExerciseTwo.Entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Item> inventory = new ArrayList<Item>();

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("Item added to inventory");
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public void getInventory() {

        if(inventory.isEmpty()) {
            System.out.println("Inventory is empty");
        }else{
            System.out.println("Inventory items: ");
            for(Item item : inventory) {
                System.out.println("* "+item);
            }
        }
    }

}
