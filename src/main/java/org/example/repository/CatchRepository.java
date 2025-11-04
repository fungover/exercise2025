package org.example.repository;

import org.example.DTO.CatchYearDTO;
import org.example.entities.Catch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.List;

public interface CatchRepository extends JpaRepository<Catch, Long> {
    Optional<Catch> findCatchBySpecies(String species);

    Optional<Catch> findDistinctByWeight(double weight);

    List<Catch> findFishById(Long id);

    @Query("""
                select new org.example.DTO.CatchYearDTO(
                    c.id,
                    c.species,
                    c.length,
                    c.weight,
                    c.caughtAt,
                    YEAR(c.caughtAt)
                    )
                    from Catch c
                    order by c.weight desc
            """) List<CatchYearDTO> orderByWeightDesc();

    @Query("""
                select new org.example.DTO.CatchYearDTO(
                    c.id,
                    c.species,
                    c.length,
                    c.weight,
                    c.caughtAt,
                    YEAR(c.caughtAt)
                    )
                    from Catch c
                    order by c.weight asc
            """)
    List<CatchYearDTO> orderByWeightAsc();
}
