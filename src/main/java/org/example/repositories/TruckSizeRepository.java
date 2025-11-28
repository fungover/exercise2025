package org.example.repositories;

import org.example.entities.DTOs.TruckDTO;
import org.example.entities.TruckSize;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TruckSizeRepository extends ListCrudRepository<TruckSize, Integer> {

    @Query("""
            select new org.example.entities.DTOs.TruckDTO(upper(tb.brand), ts.size)
            from TruckSize ts
            inner join TruckBrand tb
            on tb.id = ts.brandId
            where ts.boardWidth = :boardWidth
            """)
    List<TruckDTO> findTruckByWidth(@Param("boardWidth") double boardWidth);
}
