package org.example.repository;

import org.example.entities.Inventory;
import org.example.entities.InventoryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {
}
