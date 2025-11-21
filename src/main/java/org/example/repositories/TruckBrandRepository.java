package org.example.repositories;

import org.example.entities.TruckBrand;
import org.springframework.data.repository.ListCrudRepository;

public interface TruckBrandRepository extends ListCrudRepository<TruckBrand, Integer> {
}
