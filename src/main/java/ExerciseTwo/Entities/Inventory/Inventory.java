package ExerciseTwo.Entities.Inventory;

import ExerciseTwo.Utils.PrintText;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    List<Item> inventory = new ArrayList<>();

    public void addItem(Item item) {
        inventory.add(item);
        PrintText.printYellow("Item added to Inventory");
    }

    public void removeItem(String item) {
        inventory.stream()
                .filter(name -> name.getType().equals(item))
                .findFirst().ifPresent(inventory::remove);
    }

    public boolean getInventory() {

        if(inventory.isEmpty()) {
            PrintText.printRed("Inventory is empty");
            return false;
        }else{
            PrintText.printBold("Inventory items: ");
            long countPoint = inventory.stream()
                    .filter(item -> item.getType().equals("Potion")).count();
            if(countPoint > 0) {
                System.out.println("Potion to use: "+countPoint);
            }
            long countCoins  = inventory.stream()
                    .filter(item -> item.getType().equals("Coin"))
                    .mapToInt(Item::getEffect)
                    .sum();
            if(countCoins > 0) {
                System.out.println("Sum of coins collected: "+countCoins);
            }
            return countPoint > 0;
        }
    }

}
