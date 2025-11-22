package org.example.repositories;

import org.example.entities.Skateboard;
import org.example.entities.TruckSize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkateboardRepository extends ListCrudRepository<Skateboard, Integer> {

    @Query("""
            select upper(skateboard.brand), skateboard.boardWidth
            from Skateboard skateboard
            """)
    List<Object> findBoardBy();

    @Query("""
            select skateboard
            from Skateboard skateboard
            where skateboard.brand = :brand
            """)
    List<Skateboard> findBoardBy(@Param("brand") String brand);

    @Query("""
            select ts from TruckSize ts
            where ts.boardWidth = :boardWidth
            """)
    List<TruckSize> findByBoardWidth(@Param("boardWidth") double boardWidth);

}
