package org.example.DTO;

import java.time.OffsetDateTime;

public record CatchYearDTO(
        Long id,
        String species,
        Double length,
        Double weight,
        OffsetDateTime caughtAt,
        Integer year
) {}
