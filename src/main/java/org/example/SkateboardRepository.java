package org.example;

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
           skateboard.createdAt from Skateboard skateboard\s
           where skateboard.brand = :brand\s
          \s""")
    Optional<Skateboard> findBoardBy(@Param("brand") String brand);
}
