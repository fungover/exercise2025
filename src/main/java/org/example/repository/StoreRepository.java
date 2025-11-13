package org.example.repository;

import org.example.entities.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends ListCrudRepository<Store,Integer> {

    @Query(value = """
        SELECT store_name FROM store
        JOIN books.inventory i on store.store_id = i.store_id
        JOIN books.book b on b.book_id = i.book_id
        JOIN books.author a on a.id = b.author_id
        WHERE a.first_name = :name GROUP BY store_name;
""", nativeQuery = true)
    List<Store> findStoreByAuthorName(@Param("name") String name);

    @Query(value = """
Select * from store
""", nativeQuery = true)
    List<Store> allStores();

    @Query(value = """
    SELECT * FROM store WHERE store_name = :store
""", nativeQuery = true)
    List<Store> findByStoreName(@Param("store") String storeName);
}

