package org.example.repositories;

import org.example.entities.Skateboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SkateboardRepository extends ListCrudRepository<Skateboard, Integer> {

    List<Skateboard> findBoardBy();

    @Query("""
           select skateboard.id, upper(skateboard.brand), skateboard.boardWidth,
           skateboard.createdAt from Skateboard skateboard
           where skateboard.brand = :brand
          """)
    List<Skateboard> findBoardBy(@Param("brand") String brand);

    @Query("""
           select skateboard.id, upper(skateboard.brand), skateboard.boardWidth,
           skateboard.createdAt from Skateboard skateboard
           where skateboard.id = :id
          """)
    Optional<Skateboard> findBoardBy(@Param("id") Integer id);
}
