package org.example.repositories;

import org.example.entities.Deck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SkateboardRepository extends ListCrudRepository<Deck, Integer> {

    @Query("""
            select upper(deck.brand), deck.boardWidth
            from Deck deck
            """)
    List<Object> findDeckBy();

    @Query("""
            select deck
            from Deck deck
            where deck.brand = :brand
            """)
    List<Deck> findDeckBy(@Param("brand") String brand);

}
