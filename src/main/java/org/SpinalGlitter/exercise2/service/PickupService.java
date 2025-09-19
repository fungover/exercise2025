// src/main/java/org/SpinalGlitter/exercise2/service/PickupService.java
package org.SpinalGlitter.exercise2.service;

import org.SpinalGlitter.exercise2.entities.*;
import java.util.Map;

public final class PickupService {
    private PickupService() {}

    public static <T extends ItemObject> void tryPickup(
            Map<Position, T> itemsOnMap, Position at, Player player) {

        T found = itemsOnMap.remove(at);
        if (found == null) return;

        if (player.getInventory().addItem(found)) {
            System.out.println("You picked up: " + found.printItem());


        } else {
            System.out.println("Your inventory is full. You leave the item.");
            itemsOnMap.put(at, found);
        }
    }
}
