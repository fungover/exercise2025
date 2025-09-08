package ExerciseTwo.Entities.Weapons;

import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Utils.PrintText;

import java.util.ArrayList;
import java.util.List;

public class Weapon {

List<Item> weapons = new ArrayList<>();

    public Weapon(){
        weapons.add(new Sword());
    }

    public void setWeapon(Item item) {
        weapons.add(item);
        PrintText.printYellow("""
    Sword added to weapon inventory.
    Press j to check weapon inventory
    """);
    }

    public int numWeapons() {
        return weapons.size();
    }

    public Item getWeapon(int num){
        if(num < 0 || num >= weapons.size()){
            throw new IndexOutOfBoundsException("Index Out of Bounds");
        }
        return weapons.get(num);
    }

    public boolean getWeapons() {

            for (int index = 0; index < weapons.size(); index++) {
                Item item = weapons.get(index);
                System.out.println(index + ": " + item.getType() + " with damage " + item.getEffect());
            }

        int size = weapons.size();
            return size > 0;
    }
}
