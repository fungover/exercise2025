package org.example.repositories;

import org.example.entities.TruckSize;
import org.springframework.data.repository.ListCrudRepository;

public interface TruckSizeRepository extends ListCrudRepository<TruckSize, Integer> {
}
