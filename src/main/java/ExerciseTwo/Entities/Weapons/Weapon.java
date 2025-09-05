package ExerciseTwo.Entities.Weapons;

import ExerciseTwo.Entities.Inventory.Item;
import ExerciseTwo.Utils.PrintText;

import java.util.ArrayList;
import java.util.List;

public class Weapon {

List<Item> weapons = new ArrayList<>();

    public void addWeapon(Item item) {
        weapons.add(new Sword());
        weapons.add(item);
        PrintText.printYellow("Sword added to weapon inventory");
    }

    public int numWeapons() {
        return weapons.size();
    }

    public Item getWeapon(int num){
        return weapons.get(num);
    }

    public void getWeapons() {
        for(int index = 0; index < weapons.size(); index++){
            Item item = weapons.get(index);
            System.out.println(index+": "+item.getType()+" with damage "+ item.getEffect());

        }
    }
}
