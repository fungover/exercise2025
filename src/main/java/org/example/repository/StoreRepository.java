package org.example.repository;

import org.example.entities.Store;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends ListCrudRepository<Store,Integer> {
}
